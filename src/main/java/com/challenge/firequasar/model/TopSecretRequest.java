package com.challenge.firequasar.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopSecretRequest {

	private List<HelpMessageRequest> satellites;
}
