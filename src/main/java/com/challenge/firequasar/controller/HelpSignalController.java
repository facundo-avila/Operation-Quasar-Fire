package com.challenge.firequasar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.firequasar.model.HelpMessageRequest;
import com.challenge.firequasar.model.HelpMessageResponse;

@RestController
@RequestMapping("/help-signal")
public class HelpSignalController {
	
	@PostMapping("/topsecret")
	public HelpMessageResponse topSecret(@RequestBody HelpMessageRequest helpMessageRequest) {
		return null;
	}
	
	@PostMapping("/topsecret-split/{satellite-name}")
	public HelpMessageRequest topSecretSplitSendMessage(@RequestBody HelpMessageRequest helpMessageRequest, @PathVariable String satelliteName) {
		helpMessageRequest.setName(satelliteName);
		return null;
	}
	
	@GetMapping("/topsecret-split")
	public HelpMessageResponse topSecretSplitSendMessage() {
		return null;
	}
	
}
