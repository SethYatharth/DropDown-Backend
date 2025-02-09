package com.dropdown.service;

import com.dropdown.APICalls.Coordinates;
import com.dropdown.APICalls.H3UberGridService;
import com.dropdown.APICalls.NominatimService;
import com.dropdown.config.GetEntityFromToken;
import com.dropdown.entity.GPSLocation;
import com.dropdown.entity.ServiceProvider;
import com.dropdown.entity.ServiceProviderDAO;
import com.dropdown.repository.ServiceProviderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceProviderService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final GetEntityFromToken entityFromToken;
    private final NominatimService nominatimService;
    private final H3UberGridService h3UberGridService;

    @Autowired
    public ServiceProviderService(
            ServiceProviderRepository serviceProviderRepository,
            GetEntityFromToken entityFromToken,
            NominatimService nominatimService,
            H3UberGridService h3UberGridService
    ) {

        this.serviceProviderRepository = serviceProviderRepository;
        this.entityFromToken = entityFromToken;
        this.nominatimService = nominatimService;
        this.h3UberGridService = h3UberGridService;
    }

    public GPSLocation updateServiceProviderLocationAndGetLocation(String token, GPSLocation update) {
        ServiceProvider provider = (ServiceProvider) entityFromToken.getEntityFromToken(token);

        if (provider == null) {
            throw new UsernameNotFoundException("");
        }

        String cityName = nominatimService.getCityName(update.getLatitude(), update.getLongitude());

        String cellAddress = provider.getLocation().getCellAddress();
        GPSLocation oldGPSLocation = new GPSLocation(provider.getLocation().getLatitude(), provider.getLocation().getLongitude(), cellAddress);

        String gridAddress = h3UberGridService.getGridAddress(new Coordinates(update.getLatitude(), update.getLongitude()));

        update.setCellAddress(gridAddress);

        provider.setLocation(update);
        if (!provider.getCity().equals(cityName)) {
            provider.setCity(cityName);
        }
        serviceProviderRepository.save(provider);
        return oldGPSLocation;
    }

    public List<ServiceProviderDAO> getServiceProvidersInArea(String cellAddress) {
        return serviceProviderRepository.findAllByCellAddress(cellAddress);
    }

    public GPSLocation getServiceProviderLocation(String token) {
        ServiceProvider serviceProvider = (ServiceProvider) entityFromToken.getEntityFromToken(token);
        return serviceProvider.getLocation();
    }
}
