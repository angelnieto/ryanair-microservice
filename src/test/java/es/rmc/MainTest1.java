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
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import es.rmc.config.TestSettings;
import es.rmc.exception.FlightsException;
import es.rmc.model.FlightsMatched;
import es.rmc.service.FlightsService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Main.class, TestSettings.class })
@TestPropertySource("classpath:test.properties")
public class MainTest1 {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FlightsService flightsService;

	@Autowired
	private TestSettings config;

	private MockRestServiceServer mockServer;

	/** Test for getting one only direct flight
	 * 
	 * @throws FlightsException
	 */
	@Test
	public void testDirectFlight() throws FlightsException {

		List<FlightsMatched> response = flightsService.getFlights(config.getDeparture1(), config.getDatetime1(),
				config.getArrival1(), config.getDatetime2());

		Assert.assertTrue(response.size() == 1);
		Assert.assertTrue(response.get(0).getStops() == 0);
	}

	/** Test for getting one only interconnection
	 * 
	 * @throws FlightsException
	 */
	@Test
	public void testInterconnection() throws FlightsException {

		List<FlightsMatched> response = flightsService.getFlights(config.getDeparture1(), config.getDatetime3(),
				config.getArrival1(), config.getDatetime4());

		Assert.assertTrue(response.size() == 1);
		Assert.assertTrue(response.get(0).getStops() == 1);
	}

	/** Test for getting no flights
	 * 
	 * @throws FlightsException
	 */
	@Test
	public void testNoFlights() throws FlightsException {

		List<FlightsMatched> response = flightsService.getFlights(config.getDeparture1(), config.getDatetime5(),
				config.getArrival1(), config.getDatetime6());

		Assert.assertTrue(response.size() == 0);
	}
	
	/** Test for getting overlapped flights
	 * 
	 * @throws FlightsException
	 */
	@Test
	public void testOverlappedFlights() throws FlightsException {

		List<FlightsMatched> response = flightsService.getFlights(config.getDeparture1(), config.getDatetime9(),
				config.getArrival1(), config.getDatetime10());

		Assert.assertTrue(response.size() == 1);
		Assert.assertTrue(response.get(0).getStops() == 1);
		Assert.assertTrue(response.get(0).getLegs().size() == 3);
	}

	/**
	 * Mocks GET requests
	 */
	@Before
	public void setup() {

		mockServer = MockRestServiceServer.createServer(restTemplate);

		// all the mock requests must be done at next order,
		// otherwise, the test will fail
		readJsonFile(config.getRoutesFilePath(), config.getRoutesEndpoint());
		readJsonFile(config.getScheduledFlights_10_MAD_IBZ(), config.getScheduledFlightsEndpoint_10_MAD_IBZ());
		readJsonFile(config.getScheduledFlights_10_MAD_MAN(), config.getScheduledFlightsEndpoint_10_MAD_MAN());
		readJsonFile(config.getScheduledFlights_10_MAN_IBZ(), config.getScheduledFlightsEndpoint_10_MAN_IBZ());
		readJsonFile(config.getScheduledFlights_10_MAD_MRS(), config.getScheduledFlightsEndpoint_10_MAD_MRS());
		readJsonFile(config.getScheduledFlights_10_MRS_IBZ(), config.getScheduledFlightsEndpoint_10_MRS_IBZ());
	}

	private void readJsonFile(String filePath, String endpoint) {

		try (BufferedReader br = new BufferedReader(new FileReader(getFileFromResources(filePath)))) {
			String jsonRoutes = readAllLinesWithStream(br);

			mockServer.expect(ExpectedCount.once(), requestTo(new URI(endpoint))).andExpect(method(HttpMethod.GET))
					.andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jsonRoutes));

		} catch (FileNotFoundException e) {
			Assert.fail(String.format("File %s not found", filePath));
		} catch (IOException e) {
			Assert.fail(String.format("File %s not correctly formatted", filePath));
		} catch (URISyntaxException e) {
			Assert.fail(String.format("Url %s not correctly formatted", endpoint));
		}
	}

	private String readAllLinesWithStream(BufferedReader reader) {
		return reader.lines().collect(Collectors.joining(System.lineSeparator()));
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
