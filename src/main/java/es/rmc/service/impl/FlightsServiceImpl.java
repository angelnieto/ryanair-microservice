package es.rmc.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import es.rmc.config.FlightsServiceConfig;
import es.rmc.exception.FlightsException;
import es.rmc.exception.ThrowingConsumer;
import es.rmc.model.Flight;
import es.rmc.model.FlightsMatched;
import es.rmc.model.FlightsMatched.Leg;
import es.rmc.model.Route;
import es.rmc.model.ScheduledFlights;
import es.rmc.service.FlightsService;
import es.rmc.utils.Constants;

@Service
public class FlightsServiceImpl implements FlightsService {

	// service configuration object
	private final FlightsServiceConfig config;
	 
    @Autowired 
    public FlightsServiceImpl(FlightsServiceConfig config) {
        this.config = config;
    }
	 
	 @Autowired 
	 RestTemplate restTemplate;
	 
	 private static Logger LOG = LoggerFactory.getLogger(FlightsServiceImpl.class);
	
	@Override
	public List<FlightsMatched> getFlights(String departure, LocalDateTime departureDatetime, String arrival, LocalDateTime arrivalDatetime) throws FlightsException {
		
		List<FlightsMatched> response = new ArrayList<>();
		
		// checks if arrivalDatetime is greater than departureDatetime
		if(arrivalDatetime.isAfter(departureDatetime)) {
			
			//checks maximum temporal interval restriction
			if(!arrivalDatetime.isAfter(departureDatetime.plusDays(config.getMaxInterval()))) {
				
				Route[] routes = getRoutes();
				if(routes.length > 0) {
					response = findValidScheduledFlights(departure, departureDatetime, arrival, arrivalDatetime, routes);	
				}
			} else {
				throw new FlightsException(FlightsException.FlightsExceptionType.INVALID_INTERVAL);
			}
		} else {
			throw new FlightsException(FlightsException.FlightsExceptionType.INVALID_DATES);
		}
						
		return response;
	}

	private List<FlightsMatched> findValidScheduledFlights(String departure, LocalDateTime departureDatetime, String arrival,
			LocalDateTime arrivalDatetime, Route[] routes) throws FlightsException {
		
		List<FlightsMatched> response = new ArrayList<>();
		
		List<Route> routesList = new ArrayList<>();
		Collections.addAll(routesList, routes); 
		
		//search for direct fligths
		List<Route> directRoutes = findDirectRoutes( routesList, departure, arrival);
		
		//search for interconnected fligths
		List<Route> interconnectedRoutes = findInterconnections(routesList, departure, arrival);

		if(directRoutes.size() == 1) {
			List<ScheduledFlights> scheduledDirectFligths = findScheduledDirectFligths(directRoutes, departureDatetime, arrivalDatetime);
			
			// Add direct flights to response
			if(!scheduledDirectFligths.isEmpty()) {
				Set<Flight> validFlights = getValidDirectFlights(departureDatetime, arrivalDatetime, scheduledDirectFligths); 
				
				if(!validFlights.isEmpty()) {
					response.add(createFlightsMatched(Constants.STOP.ZERO, validFlights));
				}
			}
		
		}
		if(!interconnectedRoutes.isEmpty()) {
			List<ScheduledFlights> scheduledIndirectFligths = findScheduledIndirectFligths(interconnectedRoutes, departureDatetime, arrivalDatetime);
		
			// Add indirect flights to response
			if(!scheduledIndirectFligths.isEmpty()) {
				Set<Flight> validFlights = getValidInterconnections(departure, departureDatetime, arrivalDatetime,
						scheduledIndirectFligths);
				
				if(!validFlights.isEmpty()) {
					response.add(createFlightsMatched(Constants.STOP.ONE, validFlights));
				}
				
			}
		}
				
		return response;
	}

