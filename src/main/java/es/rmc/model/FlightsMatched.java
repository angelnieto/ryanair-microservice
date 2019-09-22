package es.rmc.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

		private String originAirport;
		private String departureDateTime;
		private String destinationAirport;
		private String arrivalDateTime;
		
		  // =========================================== Constructors =========================================
		
		public Leg(String originAirport,  LocalDateTime departureDateTime, String destinationAirport, LocalDateTime arrivalDateTime) {
						
			setOriginAirport(originAirport);
			setDepartureDateTime(departureDateTime);
			setDestinationAirport(destinationAirport);
			setArrivalDateTime(arrivalDateTime);
		}
	
		// =========================================== Getters and setters =========================================

		@JsonGetter("departureAirport")
		public String getOriginAirport() {
			return originAirport;
		}

		public void setOriginAirport(String originAirport) {
			this.originAirport = originAirport;
		}
		
		@JsonGetter("departureDateTime")
		public String getDepartureDateTime() {
			return departureDateTime;
		}

		public void setDepartureDateTime(LocalDateTime departureDateTime) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("UTC"));
			this.departureDateTime = formatter.format(departureDateTime);
		}
		
		@JsonGetter("arrivalAirport")
		public String getDestinationAirport() {
			return destinationAirport;
		}

		public void setDestinationAirport(String destinationAirport) {
			this.destinationAirport = destinationAirport;
		}
		
		@JsonGetter("arrivalDateTime")
		public String getArrivalDateTime() {
			return arrivalDateTime;
		}

		public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("UTC"));
			this.arrivalDateTime = formatter.format(arrivalDateTime);
		}
			
	}

}

