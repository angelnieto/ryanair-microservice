package es.rmc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Object that maps responses received from 
 * Routes API: https://servicesapi.ryanair.com/timtbl/3/schedules/{departure}/{arrival}/years/{year}/months/{month} endpoint
 * 
 * @author rmc
 *
 */
public class ScheduledFlights implements Serializable{

	private static final long serialVersionUID = 2693444226572531664L;

	@JsonProperty("month")
	private Integer month;
	
	@JsonProperty("days")
	private List<FlightsAtDay> flightDays;
	
	
	  // =========================================== Constructors =========================================

    public ScheduledFlights() {
        super();
    }

	
	// =========================================== Getters and setters =========================================
    
    public Integer getMonth() {
		return month;
	}


	public List<FlightsAtDay> getFlightDays() {
		return flightDays;
	}


	public void setMonth(Integer month) {
		this.month = month;
	}


	public void setFlightDays(List<FlightsAtDay> flightDays) {
		this.flightDays = flightDays;
	}
 
    // =========================================== Serialization =========================================

	/* (non-Javadoc)
     * 
     * @see java.lang.Object#toString() */
    @Override
    public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScheduledFlights [month=");
		builder.append(month);
		builder.append(", flightDays=");
		builder.append(flightDays);
		builder.append("]");
		return builder.toString();
    }
}

