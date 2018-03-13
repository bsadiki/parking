package com.sqli.echallenge.parking;

import static com.sqli.echallenge.parking.PlaceFactory.PEDESTRIAN_EXIT;

public class PedestrianExit extends AbstractPlace {
    public PedestrianExit(int placeIndex) {
        super(placeIndex);
    }

    @Override
    int takenPlaces() {
        return 1;
    }

    @Override
    char representation() {
        return PEDESTRIAN_EXIT;
    }

    @Override
    void unPark() {
    }

}
