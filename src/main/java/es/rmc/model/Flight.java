package es.rmc.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Object that maps responses received from 
 * Routes API: https://servicesapi.ryanair.com/timtbl/3/schedules/{departure}/{arrival}/years/{year}/months/{month} endpoint
 * 
 * @author rmc
 *
 */
public class Flight implements Serializable{

	private static final long serialVersionUID = -2378754017125516877L;

	// =========================================== Fields =========================================
	
	// JSON properties
	private Integer number;
	private String departureTime;
	private String arrivalTime;
	
	// Internal properties
	private String originAirport;
	private String destinationAirport;
	private LocalDateTime departureDatetime;
	private LocalDateTime arrivalDatetime;
	
	
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

	public LocalDateTime getDepartureDatetime() {
		return departureDatetime;
	}


	public void setDepartureDatetime(String year, String month, String day) {
		String departureDatetimeStr = new StringBuilder(year).append("-").append(month).append("-").append(day).append(" ").append(departureTime).toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		this.departureDatetime = LocalDateTime.parse(departureDatetimeStr, formatter);
	}
	
	public LocalDateTime getArrivalDatetime() {
		return arrivalDatetime;
	}

	public void setArrivalDatetime(String year, String month, String day) {
		String arrivalDatetimeStr = new StringBuilder(year).append("-").append(month).append("-").append(day).append(" ").append(arrivalTime).toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime calculatedDatetime = LocalDateTime.parse(arrivalDatetimeStr, formatter); 
		
		//checks if arrival date is the same as departure date
		if(departureDatetime != null && !calculatedDatetime.isBefore(departureDatetime)) {
			this.arrivalDatetime = calculatedDatetime;
		} else { //else adds 1 day to arrival datetime object
			this.arrivalDatetime = calculatedDatetime.plusDays(1);
		}
	}
	
	public String getOriginAirport() {
		return originAirport;
	}


	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}


	public String getDestinationAirport() {
		return destinationAirport;
	}


	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
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
		builder.append(", originAirport=");
		builder.append(originAirport);
		builder.append(", destinationAirport=");
		builder.append(destinationAirport);
		builder.append(", departureDatetime=");
		builder.append(departureDatetime);
		builder.append(", arrivalDatetime=");
		builder.append(arrivalDatetime);
		builder.append("]");
		return builder.toString();
    }
}

