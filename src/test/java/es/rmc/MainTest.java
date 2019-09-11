package es.rmc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class MainTest {

    @Autowired
    private MockMvc mvc;
    
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
    
    private static final String DEPARTURE = "departure", ARRIVAL = "arrival", DEPARTURE_DATETIME = "departureDatetime", ARRIVAL_DATETIME = "arrivalDatetime" ; 

    @Test
    public void getInterconnectionsForDate1() throws Exception {
    	
    	MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
    	requestParams.add(DEPARTURE, departure1);
    	requestParams.add(ARRIVAL, arrival1);
    	requestParams.add(DEPARTURE, departure1);
    	requestParams.add(ARRIVAL, arrival1);
    	
       	MvcResult result =  mvc.perform(get(resourcePath).params(requestParams))
       			.andExpect(status().isOk()).andReturn();
       	
       	String content = result.getResponse().getContentAsString();
       	
    	System.out.println(content);
    	
    }

}
