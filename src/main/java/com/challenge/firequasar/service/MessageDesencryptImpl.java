package com.challenge.firequasar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.challenge.firequasar.exception.ServiceException;

@Service
public class MessageDesencryptImpl implements MessageDesencryptService {

	@Override
	public String getMessage(List<String>... messages) {
		if(!isMessagesLengthValid(messages))
			throw new ServiceException("Las longitudes de cadenas de mensajes son distintas entre sí.", HttpStatus.NOT_FOUND);
		
		final List<String> decodedMessage = new ArrayList<>();
		final int messageLength = messages[0].size();
		for(int i=0; i < messageLength; i++) {
			final List<String> wordsAtPosition = new ArrayList<>();
            for (List<String> message : messages) {
                final String word = message.get(i);
                if (!word.isEmpty()) {
                    wordsAtPosition.add(word);
                }
            }
            if (wordsAtPosition.size() >= 1 && wordsAtPosition.stream().allMatch(word -> word.equals(wordsAtPosition.get(0)))) {
                decodedMessage.add(wordsAtPosition.get(0));
            } else {
                throw new ServiceException("No se pudo determinar el mensaje.", HttpStatus.NOT_FOUND);
            }
		}
		return String.join(" ", decodedMessage);
	}
	
	private boolean isMessagesLengthValid(List<String>... messages) {
		int messageLength = messages[0].size();
		for(List<String> message : messages) {
			if(message.size() != messageLength) {
				return false;
			}
		}
		return true;
	}

}
