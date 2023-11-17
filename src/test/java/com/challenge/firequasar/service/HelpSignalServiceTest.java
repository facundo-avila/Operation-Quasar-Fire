package com.challenge.firequasar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.challenge.firequasar.component.StaticSatelliteMessageStorage;
import com.challenge.firequasar.exception.ServiceException;
import com.challenge.firequasar.model.HelpMessageRequest;
import com.challenge.firequasar.model.HelpMessageResponse;
import com.challenge.firequasar.model.Location;
import com.challenge.firequasar.model.Satellite;

@SpringBootTest
public class HelpSignalServiceTest {

	@Autowired
	private HelpSignalService helpSignalService;
	
	@MockBean
	private StaticSatelliteMessageStorage staticSatelliteMessageStorage;
	
	@MockBean
	private LocationCalculatorService locationCalculatorService;
	
	@MockBean
	private MessageDesencryptService messageDesencrypt;
	
	@Test
	void setMessageOnSatellitesDontLoaded() {
		final HelpMessageRequest messageRequest = HelpMessageRequest.builder()
				.distance(192f)
				.name("Kenobi")
				.message(List.of("Service", "exception", "expected"))
				.build();
		
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(new HashMap<>());
		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			helpSignalService.setMessage(messageRequest);
		});
		
		assertEquals("No se ha encontrado el satellite " + messageRequest.getName(), exception.getErrorMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
	}
	
	@Test
	void setMessageOnBadNameSatellite() {
		final HelpMessageRequest messageRequest = HelpMessageRequest.builder()
				.distance(192f)
				.name("Ahsokatano")
				.message(List.of("Satellite", "bad", "name"))
				.build();
		final Map<Satellite, HelpMessageRequest> satelliteData = new HashMap<>();
		Satellite satellite1 = new Satellite();
		satellite1.setId(1L);
		satellite1.setName("KenobiTest");
		satellite1.setPositionX(200);
		satellite1.setPositionY(400);
		satelliteData.put(satellite1, null);
		
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(satelliteData);
		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			helpSignalService.setMessage(messageRequest);
		});
		
		assertEquals("No se ha encontrado el satellite " + messageRequest.getName(), exception.getErrorMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
	}
	
	@Test
	void setMessageOnExistsSatellite() {
//		Mockito.when(locationCalculatorService.getLocation(Mockito.any())).thenReturn(Location.builder().x(100f).y(-120f).build());
//		Mockito.when(messageDesencrypt.getMessage(Mockito.any())).thenReturn("Mensaje recibido");
		final HelpMessageRequest messageRequest = HelpMessageRequest.builder()
				.distance(192f)
				.name("KenobiTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		final Map<Satellite, HelpMessageRequest> satelliteData = new HashMap<>();
		Satellite satellite1 = new Satellite();
		satellite1.setId(1L);
		satellite1.setName("KenobiTest");
		satellite1.setPositionX(200);
		satellite1.setPositionY(400);
		satelliteData.put(satellite1, null);
		
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(satelliteData);
		helpSignalService.setMessage(messageRequest);
		Mockito.verify(staticSatelliteMessageStorage, times(2)).getSatelliteData();
	}
	
	@Test
	void setListOfMessagesOnExistsSatellites() {
		final HelpMessageRequest messageRequest1 = HelpMessageRequest.builder()
				.distance(192f)
				.name("KenobiTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		
		final HelpMessageRequest messageRequest2 = HelpMessageRequest.builder()
				.distance(211f)
				.name("SkywalkerTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		
		final HelpMessageRequest messageRequest3 = HelpMessageRequest.builder()
				.distance(444f)
				.name("SatoTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		final List<HelpMessageRequest> messages = List.of(messageRequest1, messageRequest2, messageRequest3);
		
		final Map<Satellite, HelpMessageRequest> satelliteData = new HashMap<>();
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
		
		satelliteData.put(satellite1, null);
		satelliteData.put(satellite2, null);
		satelliteData.put(satellite3, null);
		
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(satelliteData);
		helpSignalService.setMessages(messages);
		Mockito.verify(staticSatelliteMessageStorage, times(6)).getSatelliteData();
	}
	
	@Test
	void setListOfMessagesOnTwoSatellitesExistsAndOneNotExists() {
		final HelpMessageRequest messageRequest1 = HelpMessageRequest.builder()
				.distance(192f)
				.name("KenobiTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		
		final HelpMessageRequest messageRequest2 = HelpMessageRequest.builder()
				.distance(211f)
				.name("SkywalkerTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		
		final HelpMessageRequest messageRequest3 = HelpMessageRequest.builder()
				.distance(444f)
				.name("Ahsokatano")
				.message(List.of("Satellite", "not", "exists"))
				.build();
		final List<HelpMessageRequest> messages = List.of(messageRequest1, messageRequest2, messageRequest3);
		
		final Map<Satellite, HelpMessageRequest> satelliteData = new HashMap<>();
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
		
		satelliteData.put(satellite1, null);
		satelliteData.put(satellite2, null);
		satelliteData.put(satellite3, null);
		
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(satelliteData);
		
		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			helpSignalService.setMessages(messages);
		});
		assertEquals("No se ha encontrado el satellite " + messageRequest3.getName(), exception.getErrorMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
		Mockito.verify(staticSatelliteMessageStorage, times(5)).getSatelliteData();
	}
	
	@Test
	void processMessageFailBecauseSomeSatelliteHasntMessageSetted() {
		
		final HelpMessageRequest messageRequest1 = HelpMessageRequest.builder()
				.distance(192f)
				.name("KenobiTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		
		final HelpMessageRequest messageRequest2 = HelpMessageRequest.builder()
				.distance(211f)
				.name("SkywalkerTest")
				.message(List.of("Satellite", "exists", "name"))
				.build();
		
		final Map<Satellite, HelpMessageRequest> satelliteData = new HashMap<>();
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
		
		satelliteData.put(satellite1, messageRequest1);
		satelliteData.put(satellite2, messageRequest2);
		satelliteData.put(satellite3, null);
		
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(satelliteData);
		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			helpSignalService.processMessage();
		});
		assertEquals("Algunos satélites aun no han recibido mensaje de auxilio para triangular la posición", exception.getErrorMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
	}
	
	@Test
	void isProcessMessageOk() {
		final HelpMessageRequest messageRequest1 = HelpMessageRequest.builder()
				.distance(192f)
				.name("KenobiTest")
				.message(List.of("", "exists", "name"))
				.build();
		
		final HelpMessageRequest messageRequest2 = HelpMessageRequest.builder()
				.distance(211f)
				.name("SkywalkerTest")
				.message(List.of("Satellite", "exists", ""))
				.build();
		
		final HelpMessageRequest messageRequest3 = HelpMessageRequest.builder()
				.distance(444f)
				.name("SatoTest")
				.message(List.of("Satellite", "", "name"))
				.build();
		
		final Map<Satellite, HelpMessageRequest> satelliteData = new HashMap<>();
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
		
		satelliteData.put(satellite1, messageRequest1);
		satelliteData.put(satellite2, messageRequest2);
		satelliteData.put(satellite3, messageRequest3);
		
		final String messageDesencryptExpected = "Satellite exists name";
		final Location locationExpected = Location.builder().x(120f).y(321).build();
		Mockito.when(staticSatelliteMessageStorage.getSatelliteData()).thenReturn(satelliteData);
		Mockito.when(locationCalculatorService.getLocation(Mockito.any(float[].class))).thenReturn(locationExpected);
		Mockito.when(messageDesencrypt.getMessage(Mockito.any(List[].class))).thenReturn(messageDesencryptExpected);
		
		final HelpMessageResponse messageResponse = helpSignalService.processMessage();
		assertEquals(locationExpected.getX(), messageResponse.getPosition().getX());
		assertEquals(locationExpected.getY(), messageResponse.getPosition().getY());
		assertEquals(messageDesencryptExpected, messageResponse.getMessage());
		Mockito.verify(staticSatelliteMessageStorage, times(3)).getSatelliteData();
		Mockito.verify(locationCalculatorService, times(1)).getLocation(Mockito.any(float[].class));
		Mockito.verify(messageDesencrypt, times(1)).getMessage(Mockito.any(List[].class));
	}
}
