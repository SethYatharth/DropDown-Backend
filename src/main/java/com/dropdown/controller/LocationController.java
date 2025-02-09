package com.dropdown.controller;

import com.dropdown.config.JwtService;
import com.dropdown.dto.BaseResponse;
import com.dropdown.dto.WebSocketMessage;
import com.dropdown.entity.GPSLocation;
import com.dropdown.service.ServiceProviderService;
import com.dropdown.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class LocationController {

    private final ServiceProviderService serviceProviderService;
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtService jwtService;
    private final UserService userService;

//    @MessageMapping("/message")  // Client sends message to /app/message
//    @SendTo("/topic/messages")  // Broadcast to all subscribers
//    public String processMessage(String message) {
//        return "Server received: " + message;
//    }

    @MessageMapping("/update-location")
    public void updateLocation(@Payload WebSocketMessage message) {
        System.out.println("WebSocketMessage: " + message);

        String email = jwtService.extractEmail(message.token());
        String role = jwtService.extractRole(message.token());


        if (role.equals("SERVICE_PROVIDER")) {
            GPSLocation oldGPSLocation = serviceProviderService.updateServiceProviderLocationAndGetLocation(message.token(), message.location());
            messagingTemplate.convertAndSend(
                    "/location/" + oldGPSLocation.getCellAddress(),
                    BaseResponse.builder()
                            .response(serviceProviderService.getServiceProvidersInArea(oldGPSLocation.getCellAddress()))
                            .responseMessage("List of ServiceProviders in Area " + oldGPSLocation.getCellAddress())
                            .build()
            );
            GPSLocation newGPSLocation = serviceProviderService.getServiceProviderLocation(message.token());
            messagingTemplate.convertAndSend(
                    "/location/"+newGPSLocation.getCellAddress(),
                    BaseResponse.builder()
                            .response(serviceProviderService.getServiceProvidersInArea(newGPSLocation.getCellAddress()))
                            .responseMessage("List of ServiceProviders in Area " + newGPSLocation.getCellAddress())
                            .build()
            );
        } else if (role.equals("USER")) {
            GPSLocation newGPSLocation = userService.updateUserLocationAndGetLocation(message.token(), message.location());
            messagingTemplate.convertAndSendToUser(
                    email,
                    "/location-sub",
                    BaseResponse.builder()
                            .response(newGPSLocation.getCellAddress())
                            .responseMessage("Find The List of ServiceProviders in Area " + newGPSLocation.getCellAddress())
                            .build()
            );
        }
    }
}


