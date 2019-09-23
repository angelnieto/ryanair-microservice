package es.rmc.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
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
 * 
 * 
 * @author rmc
 */
@Api(value = "Flights API Controller", produces = MediaType.APPLICATION_JSON_VALUE, tags = { TAGS.FLIGHTS })
public interface FlightsController {


	@ApiOperation(value = "Returns all available flights between departure and arrival airports for a given temporal interval", tags = { TAGS.FLIGHTS })
	@ApiResponses({ @ApiResponse(code = 200, response = FlightsMatched.class , message = "Success" ), @ApiResponse(code = 412, message = "Prerequisites not met") })
	ResponseEntity<List<FlightsMatched>> getFlights(@ApiParam(value = "Departure airport IATA code", required = true) String departure, @ApiParam(value = "Departure datetime", required = true) LocalDateTime departureDatetime , @ApiParam(value = "Arrival airport IATA code", required = true) String arrival, @ApiParam(value = "Arrival datetime", required = true) LocalDateTime arrivalDatetime);
}
