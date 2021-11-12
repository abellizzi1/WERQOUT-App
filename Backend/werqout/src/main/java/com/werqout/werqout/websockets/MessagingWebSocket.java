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

@ServerEndpoint("/message/{userName}")
@Component
public class MessagingWebSocket {
	
	@Autowired
	private Map<Session, String> sessionUsernameMap = new Hashtable<>();
	
	@Autowired
	private Map<String, Session> usernameSessionMap = new Hashtable<>();
	
	private final Logger logger = LoggerFactory.getLogger(MessagingWebSocket.class);
	
	@OnOpen
	public void onOpen(Session session, @PathParam("userName") String username) 
	throws IOException {
		logger.info("Session opened");
		
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);
	}
	
	

}
