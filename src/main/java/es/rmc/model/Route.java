package es.rmc.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Object that maps responses received from 
 * Routes API: https://services-api.ryanair.com/locate/3/routes endpoint
 * 
 * @author rmc
 *
 */
public class Route implements Serializable{

	private static final long serialVersionUID = -1893336294674637252L;

	// =========================================== Fields =========================================
	// JSON properties
	private String originAirport;
	private String destinationAirport;
	private String connectingAirport;
	private boolean newRoute;
	private boolean seasonalRoute;
	private String operator;
	private GroupType group;
	private List<String> similarAirports;
	private List<String> tags;
	private String carrierCode;
	
	private enum GroupType {
		  CANARY,
		  CITY,
		  DOMESTIC,
		  ETHNIC,
		  GENERIC,
		  LEISURE,
		  SUN,
		  UKP
	}
	
	  // =========================================== Constructors =========================================

    public Route() {
        super();
    }

	
	// =========================================== Getters and setters =========================================

    public String getOriginAirport() {
		return originAirport;
	}
    
    @JsonSetter("airportFrom")
	public void setOriginAirport(String originAirport) {
		this.originAirport = originAirport;
	}

	public String getDestinationAirport() {
		return destinationAirport;
	}

	@JsonSetter("airportTo")
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	public String getConnectingAirport() {
		return connectingAirport;
	}

	public void setConnectingAirport(String connectingAirport) {
		this.connectingAirport = connectingAirport;
	}

	public boolean isNewRoute() {
		return newRoute;
	}

	public void setNewRoute(boolean newRoute) {
		this.newRoute = newRoute;
	}

	public boolean isSeasonalRoute() {
		return seasonalRoute;
	}

	public void setSeasonalRoute(boolean seasonalRoute) {
		this.seasonalRoute = seasonalRoute;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public GroupType getGroup() {
		return group;
	}

	public void setGroup(GroupType group) {
		this.group = group;
	}

	public List<String> getSimilarAirports() {
		return similarAirports;
	}

	@JsonSetter("similarArrivalAirportCodes")
	public void setSimilarAirports(List<String> similarAirports) {
		this.similarAirports = similarAirports;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

    // =========================================== Serialization =========================================

	/* (non-Javadoc)
     * 
     * @see java.lang.Object#toString() */
    @Override
    public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Route [originAirport=");
		builder.append(originAirport);
		builder.append(", destinationAirportn=");
		builder.append(destinationAirport);
		builder.append(", connectingAirport=");
		builder.append(connectingAirport);
		builder.append(", newRoute=");
		builder.append(newRoute);
		builder.append(", seasonalRoute=");
		builder.append(seasonalRoute);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", group=");
		builder.append(group);
		builder.append(", similarAirports=");
		builder.append(similarAirports);
		builder.append(", tags=");
		builder.append(tags);
		builder.append(", carrierCode=");
		builder.append(carrierCode);
		builder.append("]");
		return builder.toString();
    }
}

