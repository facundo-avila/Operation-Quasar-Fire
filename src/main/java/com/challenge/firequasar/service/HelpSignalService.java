package com.challenge.firequasar.service;

import java.util.List;
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
	private MessageDesencryptService messageDesencrypt;
	
	public void setMessage(HelpMessageRequest helpMessageRequest) {
		final Optional<Map.Entry<Satellite, HelpMessageRequest>> entryOpt = staticSatelliteMessageStorage.getSatelliteData().entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(helpMessageRequest.getName()))
                .findFirst();
		
		if(entryOpt.isEmpty()) {
			throw new ServiceException("No se ha encontrado el satellite " + helpMessageRequest.getName() , HttpStatus.NOT_FOUND);
		}
		final Satellite satelliteKey = entryOpt.get().getKey();
		Map<Satellite, HelpMessageRequest> map =
		staticSatelliteMessageStorage.getSatelliteData();
		map.put(satelliteKey, helpMessageRequest);
	}
	
	public void setMessages(List<HelpMessageRequest> helpMessageRequests) {
		helpMessageRequests.forEach(helpMessageRequest -> {
			setMessage(helpMessageRequest);
		});
	}
	
	public HelpMessageResponse processMessage() {
		if(!staticSatelliteMessageStorage.getSatelliteData().entrySet().stream().allMatch(entry -> Objects.nonNull(entry.getValue())))
			throw new ServiceException("Algunos satélites aun no han recibido mensaje de auxilio para triangular la posición" , HttpStatus.NOT_FOUND);
		
		final double[] distancesDouble = staticSatelliteMessageStorage.getSatelliteData().values().stream()
                .mapToDouble(HelpMessageRequest::getDistance)
                .toArray();
		final float[] distancesParsed = arrayDoubleToArrayFloat(distancesDouble);
		final Location location = locationCalculatorService.getLocation(distancesParsed);
		
		final List<String>[] messagesArray = staticSatelliteMessageStorage.getSatelliteData().values().stream()
				.map(HelpMessageRequest::getMessage)
				.toArray(List[]::new);
		final String msgDesencrypt = messageDesencrypt.getMessage(messagesArray);
		
		return HelpMessageResponse.builder()
				.position(location)
				.message(msgDesencrypt)
				.build();
	}

	private float[] arrayDoubleToArrayFloat(double[] distancesDouble) {
		final float[] distancesParsed = new float[distancesDouble.length];
        for (int i = 0; i < distancesDouble.length; i++) {
        	distancesParsed[i] = (float) distancesDouble[i];
        }
        return distancesParsed;
	}
}
