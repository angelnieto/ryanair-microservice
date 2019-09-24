package es.rmc.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import es.rmc.model.FlightsMatched;
import es.rmc.utils.Constants.TAGS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller that manages all requests to /interconnections resource
 * 
 * @author rmc
 */
@Api(value = "Flights API Controller", produces = MediaType.APPLICATION_JSON_VALUE, tags = { TAGS.FLIGHTS })
public interface FlightsController {

	/**  Endpoint that recovers all scheduled flights for Ryanair operator between 2 airports for a given time interval
	 *  
	 *  @param departure : origin airport
	 *  @param departureDatetime : start of time interval
	 *  @param arrival : destination airport
	 *  @param arrivalDatetime : end of time interval
	 *  @return {@link ResponseEntity} response for the microservice
	 *  
	 */
	@ApiOperation(value = "Returns all available flights between departure and arrival airports for a given time interval", tags = { TAGS.FLIGHTS })
	@ApiResponses({ @ApiResponse(code = 200 , message = "Success" ), @ApiResponse(code = 412, message = "Prerequisites not met"), @ApiResponse(code = 424, message = "Third party resources error") })
	ResponseEntity<List<FlightsMatched>> getFlights(@ApiParam(value = "Departure airport IATA code", required = true) String departure, @ApiParam(value = "Departure datetime", required = true) LocalDateTime departureDatetime , @ApiParam(value = "Arrival airport IATA code", required = true) String arrival, @ApiParam(value = "Arrival datetime", required = true) LocalDateTime arrivalDatetime);
}
