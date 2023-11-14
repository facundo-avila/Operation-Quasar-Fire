package com.challenge.firequasar.repository;

import java.util.List;
import java.util.Optional;

import com.challenge.firequasar.model.Satellite;

public interface SateliteRepository {

	List<Satellite> findAll();
	
	Optional<Satellite> findByName(String name);
	
}
