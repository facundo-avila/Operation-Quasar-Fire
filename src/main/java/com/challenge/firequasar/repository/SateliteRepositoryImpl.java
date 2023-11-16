package com.challenge.firequasar.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.challenge.firequasar.model.Satellite;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SateliteRepositoryImpl implements SateliteRepository {

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${app.satellite-data}")
	private String satelliteData;

	@Override
	public List<Satellite> findAll() {
		try {
			final InputStream inputStream = new ClassPathResource(satelliteData).getInputStream();
			return objectMapper.readValue(inputStream, new TypeReference<List<Satellite>>() {
			});
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}

}
