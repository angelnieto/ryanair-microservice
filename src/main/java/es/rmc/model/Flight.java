package es.rmc.model;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object that maps responses received from 
 * Routes API: https://servicesapi.ryanair.com/timtbl/3/schedules/{departure}/{arrival}/years/{year}/months/{month} endpoint
 * 
 * @author rmc
 *
 */
public class Flight implements Serializable{

	private static final long serialVersionUID = -2378754017125516877L;

	@JsonProperty("number")
	private Integer number;
	
	@JsonProperty("departureTime")
	private String departureTime;
	
	@JsonProperty("arrivalTime")
	private String arrivalTime;
	
	
	  // =========================================== Constructors =========================================

    public Flight() {
        super();
    }

	
	// =========================================== Getters and setters =========================================
	public Integer getNumber() {
		return number;
	}


	public String getDepartureTime() {
		return departureTime;
	}


	public String getArrivalTime() {
		return arrivalTime;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}


	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}


	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

    // =========================================== Serialization =========================================

	/* (non-Javadoc)
     * 
     * @see java.lang.Object#toString() */
    @Override
    public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Flight [number=");
		builder.append(number);
		builder.append(", departureTime=");
		builder.append(departureTime);
		builder.append(", arrivalTime=");
		builder.append(arrivalTime);
		builder.append("]");
		return builder.toString();
    }
}

