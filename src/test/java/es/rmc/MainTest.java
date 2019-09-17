package es.rmc;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import es.rmc.model.Route;
import es.rmc.service.FlightsService;
import es.rmc.service.impl.FlightsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@TestPropertySource(locations="classpath:test.properties")
//@AutoConfigureMockMvc
public class MainTest {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private FlightsService flightsService;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @Value("${server.servlet.resource-path}")
    private String resourcePath;
    
    @Value("${departure1.iata}")
    private String departure1;
    
    @Value("${departure1.datetime1}")
    private String departure1Datetime;
    
    @Value("${arrival1.iata}")
    private String arrival1;
    
    @Value("${arrival1.datetime1}")
    private String arrival1Datetime;
    
    @Value("${routes.filepath}")
    private String routesFilePath;
    
	 @Value("${url.routes}")
	 private String routesEndpoint;
	 
    private static final String DEPARTURE = "departure", ARRIVAL = "arrival", DEPARTURE_DATETIME = "departureDatetime", ARRIVAL_DATETIME = "arrivalDatetime" ; 

    private static Logger LOG = LoggerFactory.getLogger(FlightsServiceImpl.class);
    
    private Route[] routes = null;
    private String jsonRoutes = null;
    private MockRestServiceServer mockServer;
    
    @Test
    public void getInterconnectionsForDate1() throws Exception {
    	
    	mockServer.expect(ExpectedCount.once(), 
    	          requestTo(new URI(routesEndpoint)))
    	          .andExpect(method(HttpMethod.GET))
    	          .andRespond(withStatus(HttpStatus.OK)
    	          .contentType(MediaType.APPLICATION_JSON)
    	          .body(jsonRoutes));
    	
    	Route[] routes = flightsService.getRoutes();
     	
    }
    
    @Before
    public void setup() {
    	    	
    	mockServer = MockRestServiceServer.createServer(restTemplate);
    	
    	try(BufferedReader br = new BufferedReader(new FileReader(getFileFromResources(routesFilePath))))
    	{
    		jsonRoutes = readAllLinesWithStream(br);
			routes = new Gson().fromJson(jsonRoutes, Route[].class);
			
		} catch (FileNotFoundException e) {
			LOG.error("File {} not found", routesFilePath);
		} catch (IOException e) {
			LOG.error("File {} not correctly formatted", routesFilePath);
		}				
			  

		LOG.debug("Routes size {}", routes.length);
    }
    
    private String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines()
          .collect(Collectors.joining(System.lineSeparator()));
    }
    
    // get file from classpath, resources folder
    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

}
