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
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import es.rmc.exception.FlightsException;
import es.rmc.model.FlightsMatched;
import es.rmc.service.FlightsService;
import es.rmc.service.impl.FlightsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@TestPropertySource(locations="classpath:test.properties")
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
    
//    @Value("${departure1.datetime1}")
    @Value("#{T(java.time.LocalDateTime).parse('${departure1.datetime1}')}")
    private LocalDateTime departure1Datetime;
    
    @Value("${arrival1.iata}")
    private String arrival1;
    
//    @Value("${arrival1.datetime1}")
    @Value("#{T(java.time.LocalDateTime).parse('${arrival1.datetime1}')}")
    private LocalDateTime arrival1Datetime;
    
   	 @Value("${url.routes}")
	 private String routesEndpoint;
	
   	 @Value("${routes.filepath}")
     private String routesFilePath;
   	 
   	 
	 @Value("${url.MAD.IBZ}")
	 private String scheduledFlights1Endpoint;
	 
	 @Value("${flights.MAD.IBZ}")
	 private String scheduledFlights1FilePath;
	 	 
	 @Value("${url.MAD.MAN}")
	 private String scheduledFlights2Endpoint;
	 
	 @Value("${flights.MAD.MAN}")
	 private String scheduledFlights2FilePath;
	 
	 @Value("${url.MAN.IBZ}")
	 private String scheduledFlights3Endpoint;
	 
	 @Value("${flights.MAN.IBZ}")
	 private String scheduledFlights3FilePath;
	 
	 @Value("${url.MAD.MRS}")
	 private String scheduledFlights4Endpoint;
	 
	 @Value("${flights.MAD.MRS}")
	 private String scheduledFlights4FilePath;
	 
	 @Value("${url.MRS.IBZ}")
	 private String scheduledFlights5Endpoint;
	 
	 @Value("${flights.MRS.IBZ}")
	 private String scheduledFlights5FilePath;

	 
    //private static final String DEPARTURE = "departure", ARRIVAL = "arrival", DEPARTURE_DATETIME = "departureDatetime", ARRIVAL_DATETIME = "arrivalDatetime" ; 
  
    
    private MockRestServiceServer mockServer;
    
    @Test
    public void getInterconnectionsForDate1() throws FlightsException {
    	
    	List<FlightsMatched> response = flightsService.getFlights(departure1, departure1Datetime, arrival1, arrival1Datetime);
    	
    	Assert.assertNotNull(response);
     	
    }
    
    @Before
    public void setup() {
    	    	
    	mockServer = MockRestServiceServer.createServer(restTemplate);
    	
    	readJsonFile(routesFilePath, routesEndpoint);
    	readJsonFile(scheduledFlights1FilePath, scheduledFlights1Endpoint);
    	readJsonFile(scheduledFlights2FilePath, scheduledFlights2Endpoint);    	
    	readJsonFile(scheduledFlights3FilePath, scheduledFlights3Endpoint); 
    	readJsonFile(scheduledFlights4FilePath, scheduledFlights4Endpoint); 
    	readJsonFile(scheduledFlights5FilePath, scheduledFlights5Endpoint); 
  	}
    
    private void readJsonFile(String filePath, String endpoint) {
    	
    	try(BufferedReader br = new BufferedReader(new FileReader(getFileFromResources(filePath))))
    	{
    		String jsonRoutes = readAllLinesWithStream(br);
			
			mockServer.expect(ExpectedCount.once(), 
		  	          requestTo(new URI(endpoint)))
		  	          .andExpect(method(HttpMethod.GET))
		  	          .andRespond(withStatus(HttpStatus.OK)
		  	          .contentType(MediaType.APPLICATION_JSON)
		  	          .body(jsonRoutes));
			
    	} catch (FileNotFoundException e) {
			Assert.fail(String.format("File %s not found", filePath));
		} catch (IOException e) {
			Assert.fail(String.format("File %s not correctly formatted", filePath));
		} catch (URISyntaxException e) {
			Assert.fail(String.format("Url %s not correctly formatted", endpoint));
		}
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
