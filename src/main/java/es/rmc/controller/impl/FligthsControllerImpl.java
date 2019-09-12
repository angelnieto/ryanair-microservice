package es.rmc.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.rmc.controller.FlightsController;
import es.rmc.service.FlightsService;

@RestController
@RequestMapping("/interconnections")
public class FligthsControllerImpl implements FlightsController{
	
	@Autowired
	private FlightsService service;

	@GetMapping()
	public ResponseEntity<String> getInterconnections() {
		return  new ResponseEntity<>("Interconnections method", HttpStatus.OK);
	}

	@GetMapping(params = {"departure", "arrival"})
	public ResponseEntity<String> getInterconnections(@RequestParam(required = true) String departure, @RequestParam(required = true) String departureDatetime, @RequestParam(required = true) String arrival, @RequestParam(required = true) String arrivalDatetime) {
		
		String response = service.getInterconnections(departure, departureDatetime, arrival, arrivalDatetime);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}


}
