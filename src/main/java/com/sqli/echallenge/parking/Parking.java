package com.sqli.echallenge.parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sqli.echallenge.parking.PlaceFactory.*;

public class Parking {
    List<AbstractPlace> places;
    List<PedestrianExit> pedestrianExits;
    private final int squareSize;
    private int availableBays;

    Parking(int squareSize) {
        this.places = new ArrayList<>();
        this.pedestrianExits = new ArrayList<>();
        this.squareSize = squareSize;
        this.availableBays = (int) Math.pow(this.squareSize, 2);
        initializePlaces();
    }


    private void initializePlaces() {
        for (int i = 0; i < this.availableBays; i++) {
            this.places.add(new NonDisabledBay(i));
        }
    }

    void addItemToPlace(char item, int index) {
        AbstractPlace place = PlaceFactory.createPlace(index, item);
        this.places.remove(index);
        this.places.add(index, place);
        if (place instanceof PedestrianExit)
            pedestrianExits.add((PedestrianExit) place);
        this.availableBays -= place.takenPlaces();
    }


    public int getAvailableBays() {
        return this.availableBays;
    }


    public int parkCar(char c) {
        int index = bestPlaceToParkCar(c);
        if (index != -1) {
            if (c == OCCUPIED_DISABLED_ONLY_BAY) {
                DisabledOnlyBay disabledOnlyBay = (DisabledOnlyBay) places.get(index);
                disabledOnlyBay.park();
            } else {
                NonDisabledBay nonDisabledBay = (NonDisabledBay) places.get(index);
                nonDisabledBay.parkBy(c);
            }
            this.availableBays--;
            return index;
        }
        return -1;
    }

    private int bestPlaceToParkCar(char c) {
        HashMap<PedestrianExit, Integer> distanceToClosestEmptyBay = distanceToClosestEmptyBay(c);
        PedestrianExit closestPedestrianExit = closestPedestrianExit(distanceToClosestEmptyBay);
        if (distanceToClosestEmptyBay.get(closestPedestrianExit) != 0)
            return closestPedestrianExit.placeIndex + distanceToClosestEmptyBay.get(closestPedestrianExit);
        return -1;
    }

    private PedestrianExit closestPedestrianExit(HashMap<PedestrianExit, Integer> distanceToClosestEmptyBay) {
        PedestrianExit closestPedestrianExit = this.pedestrianExits.get(0);
        int closestDistance = distanceToClosestEmptyBay.get(closestPedestrianExit);
        for (PedestrianExit pedestrianExit : distanceToClosestEmptyBay.keySet()) {
            if (Math.abs(distanceToClosestEmptyBay.get(pedestrianExit)) < Math.abs(closestDistance)) {
                closestPedestrianExit = pedestrianExit;
                closestDistance = distanceToClosestEmptyBay.get(pedestrianExit);
            }
            if (distanceToClosestEmptyBay.get(pedestrianExit) == closestDistance) {
                if (pedestrianExit.placeIndex < closestPedestrianExit.placeIndex) {
                    closestPedestrianExit = pedestrianExit;
                    closestDistance = distanceToClosestEmptyBay.get(pedestrianExit);
                }
            }
        }
        return closestPedestrianExit;
    }

    private HashMap<PedestrianExit, Integer> distanceToClosestEmptyBay(char c) {
        HashMap<PedestrianExit, Integer> distancesToClosestEmptyBay = new HashMap<>();
        pedestrianExits.forEach(pedestrianExit -> {
            distancesToClosestEmptyBay.put(pedestrianExit, closestEmptyBay(pedestrianExit, c));
        });
        return distancesToClosestEmptyBay;
    }

    private int closestEmptyBay(PedestrianExit pedestrianExit, char c) {
        int pedestrianExitIndex = pedestrianExit.placeIndex;
        int distance = 1;
        while (isAValidePlace(pedestrianExitIndex - distance) || isAValidePlace(pedestrianExitIndex + distance)) {
            if (c == OCCUPIED_DISABLED_ONLY_BAY) {
                if (isAnEmptyDisabledOnlyBay(pedestrianExitIndex - distance))
                    return -distance;
                if (isAnEmptyDisabledOnlyBay(pedestrianExitIndex + distance))
                    return distance;
                distance++;
            } else {
                if (isAnEmptyNonDisabledBay(pedestrianExitIndex - distance))
                    return -distance;
                if (isAnEmptyNonDisabledBay(pedestrianExitIndex + distance))
                    return distance;
                distance++;
            }
        }
        return 0;
    }

    private boolean isAnEmptyDisabledOnlyBay(int index) {
        return isAValidePlace(index) && places.get(index).representation() == EMPTY_DISABLED_ONLY_BAY;
    }

    private boolean isAnEmptyNonDisabledBay(int index) {
        return isAValidePlace(index) && places.get(index).representation() == EMPTY_NON_DISABLED_BAY;
    }

    private boolean isAValidePlace(int index) {
        return (index < (squareSize * squareSize) && index >= 0);
    }

    public boolean unparkCar(int index) {
        if (places.get(index).representation() == EMPTY_DISABLED_ONLY_BAY
                || places.get(index).representation() == EMPTY_NON_DISABLED_BAY
                || places.get(index).representation() == PEDESTRIAN_EXIT)
            return false;
        else {
            places.get(index).unPark();
            this.availableBays++;
            return true;
        }
    }

    @Override
    public String toString() {
        StringBuilder drawingBuilder = new StringBuilder();
        int totalPlaces = (int) Math.pow(this.squareSize, 2);
        for (int i = 0; i < totalPlaces; i++) {
            int line = i / this.squareSize;
            if ((line % 2) == 0) {
                drawingBuilder.append(this.places.get(i).representation());
            } else {
                drawingBuilder.append(this.places.get((this.squareSize * (line + 1) - 1) - (i % 5)).representation());
            }
            if ((i + 1) % this.squareSize == 0 && i < totalPlaces - 1)
                drawingBuilder.append("\n");
        }
        return drawingBuilder.toString();
    }
}
