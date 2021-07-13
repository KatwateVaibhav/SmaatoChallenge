package com.smaato.loadtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
	@Autowired
	private RestTemplate template;

	@GetMapping("/postApi")
	public String invokePostApiInstance() {
		return template.getForObject("http://Smaato-Challenge/api/smaato/accept?id=54&url=http://Smaato-Challenge/api/smaato/addRequestCount", String.class);
	}
	
	@GetMapping("/getApi")
	public String invokeGetApi() {
		return template.getForObject("http://Smaato-Challenge/api/smaato/accept?id=54&url=http://Smaato-Challenge/api/smaato/accept?id=5", String.class);
	}

}

