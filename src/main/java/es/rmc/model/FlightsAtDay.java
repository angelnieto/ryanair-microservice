package es.rmc.model;

import java.io.Serializable;
import java.util.List;

/**
 * Object that maps responses received from 
 * Routes API: https://servicesapi.ryanair.com/timtbl/3/schedules/{departure}/{arrival}/years/{year}/months/{month} endpoint
 * 
 * @author rmc
 *
 */
public class FlightsAtDay implements Serializable{

	private static final long serialVersionUID = -8966317557993604523L;

	// JSON properties
	private Integer day;
	private List<Flight> flights;
	
	
	  // =========================================== Constructors =========================================

    public FlightsAtDay() {
        super();
    }

	
	// =========================================== Getters and setters =========================================

	public Integer getDay() {
		return day;
	}


	public List<Flight> getFlights() {
		return flights;
	}


	public void setDay(Integer day) {
		this.day = day;
	}


	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

    // =========================================== Serialization =========================================

	/* (non-Javadoc)
     * 
     * @see java.lang.Object#toString() */
    @Override
    public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FlightDays [day=");
		builder.append(day);
		builder.append(", flights=");
		builder.append(flights);
		builder.append("]");
		return builder.toString();
    }
    
    
}

