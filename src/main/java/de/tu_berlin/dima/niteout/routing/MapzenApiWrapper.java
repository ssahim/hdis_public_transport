package de.tu_berlin.dima.niteout.routing;

import de.tu_berlin.dima.niteout.routing.model.Location;
import de.tu_berlin.dima.niteout.routing.model.RouteSummary;
import de.tu_berlin.dima.niteout.routing.model.TimeMatrixEntry;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by aardila on 1/26/2017.
 */
class MapzenApiWrapper implements WalkingDirectionsAPI {

    private final String apiKey;

    public MapzenApiWrapper(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public int getWalkingTripTime(Location start, Location destination) throws IOException {
        return getWalkingTripTime(start, destination, null);
    }

    @Override
    public int getWalkingTripTime(Location start, Location destination,
                                  LocalDateTime departureTime) throws IOException {
        MapzenMobilityApiWrapper mobilityWrapper = new MapzenMobilityApiWrapper(apiKey);
        return departureTime == null ?
                mobilityWrapper.getWalkingTripTime(start, destination) :
                mobilityWrapper.getWalkingTripTime(start, destination, departureTime);
    }

    @Override
    public RouteSummary getWalkingRouteSummary(Location start, Location destination) throws IOException {
        return getWalkingRouteSummary(start, destination, null);
    }

    @Override
    public RouteSummary getWalkingRouteSummary(Location start, Location destination,
                                               LocalDateTime departureTime) throws IOException {
        MapzenMobilityApiWrapper mobilityWrapper = new MapzenMobilityApiWrapper(apiKey);
        return departureTime == null ?
                mobilityWrapper.getWalkingRouteSummary(start, destination) :
                mobilityWrapper.getWalkingRouteSummary(start, destination, departureTime);
    }

    @Override
    public List<TimeMatrixEntry> getWalkingMatrix(Location[] startLocations,
                                                  Location[] destinationLocations) throws IOException {

        MapzenMatrixApiWrapper matrixWrapper = new MapzenMatrixApiWrapper(this.apiKey);
        return matrixWrapper.getWalkingMatrix(startLocations, destinationLocations);
    }
}
