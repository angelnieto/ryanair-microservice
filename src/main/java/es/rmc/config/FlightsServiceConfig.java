package es.rmc.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class to load application properties
 * 
 * @author rmc
 *
 */
@Configuration
@ConfigurationProperties(prefix="flights.service")
public class FlightsServiceConfig {

	// =========================================== Fields =========================================
	
	/**  url to recover aerial routes  **/
	@NotNull private String routes;
	
	/**  url to recover scheduled flights at Ryanair  **/
	@NotNull private String flights;
	
	/**  maximum time between departure and arrival  **/
	@NotNull private int maxInterval;
	
	/**  minimum time between interconnected flights  **/
	@NotNull private int minInterconnectionHours;
	
	/**  aerial operator name  **/
	@NotNull private String operator;
	 
	// =========================================== Getters and setters =========================================
	  
	public String getRoutes() {
		return routes;
	}
	public String getFlights() {
		return flights;
	}
	public int getMaxInterval() {
		return maxInterval;
	}
	public void setRoutes(String routes) {
		this.routes = routes;
	}
	public void setFlights(String flights) {
		this.flights = flights;
	}
	public void setMaxInterval(int maxInterval) {
		this.maxInterval = maxInterval;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getMinInterconnectionHours() {
		return minInterconnectionHours;
	}
	public void setMinInterconnectionHours(int minInterconnectionHours) {
		this.minInterconnectionHours = minInterconnectionHours;
	}

}
