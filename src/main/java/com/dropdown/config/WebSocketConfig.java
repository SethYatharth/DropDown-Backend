package com.dropdown.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${cors.user.host}")
    private String CORS_ALLOWED_HOST;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-location")  // WebSocket endpoint
                .setAllowedOriginPatterns("*")// Allow all origins (change for security)
                .setAllowedOrigins(CORS_ALLOWED_HOST)
                .withSockJS(); // Enable SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/location","/notification","/topic1");  // Enables the broker
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for incoming messages
        registry.setUserDestinationPrefix("/user");
    }


}
