package com.challenge.firequasar.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.firequasar.model.HelpMessageRequest;
import com.challenge.firequasar.model.Satellite;
import com.challenge.firequasar.repository.SatelliteRepository;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Component
public class StaticSatelliteMessageStorage {

	@Getter
	private final Map<Satellite, HelpMessageRequest> satelliteData = new HashMap<>();
	
	@Autowired
	private SatelliteRepository sateliteRepository;

    @PostConstruct
    public void initialize() {
    	sateliteRepository.findAll().forEach(satellite -> satelliteData.put(satellite, null));
    }
}
