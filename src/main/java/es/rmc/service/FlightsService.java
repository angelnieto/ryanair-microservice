package es.rmc.service;

public interface FlightsService {
	
	String getRoutes();

	String getInterconnections(String departure,String departureDatetime , String arrival, String arrivalDatetime);
}
