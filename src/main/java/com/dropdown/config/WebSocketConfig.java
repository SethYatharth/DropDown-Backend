package com.dropdown.config;

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
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-location")  // WebSocket endpoint
                .setAllowedOriginPatterns("*")// Allow all origins (change for security)
                .setAllowedOrigins("*")
                .withSockJS(); // Enable SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/location","/location-sub");  // Enables the broker
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for incoming messages
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthChannelInterceptorAdapter());
    }

    /***
     * 🔹 When a User Updates Their Location
     * 1️⃣ User sends WebSocket message to /app/update-location with JWT.
     * 2️⃣ Server extracts username from JWT and finds their cellAddress.
     * 3️⃣ Server sends a private message to /user/{token}/location-subscription with the cellAddress.
     * 4️⃣ User subscribes to /location/{cellAddress} for real-time updates.
     *
     * 🔹 When a Service Provider Updates Location
     * 1️⃣ All users subscribed to /location/{cellAddress} get the updated provider list instantly.
     */
}
