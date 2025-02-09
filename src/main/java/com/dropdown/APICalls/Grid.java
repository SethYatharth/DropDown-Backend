package com.dropdown.APICalls;

public interface Grid {
    public String getGridAddress(Coordinates coordinates);
    public Coordinates getCoordinatesFromGridAddress(String gridAddress);
}
