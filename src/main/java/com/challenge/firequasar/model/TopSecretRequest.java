package com.challenge.firequasar.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopSecretRequest {

	private List<HelpMessageRequest> satellites;
}
