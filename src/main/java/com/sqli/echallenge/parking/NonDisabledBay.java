package com.sqli.echallenge.parking;

import static com.sqli.echallenge.parking.PlaceFactory.EMPTY_NON_DISABLED_BAY;

public class NonDisabledBay extends AbstractPlace {
    private Character takenBy;
    public NonDisabledBay(int placeIndex) {
        super(placeIndex);
    }

    @Override
    int takenPlaces() {
        if (this.takenBy==null)
            return 0;
        return 1;
    }

    @Override
    char representation() {
        if (this.takenBy == null)
            return EMPTY_NON_DISABLED_BAY;
        return this.takenBy;
    }
    void parkBy(char c){
        this.takenBy = c;
    }
    @Override
    void unPark(){
        this.takenBy = null;
    }
}
