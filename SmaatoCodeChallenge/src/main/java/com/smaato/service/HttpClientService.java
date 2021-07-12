package com.smaato.service;

public interface HttpClientService {
	
	void sendHttpRequest(String strUrl);
	void saveId(Long id);
	void logDetails();

}
