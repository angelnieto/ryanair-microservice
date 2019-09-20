package es.rmc.service;

public interface FlightsService {
	
	String getInterconnections(String departure,String departureDatetime , String arrival, String arrivalDatetime);

	String getFlights(String departure, String departureDatetime, String arrival, String arrivalDatetime);
}
