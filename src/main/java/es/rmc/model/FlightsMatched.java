package es.rmc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Object that maps responses created by Ryanair microservice 
 *
 * @author rmc
 *
 */
public class FlightsMatched implements Serializable{

	
	private static final long serialVersionUID = 4194687545366946410L;

	private Integer stops;

	private List<Leg> legs;
	

	  // =========================================== Constructors =========================================
	public FlightsMatched() {
		super();
	}
	
	// =========================================== Getters and setters =========================================

		public Integer getStops() {
			return stops;
		}


		public void setStops(Integer stops) {
			this.stops = stops;
		}


		public List<Leg> getLegs() {
			return legs;
		}

		public void setLegs(List<Leg> legs) {
			this.legs = legs;
		}
		
		// =========================================== Serialization =========================================

		/* (non-Javadoc)
	     * 
	     * @see java.lang.Object#toString() */
	    @Override
	    public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("FlightsMatched [stops=");
			builder.append(stops);
			builder.append(", legs=");
			builder.append(legs);
			builder.append("]");
			return builder.toString();
	    }

	
	public class Leg implements Serializable {
		
		private static final long serialVersionUID = -15775660502978437L;

		@JsonProperty("departureAirport")
		private String originAirport;
		
		@JsonProperty("departureDateTime")
		private LocalDateTime departureDateTime;
		
		@JsonProperty("arrivalAirport")
		private String destinationAirport;
		
		@JsonProperty("arrivalDateTime")
		private LocalDateTime arrivalDateTime;
		
		  // =========================================== Constructors =========================================
		
		public Leg(String originAirport,  LocalDateTime departureDateTime, String destinationAirport, LocalDateTime arrivalDateTime) {
			this.originAirport = originAirport;
			this.departureDateTime = departureDateTime;
			this.destinationAirport = destinationAirport;
			this.arrivalDateTime = arrivalDateTime;
		}
	
		// =========================================== Getters and setters =========================================

		public String getOriginAirport() {
			return originAirport;
		}

		public void setOriginAirport(String originAirport) {
			this.originAirport = originAirport;
		}

		public LocalDateTime getDepartureDateTime() {
			return departureDateTime;
		}

		public void setDepartureDateTime(LocalDateTime departureDateTime) {
			this.departureDateTime = departureDateTime;
		}

		public String getDestinationAirport() {
			return destinationAirport;
		}

		public void setDestinationAirport(String destinationAirport) {
			this.destinationAirport = destinationAirport;
		}

		public LocalDateTime getArrivalDateTime() {
			return arrivalDateTime;
		}

		public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
			this.arrivalDateTime = arrivalDateTime;
		}
			
	}

}

