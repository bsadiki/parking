package com.sqli.echallenge.parking;

import static com.sqli.echallenge.parking.PlaceFactory.EMPTY_DISABLED_ONLY_BAY;
import static com.sqli.echallenge.parking.PlaceFactory.OCCUPIED_DISABLED_ONLY_BAY;

public class DisabledOnlyBay extends AbstractPlace {
    private boolean empty;

    public DisabledOnlyBay(int placeIndex) {
        super(placeIndex);
        this.empty = true;
    }

    @Override
    int takenPlaces() {
        if (this.empty)
            return 0;
        return 1;
    }

    @Override
    char representation() {
        if (this.empty)
            return EMPTY_DISABLED_ONLY_BAY;
        return OCCUPIED_DISABLED_ONLY_BAY;
    }

    void park() {
        this.empty = false;
    }

    @Override
    void unPark() {
        this.empty = true;
    }
}
