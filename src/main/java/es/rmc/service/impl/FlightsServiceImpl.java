package es.rmc.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.rmc.config.FlightsServiceConfig;
import es.rmc.model.Route;
import es.rmc.model.ScheduledFlights;
import es.rmc.service.FlightsService;

@Service
public class FlightsServiceImpl implements FlightsService {

	private final FlightsServiceConfig config;
	 
    @Autowired 
    public FlightsServiceImpl(FlightsServiceConfig config) {
        this.config = config;
    }
	 
	 @Autowired 
	 RestTemplate restTemplate;
	 
	 private static Logger LOG = LoggerFactory.getLogger(FlightsServiceImpl.class);
	
	@Override
	public String getInterconnections(String departure, String departureDatetime, String arrival,
			String arrivalDatetime) {
		
		return "Interconnections method with parameters";
	}

	private Route[] getRoutes() {
		Route[] response = null;
		
	    try {
		    ResponseEntity<Route[]> responseEntity = restTemplate.exchange(config.getRoutes(), HttpMethod.GET, getHttpRequest(), Route[].class);
	
			if (null != responseEntity && null != responseEntity.getBody() && null != responseEntity.getStatusCode() && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			    response = responseEntity.getBody();
			}
	    } catch (Exception e) {
	    	LOG.error("Exception trying to connect with Ryanair routes endpoint: {}", e.getMessage());
	    }
		
	    return response;
	}

	private HttpEntity<HttpHeaders> getHttpRequest() {
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE); 
		
		//request entity is created with request headers
	    HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(requestHeaders);
		return requestEntity;
	}

	@Override
	public String getFlights(String departure, String departureDatetime, String arrival, String arrivalDatetime) {
		
		String response = null;
		
		Route[] routes = getRoutes();
		if(routes.length > 0) {
			List<Route> routesList = new ArrayList<Route>();
			Collections.addAll(routesList, routes); 
			
			//search for direct fligths
			List<Route> directRoutes = routesList.stream().filter(r -> departure.equalsIgnoreCase(r.getOriginAirport()) && arrival.equalsIgnoreCase(r.getDestinationAirport())).collect(Collectors.toList());
			
			//search for interconnected fligths
			List<Route> validDepartureRoutes = routesList.stream().filter(r -> departure.equalsIgnoreCase(r.getOriginAirport())).collect(Collectors.toList());
			List<Route> validArrivalRoutes = routesList.stream().filter(r -> arrival.equalsIgnoreCase(r.getDestinationAirport())).collect(Collectors.toList());
			
			List<Route> interconnectedRoutes = new ArrayList<>();
			validDepartureRoutes.stream().forEach(d -> {
				validArrivalRoutes.stream().forEach(a -> {
					//if the destination of a route is the origin of other route from the filtered routes, 
					//then a flight between departure and arrival is possible
					if(d.getDestinationAirport().equalsIgnoreCase(a.getOriginAirport())) {
						interconnectedRoutes.add(d);
						interconnectedRoutes.add(a);
					}
				});
			});
			
			ScheduledFlights scheduledDirectFligths = null;
			ScheduledFlights scheduledIndirectFligths = null;
			if(directRoutes.size() == 1) {
				scheduledDirectFligths = getScheduledFlights(directRoutes.get(0), getYear(departureDatetime), getMonth(departureDatetime));
			}
			if(!interconnectedRoutes.isEmpty()) {
				
			}
			
		}
				
		return response;
	}
	
	private ScheduledFlights getScheduledFlights(Route route, int year, int month) {
		ScheduledFlights response = null;
		
		//replace url params for its values
		String scheduledFlightsUrl = config.getFlights();
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{departure}", route.getOriginAirport());
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{arrival}", route.getDestinationAirport());
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{year}", Integer.toString(year));
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{month}", Integer.toString(month));
		
		try {
		    ResponseEntity<ScheduledFlights> responseEntity = restTemplate.exchange(scheduledFlightsUrl, HttpMethod.GET, getHttpRequest(), ScheduledFlights.class);
	
			if (null != responseEntity && null != responseEntity.getBody() && null != responseEntity.getStatusCode() && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			    response = responseEntity.getBody();
			}
	    } catch (Exception e) {
	    	LOG.error("Exception trying to connect with Ryanair routes endpoint: {}", e.getMessage());
	    }
		
	    return response;
	}

	private int getMonth(String dateTime) {
		return getLocalDateTime(dateTime).getMonthValue();    
	}
	
	private int getYear(String dateTime) {
		return getLocalDateTime(dateTime).getYear();    
	}

	private LocalDateTime getLocalDateTime(String dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("UTC"));
		LocalDateTime date = LocalDateTime.parse(dateTime, formatter);
		return date;
	}

}
