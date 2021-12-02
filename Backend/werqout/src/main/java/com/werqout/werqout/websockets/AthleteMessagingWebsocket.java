package com.werqout.werqout.websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.werqout.werqout.controllers.AthleteController;
import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.AthleteDM;
import com.werqout.werqout.models.AthleteMessage;
import com.werqout.werqout.repository.AthleteDMRepository;
import com.werqout.werqout.repository.AthleteMessageRepository;
import com.werqout.werqout.repository.AthleteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@ServerEndpoint("/message/{athleteID}")
@Component
@ComponentScan
public class AthleteMessagingWebsocket {
	
	static AthleteRepository athleteRepository;
	
	@Autowired
	public void setAthleteRepo(AthleteRepository repo) {
		athleteRepository = repo;
	}
	
	static AthleteDMRepository athleteDMRepository;
	
	@Autowired
	public void setAthleteDMRepo(AthleteDMRepository repo) {
		athleteDMRepository = repo;
	}
	
	static AthleteMessageRepository athleteMessageRepository;
	
	@Autowired
	public void setAthleteMessageRepo(AthleteMessageRepository repo) {
		athleteMessageRepository = repo;
	}
	
	private static Map<Session, Long> sessionUsernameMap = new Hashtable<>();
	private static Map<Long, Session> usernameSessionMap = new Hashtable<>();
	
	private final Logger logger = LoggerFactory.getLogger(AthleteMessagingWebsocket.class);
	
	@OnOpen
	public void onOpen(Session session, @PathParam("athleteID") long id) 
	throws IOException {
		logger.info("Session opened");
		
		sessionUsernameMap.put(session, id);
		usernameSessionMap.put(id, session);
		
		//Athlete athlete = athleteRepository.findById(id);
		
		//Athlete athlete = athleteController.getAthlete(id);
		
		String message = "Athlete with id: " + id + " is online.";
		
		List<Athlete> dms = athleteRepository.findById(id).getAthletesDMing();
		
		for(Athlete i : dms) 
			broadcastToAthlete(message, i.getId());
	}
	
	
	// Handles messages. DMs are in format "@id:message"
	@OnMessage
	public void onMessage(Session session, String inString) throws IOException {
		
		long id = sessionUsernameMap.get(session);
		
		// Handles DM operation
		if(inString.startsWith("@")) {
			
			// var delimiter holds the index at which we want to split the String between username and message (the first space)
			int delimiter = 0;
			
			// Iterate through array and find index of first space, set delimiter, break when found in case message contains spaces
			for(int i = 0; i < inString.length(); i++) {
				if(inString.toCharArray()[i] == ':') {
					delimiter = i;
					break;
				}
			}
			
			// Error thrown if no space in message, no way to determine where username ends
			if(delimiter == 0) {
				throw new IllegalArgumentException("Message format invalid");
			}
			
			// Split into substrings, first is from the first character after '@' until just before the space delimiter
			String dstUser = inString.substring(1, delimiter);
			// Second is the first character after the delimiter until the end of the string
			String message = inString.substring(delimiter + 1);
			
			long TOid = Integer.parseInt(dstUser);
			// Send message to both users involved
			sendDirectMessage(TOid, session, message);
		}
		else { // If no '@', this is a broadcast
			broadcast(id + ':' + inString);
		}
	}
	
	@OnClose
	public void onClose(Session session) throws IOException {
		long id = sessionUsernameMap.get(session);
		
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(id);
		
		String message = id + " disconnected";
		Athlete athlete = athleteRepository.findById(id);
		
		for(Athlete i : athlete.getAthletesDMing())
			broadcastToAthlete(message, i.getId());
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.info("Error" + sessionUsernameMap.get(session) + throwable.getMessage());
	}
	
	private void sendDirectMessage(long id, Session session, String message) {
		try {
			Athlete toAthlete = athleteRepository.findById(id);
			long fromId = sessionUsernameMap.get(session);
			Athlete fromAthlete = athleteRepository.findById(fromId);
			
			AthleteDM dm = fromAthlete.getDMWithAthlete(toAthlete);
			
			if(dm == null) {
				dm = athleteDMRepository.save(new AthleteDM(toAthlete, fromAthlete));
				toAthlete.addDM(dm);
				fromAthlete.addDM(dm);
				athleteRepository.save(toAthlete);
				athleteRepository.save(fromAthlete);
			}
			
			AthleteMessage toSend = new AthleteMessage(fromAthlete, message, dm);
			athleteMessageRepository.save(toSend);
			
			dm.sendMessage(toSend);
			athleteDMRepository.save(dm);
			
			usernameSessionMap.get(id).getBasicRemote().sendText(fromId + ':' + message);
		} catch(IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}
	
	private void broadcastToAthlete(String message, long id) {
		try {
			usernameSessionMap.get(id).getBasicRemote().sendText(message);
		} catch(IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}
	
	private void broadcast(String message) {
		sessionUsernameMap.forEach((session, username) -> {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e){
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
		});
	}
}
