package es.rmc.controller.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.rmc.controller.FlightsController;
import es.rmc.exception.FlightsException;
import es.rmc.model.ResponseObject;
import es.rmc.service.FlightsService;

@RestController
@RequestMapping("/interconnections")
public class FligthsControllerImpl implements FlightsController{
	
	@Autowired
	private FlightsService service;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObject> getFlights(@RequestParam(required = true) String departure, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDatetime, @RequestParam(required = true) String arrival, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDatetime) {
		
		ResponseObject response = null;
		HttpStatus status = HttpStatus.OK;
		try {
			String responseString = new Gson().toJson(service.getFlights(departure, departureDatetime, arrival, arrivalDatetime));
								
		} catch (FlightsException e) {
			response = new ResponseObject(e.getException().getMessage());
			status = HttpStatus.PRECONDITION_FAILED;
		}
		return new ResponseEntity<>(response, status);
	}


}
