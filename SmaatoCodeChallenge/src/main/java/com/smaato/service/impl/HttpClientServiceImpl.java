package com.smaato.service.impl;

import java.net.URI;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.smaato.service.HttpClientService;

@Service
@EnableAsync
@EnableScheduling
public class HttpClientServiceImpl implements HttpClientService {
	
	Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	Set<Long> requestIds = new CopyOnWriteArraySet<>();
	UriComponentsBuilder httpUrl;
	URI uri;

	@Async
	@Override
	public void sendHttpRequest(String strUri) {
		if (!strUri.isEmpty() && strUri.contains("/addRequestCount")) {
			try {
				httpUrl = UriComponentsBuilder.fromHttpUrl(strUri);
				uri = httpUrl.queryParam("reqCount", requestIds.size()).build().toUri();
				ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, null, String.class);
				logger.info("Status code for post endpoint {} for {} is {}", strUri, requestIds.size(),result.getStatusCode());
			} catch (HttpClientErrorException exception) {
				logger.info("Status code from the endpoint {} is {}", strUri, exception.getStatusCode().value());
			}
		} else {
			uri = UriComponentsBuilder.fromHttpUrl(strUri).build().encode().toUri();
			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
			logger.info("Status code from the endpoint {} is {}", strUri, result.getStatusCode());
		}
		
	}

	
	@Async
	@Override
	public void saveId(Long id) {
		CompletableFuture.completedFuture(requestIds.add(id));
	}
	
	@Scheduled(cron = "0 * * ? * *")
	@Override
	public void logDetails() {
		logger.info("No of unique Ids are -->"+requestIds.size());
		requestIds.clear();
	}
}
