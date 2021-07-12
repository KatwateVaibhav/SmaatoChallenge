package com.smaato.controller;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smaato.service.HttpClientService;
import com.smaato.service.impl.HttpClientServiceImpl;

@RestController
@RequestMapping(path = "/api/smaato/")
public class SmaatoApiController {

	Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);
	
	@Autowired
	private HttpClientService httpClientService;

	@Value("${server.port}")
	private int port;

	@GetMapping("/accept")
	public String acceptRequest(@RequestParam Long id, @RequestParam(required = false) String url) throws Exception {
		try {
			httpClientService.saveId(id);
			if (url != null && !url.isEmpty() && isValid(url)) {
				httpClientService.sendHttpRequest(url);
			}
			return "ok";
		} catch (Exception e) {
			return "failed";
		}
	} 

	@PostMapping("/addRequestCount")
	public String addRequestCount(@RequestParam(value = "reqCount") String requestCount) {
		try {
			logger.info("Request with  count {} went on port {}", requestCount,port);
			return "Total count of request is " + requestCount + "on port " + port;
		} catch (Exception e) {
			return "failed";
		}
	}

	private static boolean isValid(String url) {
		try {
			new URL(url).toURI();
			return true;
		}

		// If there was an Exception while creating URL object
		catch (Exception e) {
			return false;
		}
	}

}
