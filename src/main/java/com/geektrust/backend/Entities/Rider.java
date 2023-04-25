package com.geektrust.backend.Entities;

import java.util.List;

public class Rider extends BaseEntity {

    private final Location currentLocation;
    private final Ride currentRide;
    private final String activeRideId;
    private final List<Driver> matchedDriverIds;

    private Rider(String riderId, Location currentLocation, Ride currentRide, String activeRideId, List<Driver> matchedDriverIds) {
        super(riderId);
        this.currentLocation = currentLocation;
        this.currentRide = currentRide;
        this.activeRideId = activeRideId;
        this.matchedDriverIds = matchedDriverIds;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public String getActiveRideId() {
        return activeRideId;
    }

    public List<Driver> getMatchedDriverIds() {
        return matchedDriverIds;
    }

    public static class Builder {
        private String riderId;
        private Location currentLocation;
        private Ride currentRide;
        private String activeRideId;
        private List<Driver> matchedDriverIds;

        public Builder setId(String riderId) {
            this.riderId = riderId;
            return this;
        }

        public Builder setCurrentLocation(Location currentLocation) {
            this.currentLocation = currentLocation;
            return this;
        }

        public Builder setCurrentRide(Ride currentRide) {
            this.currentRide = currentRide;
            return this;
        }

        public Builder setActiveRideId(String activeRideId) {
            this.activeRideId = activeRideId;
            return this;
        }

        public Builder setMatchedDriverIds(List<Driver> matchedDriverIds) {
            this.matchedDriverIds = matchedDriverIds;
            return this;
        }

        public Rider build() {
            return new Rider(riderId, currentLocation, currentRide, activeRideId, matchedDriverIds);
        }
    }
}
