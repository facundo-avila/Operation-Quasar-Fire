package com.challenge.firequasar.model;

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

}
