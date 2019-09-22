package es.rmc.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="flights.service")
public class FlightsServiceConfig {

	@NotNull private String routes;
	@NotNull private String flights;
	@NotNull private int maxInterval;
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

}
