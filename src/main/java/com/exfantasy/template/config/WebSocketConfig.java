package com.exfantasy.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * <pre>
 * Enable and configure Stomp over WebSocket.
 * 
 * 參考: <a href="https://github.com/netgloo/spring-boot-samples/tree/master/spring-boot-web-socket-user-notifications">Spring Boot Web Socket User Notifications</a>
 * </pre>
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").withSockJS();
	}

}
