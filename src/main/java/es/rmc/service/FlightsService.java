package es.rmc.service;

import java.time.LocalDateTime;
import java.util.List;

import es.rmc.exception.FlightsException;
import es.rmc.model.FlightsMatched;

public interface FlightsService {
	
	List<FlightsMatched> getFlights(String departure, LocalDateTime departureDatetime, String arrival, LocalDateTime arrivalDatetime) throws FlightsException;
}
