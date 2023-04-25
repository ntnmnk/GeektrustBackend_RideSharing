package com.geektrust.backend.Entities;

import java.time.LocalDateTime;
import com.geektrust.backend.Enums.RideStatus;

public class Ride extends BaseEntity {


    private final Rider rider;
    private final Driver driver;
    private final Location startLocation;
    private final Location destination;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final double totalBill;
    private final RideStatus rideStatus;
    private final int timeTaken;

    public Location getStartLocation() {
        return startLocation;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public Location getDestination() {
        return destination;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }


    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public Ride(Builder builder) {
        super(builder.id);
        this.rider = builder.rider;
        this.driver = builder.driver;
        this.startLocation = builder.startLocation;
        this.destination = builder.destination;

        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.totalBill = builder.totalBill;
        this.rideStatus = builder.rideStatus;
        this.timeTaken = builder.timeTaken;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private Rider rider;
        private Driver driver;
        private Location startLocation;
        private Location destination;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private double totalBill;
        private RideStatus rideStatus;
        private int timeTaken;

        public Builder() {}

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setRider(Rider rider) {
            this.rider = rider;
            return this;
        }

        public Builder setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder setStartLocation(Location startLocation) {
            this.startLocation = startLocation;
            return this;
        }

        public Builder setDestination(Location destination) {
            this.destination = destination;
            return this;
        }

        public Builder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setTotalBill(double totalBill) {
            this.totalBill = totalBill;
            return this;
        }

        public Builder setRideStatus(RideStatus rideStatus) {
            this.rideStatus = rideStatus;
            return this;
        }

        public Builder setTimeTaken(int timeTaken) {
            this.timeTaken = timeTaken;
            return this;
        }

        public Ride build() {
            return new Ride(this);
        }
    }

}
