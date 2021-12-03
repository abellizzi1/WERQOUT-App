package com.werqout.werqout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.werqout.werqout.repository.AthleteRepository;

@Configuration
public class MessagingWebSocketConfig {

	@Autowired
	AthleteRepository athleteRepository;
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
}
