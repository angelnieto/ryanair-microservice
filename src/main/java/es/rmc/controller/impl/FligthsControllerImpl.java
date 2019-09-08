package es.rmc.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.rmc.controller.FlightsController;

@RestController
@RequestMapping("/interconnections")
public class FligthsControllerImpl implements FlightsController{

	@GetMapping()
	public ResponseEntity<String> getInterconnections() {
		return  new ResponseEntity<String>("Interconnections method", HttpStatus.OK);
	}

	@GetMapping(params = {"departure", "arrival"})
	public ResponseEntity<String> getInterconnections(@RequestParam(required = true) String departure, @RequestParam(required = true) String arrival) {
		
		return new ResponseEntity<String>("Interconnections method with parameters", HttpStatus.OK);
	}


}
