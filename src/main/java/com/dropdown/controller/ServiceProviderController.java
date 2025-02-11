package com.dropdown.controller;

import com.dropdown.dto.BaseResponse;
import com.dropdown.dto.ServiceProviderDto;
import com.dropdown.dto.VehicleDto;
import com.dropdown.entity.ServiceProviderDAO;
import com.dropdown.exception.ServiceProviderException;
import com.dropdown.service.ServiceProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service-provider")
@RequiredArgsConstructor
public class ServiceProviderController {

    private final ServiceProviderService serviceProviderService;

    @PutMapping("/vehicle-info")
    public ResponseEntity<BaseResponse> addVehicleInformation(@RequestBody VehicleDto vehicleDto,@RequestHeader("Authorization") String token) throws ServiceProviderException {
         return ResponseEntity.ok(serviceProviderService.vehicleInformation(vehicleDto,token));
    }


}
