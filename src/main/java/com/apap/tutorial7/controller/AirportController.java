package com.apap.tutorial7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.apap.tutorial7.rest.Setting;

@RestController
public class AirportController {

    @Autowired
    RestTemplate restTemplate;
    
    @Bean
    public RestTemplate restAirport() {
    	return new RestTemplate();
    }

	
	  @GetMapping(value="/airport")
	    public String getAirportByCity(@RequestParam("city") String city) throws Exception {
	    	String path = Setting.APIurlFront+city+Setting.APIurlBAck;
	    	return restTemplate.getForEntity(path, String.class).getBody();
	    } 
}
