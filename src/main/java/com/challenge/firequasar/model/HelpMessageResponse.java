package com.challenge.firequasar.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HelpMessageResponse {

	private Location position;
	
	private String message;
}
