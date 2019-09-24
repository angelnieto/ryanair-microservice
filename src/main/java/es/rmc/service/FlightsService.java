package es.rmc.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import es.rmc.exception.FlightsException;
import es.rmc.model.FlightsMatched;

/**
 * Service that manages searches for scheduled flights of Ryanair operator for a given interval
 * 
 * @author rmc
 */
public interface FlightsService {
	
	/**  method that recovers all scheduled flights for Ryanair operator between 2 airports for a given time interval
	 *  
	 *  @param departure : origin airport
	 *  @param departureDatetime : start of time interval
	 *  @param arrival : destination airport
	 *  @param arrivalDatetime : end of time interval
	 *  @return {@link ResponseEntity} response for the microservice
	 *  @throws {@link FlightsException} customized exception
	 *  
	 */
	List<FlightsMatched> getFlights(String departure, LocalDateTime departureDatetime, String arrival, LocalDateTime arrivalDatetime) throws FlightsException;
}
