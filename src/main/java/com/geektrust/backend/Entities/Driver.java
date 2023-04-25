package com.geektrust.backend.Entities;

import com.geektrust.backend.Enums.Availability;

public class Driver extends BaseEntity {
    private final Location currentLocation;
    private final Availability availability;

    private Driver(String id, Location currentLocation, Availability availability) {
        super(id);
        this.currentLocation = currentLocation;
        this.availability = availability;
    }

    public Location getCurrentLocation() {
        return new Location(currentLocation.getX(), currentLocation.getY());
    }

    public Availability getAvailability() {
        return availability;
    }

    public boolean isAvailable() {
        return availability == Availability.AVAILABLE;
    }

    public static class Builder {
        private String id;
        private Location currentLocation;
        private Availability availability = Availability.AVAILABLE;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCurrentLocation(Location currentLocation) {
            this.currentLocation = currentLocation;
            return this;
        }

        public Builder setAvailability(Availability availability) {
            this.availability = availability;
            return this;
        }

        public Driver build() {
            return new Driver(id, currentLocation, availability);
        }
    }

}
