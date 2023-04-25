package com.geektrust.backend.Services;

import java.util.List;
import com.geektrust.backend.Entities.Driver;
import com.geektrust.backend.Entities.Rider;

public interface MatchingStrategy {
    List<Driver> matchRider(Rider rider, List<Driver> drivers);
}