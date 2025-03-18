package com.dropdown.controller;

import com.dropdown.dto.BaseResponse;
import com.dropdown.dto.RideAcceptRejectRequest;
import com.dropdown.dto.RideRequestByCustomer;
import com.dropdown.exception.RideException;
import com.dropdown.exception.ServiceProviderException;
import com.dropdown.exception.UserException;
import com.dropdown.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ride")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public ResponseEntity<BaseResponse> createRide(@RequestBody RideRequestByCustomer request, @RequestHeader("Authorization")String token) throws ServiceProviderException, UserException {

        String rideRequest = rideService.createRideRequest(request,token);
        if (rideRequest!=null){
            return new ResponseEntity<>(BaseResponse.builder()
                    .response(true)
                    .responseMessage("Ride created successfully")
                    .build(),HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(BaseResponse.builder()
                    .response(false)
                    .responseMessage("Ride creation failed")
                    .build(),HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('SERVICE_PROVIDER')")
    @PutMapping
    public ResponseEntity<BaseResponse> acceptRide(@RequestBody RideAcceptRejectRequest request, @RequestHeader("Authorization")String token) throws ServiceProviderException, UserException, RideException {
        rideService.acceptRide(request,token);
        return new ResponseEntity<>(BaseResponse.builder()
                .response(true)
                .responseMessage("Ride accepted successfully")
                .build(),HttpStatus.OK);
    }

}
