package com.challenge.firequasar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.firequasar.model.HelpMessageRequest;
import com.challenge.firequasar.model.HelpMessageResponse;
import com.challenge.firequasar.model.TopSecretRequest;
import com.challenge.firequasar.service.HelpSignalService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/help-signal")
public class HelpSignalController {


	@Autowired
	private HelpSignalService helpSignalService;
	
	@PostMapping("/topsecret")
	public HelpMessageResponse topSecret(@RequestBody TopSecretRequest topSecretRequest) {
		helpSignalService.setMessages(topSecretRequest.getSatellites());
		return helpSignalService.processMessage();
	}

	@PostMapping("/topsecret-split/{satelliteName}")
	public HelpMessageRequest topSecretSplitSendMessage(@RequestBody HelpMessageRequest helpMessageRequest,
			@PathVariable @NotNull String satelliteName) {
		helpMessageRequest.setName(satelliteName);
		helpSignalService.setMessage(helpMessageRequest);
		return helpMessageRequest;
	}

	@GetMapping("/topsecret-split")
	public HelpMessageResponse topSecretSplitSendMessage() {
		return helpSignalService.processMessage();
	}

}
