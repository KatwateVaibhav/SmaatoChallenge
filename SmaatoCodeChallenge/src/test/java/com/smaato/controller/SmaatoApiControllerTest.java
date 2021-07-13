package com.smaato.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
class SmaatoApiControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;
    

    @LocalServerPort
    private  int port;
    private  String getUrl;
    URI uri;
    
    
    @BeforeEach
    public void setUp() {
    	getUrl = "http://localhost:" + port + "/api/smaato/accept";
    	
    }

    @Test
    void acceptWithId() throws Exception {
    	uri = UriComponentsBuilder.fromHttpUrl(getUrl).queryParam("id", 145).build().toUri();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl, String.class);
    	assertEquals("ok", "ok");
    }

    @Test
    void idNotPresent() throws Exception {
    	uri = UriComponentsBuilder.fromHttpUrl(getUrl).build().toUri();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
    	assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
    }

	
	@Test
	void acceptIdWithPostRequest() throws Exception {
		
		uri = UriComponentsBuilder.fromHttpUrl(getUrl).queryParam("id", 12).queryParam("url","http://localhost:" + port +"/api/smaato/addRequestCount").build().toUri();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

	}
	 
	@Test
    void acceptIncorrectId() throws Exception {
    	uri = UriComponentsBuilder.fromHttpUrl(getUrl).queryParam("id", "zyxs").build().toUri();
    	ResponseEntity<String> responseEntity = restTemplate.getForEntity(getUrl, String.class);
    	assertEquals("ok", "ok");
    }
	
	@Test
	void acceptIdWithGetRequest() throws Exception {
		
		uri = UriComponentsBuilder.fromHttpUrl(getUrl).queryParam("id", 12).queryParam("url","http://localhost:" + port +"api/smaato/accept?id=5").build().toUri();
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
		assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());

	}

}