package es.rmc.service.impl;

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

import es.rmc.model.Route;
import es.rmc.service.FlightsService;

@Service
public class FlightsServiceImpl implements FlightsService {

	 @Value("${url.routes}")
	 private String routesEndpoint;
	 
	 @Autowired 
	 RestTemplate restTemplate;
	 
	 private static Logger LOG = LoggerFactory.getLogger(FlightsServiceImpl.class);
	
	@Override
	public String getInterconnections(String departure, String departureDatetime, String arrival,
			String arrivalDatetime) {
		
		return "Interconnections method with parameters";
	}

	@Override
	public String getRoutes() {
		String response = null;
		
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE); 
		
		//request entity is created with request headers
	    HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(requestHeaders);

	    try {
		    ResponseEntity<Route[]> responseEntity = restTemplate.exchange(routesEndpoint, HttpMethod.GET, requestEntity, Route[].class);
	
			if (null != responseEntity && null != responseEntity.getBody() && null != responseEntity.getStatusCode() && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
			    response = responseEntity.getBody().toString();
			}
	    } catch (Exception e) {
	    	LOG.error("Exception trying to connect with Ryanair routes endpoint: {}", e.getMessage());
	    }
		
	    return response;
	}

}
