package es.rmc.controller.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.rmc.controller.FlightsController;
import es.rmc.exception.FlightsException;
import es.rmc.exception.FlightsException.FlightsExceptionType;
import es.rmc.model.FlightsMatched;
import es.rmc.service.FlightsService;

@RestController
@RequestMapping("/interconnections")
public class FligthsControllerImpl implements FlightsController{
	
	@Autowired
	private FlightsService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FlightsMatched>> getFlights(@RequestParam(required = true) String departure, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDatetime, @RequestParam(required = true) String arrival, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDatetime) {
		
		List<FlightsMatched> response = new ArrayList<>();
		HttpStatus status = HttpStatus.OK;
				
		try {
			response = service.getFlights(departure, departureDatetime, arrival, arrivalDatetime);
								
		} catch (FlightsException e) {
			FlightsMatched item = new FlightsMatched(e.getException().getMessage()); 
			response.add(item);
			if(FlightsExceptionType.COMMUNICATION_ERROR.equals(e.getException())) {
				//Third party server not found
				status = HttpStatus.FAILED_DEPENDENCY;
			} else {
				//Request parameters don't mets prerequisites
				status = HttpStatus.PRECONDITION_FAILED;
			}
			
		}
		return new ResponseEntity<>(response, status);
	}


}
