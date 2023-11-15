package com.challenge.firequasar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.firequasar.model.HelpMessageRequest;
import com.challenge.firequasar.model.HelpMessageResponse;
import com.challenge.firequasar.service.LocationCalculatorService;
import com.challenge.firequasar.service.MessageDesencrypt;

@RestController
@RequestMapping("/help-signal")
public class HelpSignalController {

	@Autowired
	private LocationCalculatorService locationCalculatorService;
	
	@Autowired
	private MessageDesencrypt messageDesencrypt;
	
	@PostMapping("/topsecret")
	public HelpMessageResponse topSecret(@RequestBody HelpMessageRequest helpMessageRequest) {
		return null;
	}

	@PostMapping("/topsecret-split/{satellite-name}")
	public HelpMessageRequest topSecretSplitSendMessage(@RequestBody HelpMessageRequest helpMessageRequest,
			@PathVariable String satelliteName) {
		helpMessageRequest.setName(satelliteName);
		return null;
	}

	@GetMapping("/topsecret-split")
	public HelpMessageResponse topSecretSplitSendMessage() {
		
		float[] floatArray = { 1.1f, 2.2f, 3.3f };
		List<String> kenobiMessage = List.of("este", "", "", "mensaje", "");
        List<String> skywalkerMessage = List.of("", "es", "", "", "secreto");
        List<String> satoMessage = List.of("este", "", "un", "", "");
		String msg = messageDesencrypt.getMessage(kenobiMessage, skywalkerMessage, satoMessage);
		return HelpMessageResponse.builder().position(locationCalculatorService.getLocation(floatArray))
				.message(msg)
				.build();
	}

}
