package es.rmc.service.impl;

import org.springframework.stereotype.Service;

import es.rmc.service.FlightsService;

@Service
public class FlightsServiceImpl implements FlightsService {

	@Override
	public String getInterconnections(String departure, String departureDatetime, String arrival,
			String arrivalDatetime) {
		
		return "Interconnections method with parameters";
	}

}
