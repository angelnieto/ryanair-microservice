package es.rmc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Object that maps responses received from 
 * Routes API: https://services-api.ryanair.com/locate/3/routes endpoint
 * 
 * @author rmc
 *
 */
public class Route implements Serializable{

	@JsonProperty("airportFrom")
	private String originAirport;
	
	@JsonProperty("airportTo")
	private String destinationAirport;
	
	@JsonProperty("connectingAirport")
	private String connectingAirport;
	
	@JsonProperty("newRoute")
	private boolean newRoute;
	
	@JsonProperty("seasonalRoute")
	private boolean seasonalRoute;
	
	@JsonProperty("operator")
	private String operator;
	
	@JsonProperty("group")
	private GroupType group;
	
	@JsonProperty("similarArrivalAirportCodes")
	private List<String> similarAirports;
	
	@JsonProperty("tags")
	private List<String> tags;
	
	@JsonProperty("carrierCode")
	private String carrierCode;
	
	  // =========================================== Constructors =========================================

    public Route() {
        super();
    }

	
	// =========================================== Getters and setters =========================================

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

