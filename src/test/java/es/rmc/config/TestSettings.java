package es.rmc.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "test")
public class TestSettings {
	
	@NotNull
	private String departure1;
	
	@NotNull
	private String arrival1;

	@NotNull
	private LocalDateTime datetime1;

	@NotNull
	private LocalDateTime datetime2;
	
	@NotNull
	private LocalDateTime datetime3;
	
	@NotNull
	private LocalDateTime datetime4;
	
	@NotNull
	private LocalDateTime datetime5;
	
	@NotNull
	private LocalDateTime datetime6;

	@NotNull
	private String routesEndpoint;
	
	@NotNull
	private String routesFilePath;
	
	@NotNull
	private String scheduledFlightsEndpoint_MAD_IBZ;

	@NotNull
	private String scheduledFlights_MAD_IBZ;

	@NotNull
	private String scheduledFlightsEndpoint_MAD_MAN;

	@NotNull
	private String scheduledFlights_MAD_MAN;

	@NotNull
	private String scheduledFlightsEndpoint_MAN_IBZ;

	@NotNull
	private String scheduledFlights_MAN_IBZ;

	@NotNull
	private String scheduledFlightsEndpoint_MAD_MRS;

	@NotNull
	private String scheduledFlights_MAD_MRS;

	@NotNull
	private String scheduledFlightsEndpoint_MRS_IBZ;

	@NotNull
	private String scheduledFlights_MRS_IBZ;

	// =========================================== Getters and setters
	// =========================================
	public String getDeparture1() {
		return departure1;
	}

	public void setDeparture1(String departure1) {
		this.departure1 = departure1;
	}

	public LocalDateTime getDatetime1() {
		return datetime1;
	}

	public void setDatetime1(String datetime) {
		this.datetime1 = parseToLocalDateTime(datetime);
	}

	public String getArrival1() {
		return arrival1;
	}

	public void setArrival1(String arrival1) {
		this.arrival1 = arrival1;
	}

	public LocalDateTime getDatetime2() {
		return datetime2;
	}

	public void setDatetime2(String datetime) {
		this.datetime2 = parseToLocalDateTime(datetime);
	}
	
	public LocalDateTime getDatetime3() {
		return datetime3;
	}
	
	public void setDatetime3(String datetime) {
		this.datetime3 = parseToLocalDateTime(datetime);
	}

	public LocalDateTime getDatetime4() {
		return datetime4;
	}
	
	public void setDatetime4(String datetime) {
		this.datetime4 = parseToLocalDateTime(datetime);
	}
	
	public LocalDateTime getDatetime5() {
		return datetime5;
	}
	
	public void setDatetime5(String datetime) {
		this.datetime5 = parseToLocalDateTime(datetime);
	}
	
	public LocalDateTime getDatetime6() {
		return datetime6;
	}
	
	public void setDatetime6(String datetime) {
		this.datetime6 = parseToLocalDateTime(datetime);
	}
	
	public String getRoutesEndpoint() {
		return routesEndpoint;
	}

	public void setRoutesEndpoint(String routesEndpoint) {
		this.routesEndpoint = routesEndpoint;
	}

	public String getRoutesFilePath() {
		return routesFilePath;
	}

	public void setRoutesFilePath(String routesFilePath) {
		this.routesFilePath = routesFilePath;
	}

	public String getScheduledFlightsEndpoint_MAD_IBZ() {
		return scheduledFlightsEndpoint_MAD_IBZ;
	}

	public void setScheduledFlightsEndpoint_MAD_IBZ(String scheduledFlightsEndpoint_MAD_IBZ) {
		this.scheduledFlightsEndpoint_MAD_IBZ = scheduledFlightsEndpoint_MAD_IBZ;
	}

	public String getScheduledFlights_MAD_IBZ() {
		return scheduledFlights_MAD_IBZ;
	}

	public void setScheduledFlights_MAD_IBZ(String scheduledFlights_MAD_IBZ) {
		this.scheduledFlights_MAD_IBZ = scheduledFlights_MAD_IBZ;
	}

	public String getScheduledFlightsEndpoint_MAD_MAN() {
		return scheduledFlightsEndpoint_MAD_MAN;
	}

	public void setScheduledFlightsEndpoint_MAD_MAN(String scheduledFlightsEndpoint_MAD_MAN) {
		this.scheduledFlightsEndpoint_MAD_MAN = scheduledFlightsEndpoint_MAD_MAN;
	}

	public String getScheduledFlights_MAD_MAN() {
		return scheduledFlights_MAD_MAN;
	}

	public void setScheduledFlights_MAD_MAN(String scheduledFlights_MAD_MAN) {
		this.scheduledFlights_MAD_MAN = scheduledFlights_MAD_MAN;
	}

	public String getScheduledFlightsEndpoint_MAN_IBZ() {
		return scheduledFlightsEndpoint_MAN_IBZ;
	}

	public void setScheduledFlightsEndpoint_MAN_IBZ(String scheduledFlightsEndpoint_MAN_IBZ) {
		this.scheduledFlightsEndpoint_MAN_IBZ = scheduledFlightsEndpoint_MAN_IBZ;
	}

	public String getScheduledFlights_MAN_IBZ() {
		return scheduledFlights_MAN_IBZ;
	}

	public void setScheduledFlights_MAN_IBZ(String scheduledFlights_MAN_IBZ) {
		this.scheduledFlights_MAN_IBZ = scheduledFlights_MAN_IBZ;
	}

	public String getScheduledFlightsEndpoint_MAD_MRS() {
		return scheduledFlightsEndpoint_MAD_MRS;
	}

	public void setScheduledFlightsEndpoint_MAD_MRS(String scheduledFlightsEndpoint_MAD_MRS) {
		this.scheduledFlightsEndpoint_MAD_MRS = scheduledFlightsEndpoint_MAD_MRS;
	}

	public String getScheduledFlights_MAD_MRS() {
		return scheduledFlights_MAD_MRS;
	}

	public void setScheduledFlights_MAD_MRS(String scheduledFlights_MAD_MRS) {
		this.scheduledFlights_MAD_MRS = scheduledFlights_MAD_MRS;
	}

	public String getScheduledFlightsEndpoint_MRS_IBZ() {
		return scheduledFlightsEndpoint_MRS_IBZ;
	}

	public void setScheduledFlightsEndpoint_MRS_IBZ(String scheduledFlightsEndpoint_MRS_IBZ) {
		this.scheduledFlightsEndpoint_MRS_IBZ = scheduledFlightsEndpoint_MRS_IBZ;
	}

	public String getScheduledFlights_MRS_IBZ() {
		return scheduledFlights_MRS_IBZ;
	}

	public void setScheduledFlights_MRS_IBZ(String scheduledFlights_MRS_IBZ) {
		this.scheduledFlights_MRS_IBZ = scheduledFlights_MRS_IBZ;
	}
	
	// =========================================== Parser methods
	// =========================================
	
	private LocalDateTime parseToLocalDateTime(String datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm").withZone(ZoneId.of("UTC"));
		return LocalDateTime.parse(datetime, formatter);
	}

}
