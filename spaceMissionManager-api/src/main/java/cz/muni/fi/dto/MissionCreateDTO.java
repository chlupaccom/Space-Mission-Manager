package cz.muni.fi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.config.LocalDateDeserializer;
import cz.muni.fi.config.LocalDateSerializer;
import cz.muni.fi.config.ZonedDateTimeDeserializer;
import cz.muni.fi.config.ZonedDateTimeSerializer;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class MissionCreateDTO {

	@NotNull
	private Set<UserDTO> astronauts = new HashSet<>();

	@Size(min = 1)
	@NotNull
	private Set<SpacecraftDTO> spacecrafts = new HashSet<>();

	@NotNull
	@Size(min = 3, max = 50)
	private String name;

	@NotNull
	@Size(min = 3, max = 255)
	private String destination;

	@NotNull
	@Future
	@JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private ZonedDateTime eta;

	@NotNull
	@Size(min = 3, max = 255)
	private String missionDescription;


	private boolean active;
	private String result;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate endDate;


	public Set<UserDTO> getAstronauts() {
		return astronauts;
	}


	public Set<SpacecraftDTO> getSpacecrafts() {
		return spacecrafts;
	}

	public void setAstronauts(Set<UserDTO> astronauts) {
		this.astronauts = astronauts;
	}

	public void setSpacecrafts(Set<SpacecraftDTO> spacecrafts) {
		this.spacecrafts = spacecrafts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public ZonedDateTime getEta() {
		return eta;
	}

	public void setEta(ZonedDateTime eta) {
		this.eta = eta;
	}

	public String getMissionDescription() {
		return missionDescription;
	}

	public void setMissionDescription(String missionDescription) {
		this.missionDescription = missionDescription;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
}
