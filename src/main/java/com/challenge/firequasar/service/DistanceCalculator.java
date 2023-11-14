package com.challenge.firequasar.service;

import com.challenge.firequasar.model.Location;

public interface DistanceCalculator {

	Location getLocation(float... distances);
}
