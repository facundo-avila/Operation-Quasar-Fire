package com.challenge.firequasar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.challenge.firequasar.exception.ServiceException;

@SpringBootTest
class LocationCalculatorImplTest {

	@Autowired
	private MessageDesencryptImpl messageDesencryptImpl;

	private List<String>[] messages;

	@AfterEach
	void purge() {
		messages = null;
	}
	@Test
	void messageCanBeDesencrypt() {
		messages = new List[] { Arrays.asList("este", "", "", "mensaje", ""),
				Arrays.asList("", "es", "", "", "secreto"), Arrays.asList("este", "", "un", "", "") };

		final String messageDesencrypted = "este es un mensaje secreto";
		assertEquals(messageDesencrypted, messageDesencryptImpl.getMessage(messages));
	}

	@Test
	void messageCannotBeDesencryptBecauseMessageSizesNotEquals() {
		messages = new List[] { Arrays.asList("este", "", "", "mensaje", ""),
				Arrays.asList("", "es", "un"), Arrays.asList( "", "secreto") };

		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			messageDesencryptImpl.getMessage(messages);
		});
		
		assertEquals("Las longitudes de cadenas de mensajes son distintas entre sí.", exception.getErrorMessage());
		assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	void messageCannotBeDesencryptBecauseWordsIncorrectOrder() {
		messages = new List[] { Arrays.asList("este", "", "", "mensaje", "secreto"),
				Arrays.asList("es", "esto", "", "", "secreto"), Arrays.asList("un", "es", "un", "", "mensaje") };
		
		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			messageDesencryptImpl.getMessage(messages);
		});
		
		assertEquals("No se pudo determinar el mensaje.", exception.getErrorMessage());
		assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	void messageCannotBeDesencryptBecauseWordsHasUpperAndLowerCaseCombination() {
		messages = new List[] { Arrays.asList("eStE", "ES", "un", "Mensaje", "seCreto"),
				Arrays.asList("Este", "es", "Un", "Mensaje", "Secreto"), Arrays.asList("eStE", "Es", "UN", "MeNsaje", "secreTO") };
		
		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			messageDesencryptImpl.getMessage(messages);
		});
		
		assertEquals("No se pudo determinar el mensaje.", exception.getErrorMessage());
		assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	void messageCannotBeDesencryptBecauseEmptySpacesInEveryArrayAtSamePosition() {
		messages = new List[] { Arrays.asList("este", "es", "", "mensaje", "secreto"),
				Arrays.asList("este", "es", "", "mensaje", "secreto"), Arrays.asList("este", "es", "", "mensaje", "secreto") };
		
		final ServiceException exception = assertThrows(ServiceException.class, () -> {
			messageDesencryptImpl.getMessage(messages);
		});
		
		assertEquals("No se pudo determinar el mensaje.", exception.getErrorMessage());
		assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
	}
}
