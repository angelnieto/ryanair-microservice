package es.rmc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class SpringConfig {

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
}
