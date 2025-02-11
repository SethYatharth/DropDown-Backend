package com.dropdown.service;

import com.dropdown.APICalls.Coordinates;
import com.dropdown.APICalls.H3UberGridService;
import com.dropdown.APICalls.NominatimService;
import com.dropdown.config.GetEntityFromToken;
import com.dropdown.entity.GPSLocation;
import com.dropdown.entity.User;
import com.dropdown.exception.UserException;
import com.dropdown.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GetEntityFromToken entityFromToken;
    private final NominatimService nominatimService;
    private final H3UberGridService h3UberGridService;

    public GPSLocation updateUserLocationAndGetLocation(String token, GPSLocation update) throws UserException {
        User user = (User) entityFromToken.getEntityFromToken(token);
        if (user == null) {
            throw new UserException("User not found");
        }
        String cityName = nominatimService.getCityName(update.getLatitude(), update.getLongitude());
        update.setCellAddress(
                h3UberGridService.getGridAddress(
                        new Coordinates(
                                update.getLatitude(),
                                update.getLongitude()
                        )
                )
        );
        user.setLocation(update);
        if (!user.getCity().equals(cityName)){
            user.setCity(cityName);
        }
        return userRepository.save(user).getLocation();
    }
}
