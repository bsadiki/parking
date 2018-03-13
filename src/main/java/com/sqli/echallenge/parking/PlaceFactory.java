package com.sqli.echallenge.parking;

public class PlaceFactory {
    public static final char PEDESTRIAN_EXIT = '=';
    public static final char EMPTY_DISABLED_ONLY_BAY = '@';
    public static final char OCCUPIED_DISABLED_ONLY_BAY = 'D';
    public static final char EMPTY_NON_DISABLED_BAY = 'U';
    static AbstractPlace createPlace(int index, char c){
        switch (c){
            case PEDESTRIAN_EXIT: return new PedestrianExit(index);
            case EMPTY_DISABLED_ONLY_BAY: return new DisabledOnlyBay(index);
        }
        return new NonDisabledBay(index);
    }
}
