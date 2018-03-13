package com.sqli.echallenge.parking;

public abstract class AbstractPlace{
    int placeIndex;

    public AbstractPlace(int placeIndex) {
        this.placeIndex = placeIndex;
    }

    abstract int takenPlaces();
    abstract char representation();
    abstract void unPark();
}
