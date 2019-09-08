package es.rmc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
@SpringBootTest(classes = App.class)
@TestPropertySource(locations="classpath:test.properties")
@AutoConfigureMockMvc
public class AppTest {

    @Autowired
    private MockMvc mvc;
    
    @Value("${server.servlet.context-path}")
    private String contextPath;
    
    @Value("${server.servlet.resource-path}")
    private String resourcePath;
    
    @Value("${departure.iata}")
    private String departure;
    
    @Value("${arrival.iata}")
    private String arrival;
    
    private static String DEPARTURE = "departure", ARRIVAL = "arrival"; 

    @Test
    public void helloGradle() throws Exception {
    	
    	MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();
    	requestParams.add(DEPARTURE, departure);
    	requestParams.add(ARRIVAL, arrival);
    	
       	MvcResult result =  mvc.perform(get(resourcePath).params(requestParams))
       			.andExpect(status().isOk()).andReturn();
       	
       	String content = result.getResponse().getContentAsString();
       	
    	System.out.println(content);
    	
    }

}
