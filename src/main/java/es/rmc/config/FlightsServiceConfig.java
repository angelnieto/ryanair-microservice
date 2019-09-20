package es.rmc.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="flights.service")
public class FlightsServiceConfig {

	@NotNull private String routes;
	@NotNull private String flights;
	 
	// =========================================== Getters and setters =========================================
	  
	public String getRoutes() {
		return routes;
	}
	public void setRoutes(String routes) {
		this.routes = routes;
	}
	public String getFlights() {
		return flights;
	}
	public void setFlights(String flights) {
		this.flights = flights;
	}
}
