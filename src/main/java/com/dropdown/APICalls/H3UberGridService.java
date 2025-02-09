package com.dropdown.APICalls;

import com.uber.h3core.H3Core;
import com.uber.h3core.util.LatLng;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class H3UberGridService implements Grid{

    H3Core h3Core ;
    int resolution = 7;

    public H3UberGridService() throws IOException {
        h3Core = H3Core.newInstance();
    }

    @Override
    public String getGridAddress(Coordinates coordinates) {
        return h3Core.latLngToCellAddress(coordinates.lat,coordinates.lon,resolution);
    }

    @Override
    public Coordinates getCoordinatesFromGridAddress(String gridAddress) {
        LatLng latLng = h3Core.cellToLatLng(gridAddress);
        return new Coordinates(latLng.lat,latLng.lng);
    }
}
