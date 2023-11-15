package com.challenge.firequasar.service;

import com.challenge.firequasar.model.Location;

public interface LocationCalculatorService {

	Location getLocation(float... distances);
}
