package com.challenge.firequasar.repository;

import java.util.List;

import com.challenge.firequasar.model.Satellite;

public interface SateliteRepository {

	List<Satellite> findAll();
	
}
