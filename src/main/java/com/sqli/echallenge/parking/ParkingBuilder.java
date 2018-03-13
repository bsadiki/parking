package com.sqli.echallenge.parking;

import static com.sqli.echallenge.parking.PlaceFactory.*;

public class ParkingBuilder {
    private Parking parking;


    public ParkingBuilder withSquareSize(int squareSize) {
        this.parking = new Parking(squareSize);
        return this;
    }

    public ParkingBuilder withPedestrianExit(int pedestrianExitIndex) {
        this.parking.addItemToPlace(PEDESTRIAN_EXIT,pedestrianExitIndex);
        return this;
    }

    public ParkingBuilder withDisabledBay(int disabledBayIndex) {
        this.parking.addItemToPlace(EMPTY_DISABLED_ONLY_BAY,disabledBayIndex);
        return this;
    }

    public Parking build() {
        return this.parking;
    }
}
