package com.dropdown.service;

import com.dropdown.APICalls.Coordinates;
import com.dropdown.APICalls.H3UberGridService;
import com.dropdown.config.GetEntityFromToken;
import com.dropdown.dto.BaseResponse;
import com.dropdown.dto.VehicleDto;
import com.dropdown.entity.GPSLocation;
import com.dropdown.entity.ServiceProvider;
import com.dropdown.entity.ServiceProviderDAO;
import com.dropdown.exception.ServiceProviderException;
import com.dropdown.repository.ServiceProviderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final GetEntityFromToken entityFromToken;
    private final H3UberGridService h3UberGridService;
    private final OnlineServiceCache onlineServiceCache;


    public GPSLocation updateServiceProviderLocationAndGetLocation(String token, GPSLocation update) throws ServiceProviderException {
        ServiceProvider provider = (ServiceProvider) entityFromToken.getEntityFromToken(token);

        if (provider == null) {
            throw new ServiceProviderException("Service provider not found");
        }


        GPSLocation oldGPSLocation = new GPSLocation(
                provider.getLocation().getLatitude(),
                provider.getLocation().getLongitude(),
                provider.getLocation().getCellAddress());
        String gridAddress = h3UberGridService.getGridAddress(new Coordinates(update.getLatitude(), update.getLongitude()));

        update.setCellAddress(gridAddress);

        provider.setLocation(update);

        serviceProviderRepository.save(provider);
        return oldGPSLocation;
    }

    public List<ServiceProviderDAO> getServiceProvidersInArea(GPSLocation location) {
        List<ServiceProviderDAO> serviceProviderDAOS;
        serviceProviderDAOS = serviceProviderRepository.findAllByCellAddress(location.getCellAddress());
        return serviceProviderDAOS;

//        return serviceProviderDAOS.stream()
//                .filter(provider -> onlineServiceCache.isOnline(provider.getEmail()))
//                .collect(Collectors.toList());
    }

    public GPSLocation getServiceProviderLocation(String token) throws ServiceProviderException {
        ServiceProvider serviceProvider = (ServiceProvider) entityFromToken.getEntityFromToken(token);
        if (serviceProvider == null) {
            throw new ServiceProviderException("Service Provider not found");
        }
        return serviceProvider.getLocation();
    }

    public BaseResponse vehicleInformation(VehicleDto vehicleDto, String token) throws ServiceProviderException {
        ServiceProvider serviceProvider = (ServiceProvider) entityFromToken.getEntityFromToken(token);
        if (serviceProvider == null) {
            throw new ServiceProviderException("Service Provider not found");
        }
        serviceProvider.setVehicleType(vehicleDto.vehicleType());
        serviceProvider.setVehicleNo(vehicleDto.vehicleNo());
        serviceProvider.setVehicleModel(vehicleDto.vehicleModel());
        serviceProviderRepository.save(serviceProvider);
        return BaseResponse.builder()
                .responseMessage("Success")
                .build();
    }
}
