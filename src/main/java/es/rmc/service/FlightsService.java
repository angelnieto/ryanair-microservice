package es.rmc.service;

import es.rmc.model.Route;

public interface FlightsService {
	
	Route[] getRoutes();

	String getInterconnections(String departure,String departureDatetime , String arrival, String arrivalDatetime);
}
