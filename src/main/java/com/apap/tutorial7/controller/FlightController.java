package com.apap.tutorial7.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.apap.tutorial7.model.FlightModel;
import com.apap.tutorial7.model.PilotModel;
import com.apap.tutorial7.rest.Setting;
import com.apap.tutorial7.service.FlightService;
import com.apap.tutorial7.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * FlightController
 */
@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;
    
    @Autowired
    private PilotService pilotService;
    
    @PostMapping(value="/add")
    public FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
    	return flightService.addFlight(flight);
    }
    
    @GetMapping(value="/view/{flightNumber}")
    public FlightModel flightView(@PathVariable("flightNumber") String flightNumber) {
    	FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber).get();
    	return flight;
    }
    
    @DeleteMapping(value="/delete")
    public String deleteFlight(@RequestParam("flightId") long flightId) {
    	FlightModel flight = flightService.getFlightDetailById(flightId).get();
    	flightService.deleteFlight(flight);
    	return "Flight Has Been Deleted";
    }
    
    @PutMapping(value="/update/{flightId}")
    public String updateFlight(@PathVariable("flightId") long flightId,
    		@RequestParam("origin") Optional<String> origin,
    		@RequestParam("destination") Optional<String> destination,
    		@RequestParam("time") Optional<Date> time) { 
    	FlightModel flight = null;
    	
    	try{
    		flight = flightService.getFlightDetailById(flightId).get();
    	}
    	finally {
    		if (flight == null)
    			return "Couldn't find the flight";
    	}
    	if(origin.isPresent())
    		flight.setOrigin(origin.get());
    	if(destination.isPresent())
    		flight.setDestination(destination.get());
    	if(time.isPresent())
    		flight.setTime(time.get());
    	flightService.updateFlight(flightId, flight);
    	return "Flight Update Success";
    }
    
    @GetMapping(value="/all")
    public List<FlightModel> viewAllFlight(){
    	List<FlightModel> ancol = flightService.getAllFlightDetail(); 
    	return ancol;
    }
    
   
}