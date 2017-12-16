package cz.muni.fi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.config.LocalDateDeserializer;
import cz.muni.fi.config.LocalDateSerializer;
import cz.muni.fi.config.ZonedDateTimeDeserializer;
import cz.muni.fi.config.ZonedDateTimeSerializer;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MissionDTO {

	private Long id;

	private Set<UserDTO> astronauts = new HashSet<>();

	private Set<SpacecraftDTO> spacecrafts = new HashSet<>();

	private String name;

	private String destination;
	@JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private ZonedDateTime eta;
	private String missionDescription;


	private boolean active;
	private String result;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate endDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<UserDTO> getAstronauts() {
		return Collections.unmodifiableSet(astronauts);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SpacecraftDTO> getSpacecrafts() {
		return Collections.unmodifiableSet(spacecrafts);
	}

	public void addAstronaut(UserDTO user){
		astronauts.add(user);
	}

	public void removeAstronaut(UserDTO user){
		astronauts.remove(user);
	}

	public void addSpacecraft(SpacecraftDTO spacecraft){
		spacecrafts.add(spacecraft);
	}

	public void removeSpacecraft(SpacecraftDTO spacecraft){
		spacecrafts.remove(spacecraft);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MissionDTO)) return false;

		MissionDTO mission = (MissionDTO) o;

		return getName() != null ? getName().equals(mission.getName()) : mission.getName() == null;
	}

	@Override
	public int hashCode() {
		return getName() != null ? getName().hashCode() : 0;
	}
}
