package com.challenge.firequasar.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

	private static final String SATELITES_DATA = "satelites.json";

	private static List<Satellite> SATELITES;

	@Override
	public List<Satellite> findAll() {
		loadData();
		return SATELITES;
	}

	@Override
	public Optional<Satellite> findByName(final String name) {
		loadData();
		return SATELITES.stream().filter(satelite -> satelite.getName().equals(name)).findFirst();
	}

	private void loadData() {
		if (Objects.isNull(SATELITES)) {
			try {
				final InputStream inputStream = new ClassPathResource(SATELITES_DATA).getInputStream();
				SATELITES = objectMapper.readValue(inputStream, new TypeReference<List<Satellite>>() {
				});
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
