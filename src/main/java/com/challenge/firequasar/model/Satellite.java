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

}
