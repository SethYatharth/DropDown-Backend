package com.dropdown.service;

import com.dropdown.config.JwtService;
import com.dropdown.dto.RideAcceptRejectRequest;
import com.dropdown.dto.RideRequestByCustomer;
import com.dropdown.dto.RideRequestToServiceProvider;
import com.dropdown.entity.Ride;
import com.dropdown.entity.RideStatus;
import com.dropdown.entity.ServiceProvider;
import com.dropdown.entity.User;
import com.dropdown.exception.RideException;
import com.dropdown.exception.ServiceProviderException;
import com.dropdown.exception.UserException;
import com.dropdown.mapper.RideMapper;
import com.dropdown.repository.RideRepository;
import com.dropdown.repository.ServiceProviderRepository;
import com.dropdown.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final RideMapper rideMapper;
    private final ServiceProviderService serviceProviderService;

    public boolean createRideRequest(RideRequestByCustomer request, String token) throws ServiceProviderException, UserException {
        if (request.serviceProviderId() == null) {
            throw new ServiceProviderException("ServiceProvider Id no found in request");
        }
        String email = jwtService.extractEmail(token.substring(7));
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UserException("User Not Found"));
        ServiceProvider serviceProvider = serviceProviderRepository.findById(request.serviceProviderId()).orElseThrow(() -> new ServiceProviderException("Service Provider Not Found"));


        Ride save = rideRepository.save(
                Ride.builder()
                        .serviceProvider(ServiceProvider.builder()
                                .id(request.serviceProviderId())
                                .build())
                        .user(User.builder()
                                .id(user.getId())
                                .build())
                        .createdAt(LocalDateTime.now())
                        .destinationLocation(request.destinationLocation())
                        .rideStatus(RideStatus.REQUESTED_BY_CUSTOMER)
                        .build()
        );
        RideRequestToServiceProvider rideRequestToServiceProvider = rideMapper.rideRequestToServiceProvider(save, user, serviceProvider);

        // notification to specific service provider
        messagingTemplate.convertAndSend("/notification/"+serviceProvider.getId(),rideRequestToServiceProvider);
        return true;
    }

    public void acceptRide(RideAcceptRejectRequest request, String token) throws ServiceProviderException, RideException {
        String email = jwtService.extractEmail(token.substring(7));
        Ride ride = rideRepository.findById(request.rideId()).orElseThrow(() -> new RideException("Ride Doesn't Exist by following id"));
        ServiceProvider serviceProvider = serviceProviderRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ServiceProviderException("No Service Provider Exist"));
        if (request.accept()) {
            if (serviceProvider.getRides().contains(ride)) {
                rideRepository.updateRideStatus(request.rideId(), RideStatus.ACCEPTED_BY_DRIVER);
            }
        }
    }

    public Boolean checkStatus(String rideId, String token) throws ServiceProviderException, RideException {
        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RideException("Ride Doesn't Exist by following id"));
        if (ride.getRideStatus().equals(RideStatus.REQUESTED_BY_CUSTOMER)) {
            rideRepository.deleteById(rideId);
            return false;
        }
        return true;
    }
//    @Scheduled(fixedRate = 3000)
//    public void send(){
//        messagingTemplate.convertAndSend("/topic1",rideRepository.findAll());
//    }

}