	private Set<Flight> getValidInterconnections(String departure, LocalDateTime departureDatetime,
			LocalDateTime arrivalDatetime, List<ScheduledFlights> scheduledIndirectFligths) {
		Set<Flight> validFlights = new HashSet<>(); 
		
		Set<Flight> validScheduledFlights = new HashSet<>(); 
		
		//1 : checks if flight datatimes fulfill request datatimes 
		scheduledIndirectFligths.forEach(sif ->
			sif.getFlightDays().forEach(fd -> 
				fd.getFlights().forEach(f -> {
					//checks if flight datatimes fulfill request datatimes 
					if(!f.getDepartureDatetime().isBefore(departureDatetime) && !f.getArrivalDatetime().isAfter(arrivalDatetime)) {
						validScheduledFlights.add(f);
					}
				})
			)
		); 
		// 2 : checks if departure and arrival airports are connected by 2 flights
		if(!validScheduledFlights.isEmpty()) {
			
			validScheduledFlights.forEach(f -> {
				
				//checks if flights from departure airport have at least one pair to arrival airport,
				// and the difference between the arrival and the next departure is 2h or greater  
				if(departure.equalsIgnoreCase(f.getOriginAirport())){
					List<Flight> interconnections = validScheduledFlights.stream().filter(vsf -> f.getDestinationAirport().equalsIgnoreCase(vsf.getOriginAirport()) && !vsf.getDepartureDatetime().isBefore(f.getArrivalDatetime().plusHours(config.getMinInterconnectionHours()))).collect(Collectors.toList());
					if(!interconnections.isEmpty()) {
						// Adds first flight
						validFlights.add(f);
						// Adds second flight
						validFlights.addAll(interconnections);
					}
				}
				
			});
		}
		return validFlights;
	}

	private Set<Flight> getValidDirectFlights(LocalDateTime departureDatetime, LocalDateTime arrivalDatetime,
			List<ScheduledFlights> scheduledDirectFligths) {
		Set<Flight> validFlights = new HashSet<>(); 
		
		scheduledDirectFligths.forEach(sdf ->
			sdf.getFlightDays().forEach(fd -> 
				fd.getFlights().forEach(f -> {
					//checks if flight datatimes fulfill request datatimes 
					if(!f.getDepartureDatetime().isBefore(departureDatetime) && !f.getArrivalDatetime().isAfter(arrivalDatetime)) {
						validFlights.add(f);
					}
				})
			)
		);
		return validFlights;
	}

	private List<ScheduledFlights> findScheduledIndirectFligths(List<Route> interconnectedRoutes,
			LocalDateTime departureDatetime, LocalDateTime arrivalDatetime){
		
		List<ScheduledFlights> scheduledIndirectFligths = new ArrayList<>();
		
//		final ThrowingConsumer<String> throwingConsumer = aps -> {
//			scheduledIndirectFligths.add(getScheduledFlights(indirectRoute, departureDatetime.getYear(), departureDatetime.getMonthValue()));
//			
//			//if arrival date is for next month
//			if(arrivalDatetime.getMonthValue() != departureDatetime.getMonthValue()) {
//				scheduledIndirectFligths.add(getScheduledFlights(indirectRoute, arrivalDatetime.getYear(), arrivalDatetime.getMonthValue()));
//			}
//			
//		    throw new Exception("asdas");
//		};
		
		interconnectedRoutes.forEach(
				(ThrowingConsumer<Route>) indirectRoute -> {
			
					scheduledIndirectFligths.add(getScheduledFlights(indirectRoute, departureDatetime.getYear(), departureDatetime.getMonthValue()));
				
					//if arrival date is for next month
					if(arrivalDatetime.getMonthValue() != departureDatetime.getMonthValue()) {
						scheduledIndirectFligths.add(getScheduledFlights(indirectRoute, arrivalDatetime.getYear(), arrivalDatetime.getMonthValue()));
					}
				});
		return scheduledIndirectFligths;
	}

	private List<ScheduledFlights> findScheduledDirectFligths(List<Route> directRoutes,
			LocalDateTime departureDatetime, LocalDateTime arrivalDatetime) throws FlightsException {
		
		List<ScheduledFlights> scheduledDirectFligths = new ArrayList<>();
		
		scheduledDirectFligths.add(getScheduledFlights(directRoutes.get(0), departureDatetime.getYear(), departureDatetime.getMonthValue()));
		//if arrival date is for next month
		if(arrivalDatetime.getMonthValue() != departureDatetime.getMonthValue()) {
			scheduledDirectFligths.add(getScheduledFlights(directRoutes.get(0), arrivalDatetime.getYear(), arrivalDatetime.getMonthValue()));
		}
		return scheduledDirectFligths;
	}

	private List<Route> findDirectRoutes(List<Route> routesList, String departure, String arrival) {
		return routesList.stream().filter(r -> departure.equalsIgnoreCase(r.getOriginAirport()) && arrival.equalsIgnoreCase(r.getDestinationAirport())).collect(Collectors.toList());
	}

