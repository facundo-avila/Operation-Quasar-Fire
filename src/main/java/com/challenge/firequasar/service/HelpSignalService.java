package com.challenge.firequasar.service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.challenge.firequasar.component.StaticSatelliteMessageStorage;
import com.challenge.firequasar.exception.ServiceException;
import com.challenge.firequasar.model.HelpMessageRequest;
import com.challenge.firequasar.model.HelpMessageResponse;
import com.challenge.firequasar.model.Location;
import com.challenge.firequasar.model.Satellite;

@Service
public class HelpSignalService {

	@Autowired
	private StaticSatelliteMessageStorage staticSatelliteMessageStorage;
	
	@Autowired
	private LocationCalculatorService locationCalculatorService;
	
	@Autowired
	private MessageDesencrypt messageDesencrypt;
	
	public void setMessage(HelpMessageRequest helpMessageRequest) {
		final Optional<Map.Entry<Satellite, HelpMessageRequest>> entryOpt = staticSatelliteMessageStorage.getSatelliteData().entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(helpMessageRequest.getName()))
                .findFirst();
		
		if(entryOpt.isEmpty()) {
			throw new ServiceException("No se ha encontrado el satellite " + helpMessageRequest.getName() , HttpStatus.NOT_FOUND);
		}
		final Satellite satelliteKey = entryOpt.get().getKey();
		staticSatelliteMessageStorage.getSatelliteData().put(satelliteKey, helpMessageRequest);
	}
	
	public HelpMessageResponse processMessage() {
		if(!staticSatelliteMessageStorage.getSatelliteData().entrySet().stream().allMatch(entry -> Objects.nonNull(entry.getValue())))
			throw new ServiceException("Algunos satélites aun no han recibido mensaje de auxilio para triangular la posición" , HttpStatus.NOT_FOUND);
		
		final Location location = locationCalculatorService.getLocation(null);
		final String msgDesencrypt = messageDesencrypt.getMessage(null);
		return null;
	}
}
