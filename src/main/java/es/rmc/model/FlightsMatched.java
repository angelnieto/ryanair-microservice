package es.rmc.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * Object that maps responses obtained from Ryanair microservice 
 *
 * @author rmc
 *
 */
public class FlightsMatched implements Serializable{

	
	private static final long serialVersionUID = 4194687545366946410L;

	// JSON properties
	@JsonInclude(Include.NON_NULL)
	private Stop stops;
	
	@JsonInclude(Include.NON_NULL)
	private List<Leg> legs;
	
	@JsonInclude(Include.NON_NULL)
	private String  error;
	

	  // =========================================== Constructors =========================================
	public FlightsMatched() {
		super();
	}
	
	public FlightsMatched(String message) {
		this.error = message;
	}
	
	// =========================================== Getters and setters =========================================

		public Integer getStops() {
			Integer stopsIntValue = null;
			
			//stops == null when an exception is thrown
			if(this.stops != null) {
				stopsIntValue = stops.getValue();
			}
			return stopsIntValue;
		}


		public void setStops(Stop stops) {
			this.stops = stops;
		}


		public List<Leg> getLegs() {
			return legs;
		}

		public void setLegs(List<Leg> legs) {
			this.legs = legs;
		}
		
		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
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

	    
	 /** Enum for stops values */   
	 public enum Stop {
			  ZERO(0),
			  ONE(1);
		 
		 	private Integer value;
			  
		  // =========================================== Constructors =========================================

		    private Stop(Integer value) {
			   this.value = value;
		    }
		    
		    // =========================================== Getters and setters =========================================
		    public Integer getValue() {
				return this.value;
			}
		    
		    public void setValue(Integer value) {
				this.value = value;
			}
		    		    
	}
	
	 
	 
	 /** Class for leg objects */ 
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
	
	// =========================================== Methods =========================================
	public class SortByDepartureDatetime implements Comparator<Leg> 
	{ 
	    // Used for sorting in ascending order of departure datetime 
	    public int compare(Leg a, Leg b) 
	    { 
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("UTC"));
	        return LocalDateTime.parse(a.getDepartureDateTime(), formatter).compareTo(LocalDateTime.parse(b.getDepartureDateTime(), formatter)); 
	    } 
	} 

}

