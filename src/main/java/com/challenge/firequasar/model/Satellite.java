package com.challenge.firequasar.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Satellite {

	private Long id;

	private String name;

	private float positionX;

	private float positionY;
	
	private List<String> message;

	public Location getLocation(float distance) {
		//TODO Calcular posición del emisor a partir de la distancia, positionX, positionY
		return Location.builder()
				.x(0)
				.y(0)
				.build();
	}
	
//	public String getMessage(List<String> )

}