	private List<Route> findInterconnections(List<Route> validRoutes, String departure, String arrival) {
		List<Route> validDepartureRoutes = validRoutes.stream().filter(r -> departure.equalsIgnoreCase(r.getOriginAirport())).collect(Collectors.toList());
		List<Route> validArrivalRoutes = validRoutes.stream().filter(r -> arrival.equalsIgnoreCase(r.getDestinationAirport())).collect(Collectors.toList());
		
		List<Route> interconnectedRoutes = new ArrayList<>();
		validDepartureRoutes.forEach(d -> 
			validArrivalRoutes.forEach(a -> {
				//if the destination of a route is the origin of another route from the filtered routes, 
				//then an interconnection between departure and arrival is possible
				if(d.getDestinationAirport().equalsIgnoreCase(a.getOriginAirport())) {
					interconnectedRoutes.add(d);
					interconnectedRoutes.add(a);
				}
			})
		);
		return interconnectedRoutes;
	}
	
	private FlightsMatched createFlightsMatched(int stops, Set<Flight> flights) {
		FlightsMatched fm = new FlightsMatched();
		
		List<Leg> legs = new ArrayList<>();
		for(Flight flight: flights) {
			Leg leg = fm.new Leg(flight.getOriginAirport(), flight.getDepartureDatetime(), flight.getDestinationAirport(), flight.getArrivalDatetime());
			legs.add(leg);
		}
		//Orders flights by its departure datetime	
		Collections.sort(legs, fm.new SortByDepartureDatetime());
		
		fm.setLegs(legs);
		fm.setStops(stops);
		
		return fm;
	}

	private Route[] getRoutes() throws FlightsException {
		Route[] response = {};
		
	    try {
		    ResponseEntity<Route[]> responseEntity = restTemplate.exchange(config.getRoutes(), HttpMethod.GET, getHttpRequest(), Route[].class);
	
			if (null != responseEntity.getBody() && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				//filter all routes with operator = "RYANAIR" and connectingAirport = null
				response = filterRoutes(responseEntity);
			} 
	    } catch (Exception e) {
	    	LOG.error("Exception trying to connect with Ryanair routes endpoint: {}", e.getMessage());
	    	throw new FlightsException(FlightsException.FlightsExceptionType.COMMUNICATION_ERROR);
	    }
		
	    return response;
	}
	
	private ScheduledFlights getScheduledFlights(Route route, int year, int month) throws FlightsException {
		ScheduledFlights response = null;
		
		//replace url params for its values
		String scheduledFlightsUrl = config.getFlights();
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{departure}", route.getOriginAirport());
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{arrival}", route.getDestinationAirport());
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{year}", Integer.toString(year));
		scheduledFlightsUrl = scheduledFlightsUrl.replace("{month}", Integer.toString(month));
		
		try {
		    ResponseEntity<ScheduledFlights> responseEntity = restTemplate.exchange(scheduledFlightsUrl, HttpMethod.GET, getHttpRequest(), ScheduledFlights.class);
	
			if (null != responseEntity.getBody() && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			    response = responseEntity.getBody();
			    
			    //fills datetime and airport properties in Flight object
			    response.getFlightDays().forEach(fd -> 
			    	fd.getFlights().forEach(f -> {
			    		f.setDepartureDatetime(getYear(year), getMonth(month),  getDay(fd.getDay()));
			    		f.setArrivalDatetime(getYear(year), getMonth(month),  getDay(fd.getDay()));
			    		f.setOriginAirport(route.getOriginAirport());
			    		f.setDestinationAirport(route.getDestinationAirport());
			    	})
			    );
			    
			}
	    } catch (Exception e) {
	    	LOG.error("Exception trying to connect with Ryanair schedules endpoint: {}", e.getMessage());
//	    	throw new FlightsException(FlightsException.FlightsExceptionType.COMMUNICATION_ERROR);
	    }
		
	    return response;
	}

	private String getYear(int year) {
		return Integer.toString(year);
	}
	
	private String getMonth(int month) {
		StringBuilder sb = new StringBuilder();
		if(month < 10) {
			sb.append("0");    
		}
		return sb.append(Integer.toString(month)).toString();
	}
	
	private String getDay(int day) {
		StringBuilder sb = new StringBuilder();
		if(day < 10) {
			sb.append("0");    
		}
		return sb.append(Integer.toString(day)).toString();
	}
	
	private Route[] filterRoutes(ResponseEntity<Route[]> responseEntity) {
		List<Route> routesFiltered = Arrays.asList(responseEntity.getBody()).stream().filter(route -> config.getOperator().equalsIgnoreCase(route.getOperator()) && route.getConnectingAirport() == null).collect(Collectors.toList());
		
		return routesFiltered.toArray(new Route[routesFiltered.size()]);
	}

	private HttpEntity<HttpHeaders> getHttpRequest() {
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE); 
		
		//request entity is created with request headers
		return  new HttpEntity<>(requestHeaders);
	}

}
