package es.rmc.controller;

import org.springframework.http.ResponseEntity;

import es.rmc.utils.Constants.TAGS;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * 
 * @author rmc
 */
public interface FlightsController {

	ResponseEntity<String> getInterconnections();
	
	@ApiOperation(value = "Devuelve el listado de fondos", tags = { TAGS.FONDO })
	@ApiResponses({ @ApiResponse(code = 200, message = "El listado de fondos ha sido devuelto"), @ApiResponse(code = 204, message = "No se han encontrado fondos") })
	ResponseEntity<String> getInterconnections(@ApiParam(value = "Departure airport IATA code", required = true)String departure, @ApiParam(value = "Departure datetime", required = true)String departureDatetime , @ApiParam(value = "Arrival airport IATA code", required = true)String arrival, @ApiParam(value = "Arrival datetime", required = true)String arrivalDatetime);
}
