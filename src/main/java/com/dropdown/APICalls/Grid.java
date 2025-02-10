package com.dropdown.APICalls;

public interface Grid {
     String getGridAddress(Coordinates coordinates);
     Coordinates getCoordinatesFromGridAddress(String gridAddress);

}
