package com.challenge.firequasar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.challenge.firequasar.component.StaticSatelliteMessageStorage;
import com.challenge.firequasar.model.HelpMessageRequest;
import com.challenge.firequasar.model.Satellite;

@SpringBootTest
public class LocationClaculatorImplTest {

	@Autowired
	private LocationCalculatorTrilateration locationCalculatorImpl;
	
	@Mock
	private StaticSatelliteMessageStorage staticSatelliteMessageStorage;
	
	private Map<Satellite, HelpMessageRequest> satellitesMock;
	
	@BeforeEach
	void init() {
		satellitesMock = new HashMap<>();
	}
	
	@Test
	void isPositionCannotBeCalculatedBecauseDistancesQuantityAreGreaterThanSatellitesQuantity() {
		Satellite satellite1 = new Satellite();
		satellite1.setId(1L);
		satellite1.setName("KenobiTest");
		satellite1.setPositionX(200);
		satellite1.setPositionY(400);
		
		Satellite satellite2 = new Satellite();
		satellite2.setId(1L);
		satellite2.setName("SkywalkerTest");
		satellite2.setPositionX(23);
		satellite2.setPositionY(-500);
		
		Satellite satellite3 = new Satellite();
		satellite3.setId(1L);
		satellite3.setName("SatoTest");
		satellite3.setPositionX(312);
		satellite3.setPositionY(-212);
		satellitesMock.put(satellite1, null);
		satellitesMock.put(satellite2, null);
		satellitesMock.put(satellite3, null);
		
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(satellitesMock);
		
		final float[] distances = {100.0f, 232.5f, 3444.7f, 300.0f, 1222.32f};
		
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			locationCalculatorImpl.getLocation(distances);
		});
		assertEquals("The number of positions you provided, 3, does not match the number of distances, 5.", exception.getMessage());
	}
}
