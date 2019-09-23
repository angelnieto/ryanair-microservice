package es.rmc.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseObject implements Serializable {

	private static final long serialVersionUID = 2385368817988437679L;

	@JsonInclude(Include.NON_NULL)
	private String error;
	
	@JsonInclude(Include.NON_NULL)
	private List<FlightsMatched> flightsMatched;
	
	  // =========================================== Constructors =========================================
	
	public ResponseObject() {
		
	}
	
	public ResponseObject(String message) {
		this.error = message;
	}
	
	public ResponseObject(List<FlightsMatched> flights) {
		this.flightsMatched = flights;
	}
	
	// =========================================== Getters and setters =========================================

	public String getError() {
		return error;
	}

	public List<FlightsMatched> getFlightsMatched() {
		return flightsMatched;
	}

}
