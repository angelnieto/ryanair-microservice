package es.rmc.controller;

import org.springframework.http.ResponseEntity;

/**
 * 
 * 
 * @author rmc
 */
public interface FlightsController {

	public ResponseEntity<String> getInterconnections();
	
	public ResponseEntity<String> getInterconnections(String departure, String arrival);
}
