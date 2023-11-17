package com.challenge.firequasar.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.firequasar.component.StaticSatelliteMessageStorage;
import com.challenge.firequasar.model.Location;
import com.challenge.firequasar.model.Satellite;
import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class LocationCalculatorTrilateration implements LocationCalculatorService {

	@Autowired
	private StaticSatelliteMessageStorage staticSatelliteMessageStorage;
	
	@Override
	public Location getLocation(float... distances) {
		final List<Satellite> satellites = staticSatelliteMessageStorage.getSatelliteData().keySet().stream()
                .collect(Collectors.toList());
		
		final double[][] positions = satellites.stream()
                .map(satellite -> new double[]{(double) satellite.getPositionX(), (double) satellite.getPositionY()})
                .toArray(double[][]::new);

		final NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(
				new TrilaterationFunction(positions, convertFloatArrayToDoubleArray(distances)), new LevenbergMarquardtOptimizer());
		
		final double[] location = solver.solve().getPoint().toArray();
		return Location.builder().x((float) location[0])
				.y((float) location[1])
				.build();
	}
	
	private double[] convertFloatArrayToDoubleArray(float... floatArray) {
        final int length = floatArray.length;
        final double[] doubleArray = new double[length];

        for (int i = 0; i < length; i++) {
            doubleArray[i] = (double) floatArray[i];
        }

        return doubleArray;
    }

}
