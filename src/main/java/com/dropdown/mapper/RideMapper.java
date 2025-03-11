package com.dropdown.mapper;

import com.dropdown.dto.RideRequestToServiceProvider;
import com.dropdown.dto.UserDto;
import com.dropdown.entity.Ride;
import com.dropdown.entity.ServiceProvider;
import com.dropdown.entity.User;
import com.dropdown.service.DistanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RideMapper {

    private final DistanceService distanceService;


    public RideRequestToServiceProvider rideRequestToServiceProvider(Ride ride, User user, ServiceProvider serviceProvider){
        return RideRequestToServiceProvider.builder()
                .id(ride.getId())
                .destinationLocation(ride.getDestinationLocation())
                .distanceToPickUp(distanceService.calculateDistance(user.getLocation(),serviceProvider.getLocation()))
                .requestingUser(UserDto.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .phoneNo(user.getPhoneNo())
                        .build())
                .build();
    }

}
