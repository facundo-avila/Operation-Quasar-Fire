package com.challenge.firequasar.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.challenge.firequasar.component.StaticSatelliteMessageStorage;

@SpringBootTest
public class HelpSignalServiceTest {

	@Autowired
	private HelpSignalService helpSignalService;
	
	@Mock
	private StaticSatelliteMessageStorage staticSatelliteMessageStorage;
	
	@Mock
	private LocationCalculatorService locationCalculatorService;
	
	@Mock
	private MessageDesencryptService messageDesencrypt;
	
	@Test
	void setMessage() {
		
	}
}
