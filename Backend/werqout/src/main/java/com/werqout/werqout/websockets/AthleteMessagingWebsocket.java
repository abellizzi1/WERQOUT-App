package com.werqout.werqout.websockets;

import java.io.IOException;
import java.util.Hashtable;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

@ServerEndpoint("/message/{userName}")
@Component
public class MessagingWebSocket {
	
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();
	
	private final Logger logger = LoggerFactory.getLogger(MessagingWebSocket.class);
	
	@OnOpen
	public void onOpen(Session session, @PathParam("userName") String username) 
	throws IOException {
		logger.info("Session opened");
		
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);
		
		String message = "User " + username + " is online.";
		broadcast(message);
	}
	
	
	// Handles messages. DMs are in format "@Username:message"
	@OnMessage
	public void onMessage(Session session, String inString) throws IOException {
		
		// Gets session of user sending message
		String username = sessionUsernameMap.get(session);
		
		// Handles DM operation
		if(inString.startsWith("@")) {
			
			// var delimiter holds the index at which we want to split the String between username and message (the first space)
			int delimiter = 0;
			
			// Iterate through array and find index of first space, set delimiter, break when found in case message contains spaces
			for(int i = 0; i < inString.length(); i++) {
				if(inString.toCharArray()[i] == ' ') {
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
			
			// Send message to both users involved
			sendDirectMessage(dstUser, "[DM]" + username + ":" + message);
			sendDirectMessage(username, "[DM]" + username + ":" + message);
		}
		else { // If no '@', this is a broadcast
			broadcast(username + ':' + inString);
		}
	}
	
	@OnClose
	public void onClose(Session session) throws IOException {
		String username = sessionUsernameMap.get(session);
		
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
		
		String message = username + " disconnected";
		broadcast(message);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.info("Error");
	}
	
	private void sendDirectMessage(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
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
