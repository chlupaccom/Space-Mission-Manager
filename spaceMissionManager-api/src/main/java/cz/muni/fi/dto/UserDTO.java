package cz.muni.fi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.config.LocalDateDeserializer;
import cz.muni.fi.config.LocalDateSerializer;

import java.time.LocalDate;

public class UserDTO {

	private Long id;

	private String name;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthDate;

	private String email;

	private String password;

	private boolean isManager;

	private int experienceLevel;


	private boolean acceptedMission;

	private String explanation;

	private MissionDTO mission;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean manager) {
		isManager = manager;
	}

	public int getExperienceLevel() {
		return experienceLevel;
	}

	public void setExperienceLevel(int experienceLevel) {
		this.experienceLevel = experienceLevel;
	}

	public boolean getAcceptedMission() {
		return acceptedMission;
	}

	public void setAcceptedMission(boolean acceptedMission) {
		this.acceptedMission = acceptedMission;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public MissionDTO getMission() {
		return mission;
	}

	public void setMission(MissionDTO mission) {
		this.mission = mission;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserDTO)) return false;

		UserDTO user = (UserDTO) o;

		return getEmail() != null ? getEmail().equals(user.getEmail()) : user.getEmail() == null;
	}

	@Override
	public int hashCode() {
		return getEmail() != null ? getEmail().hashCode() : 0;
	}
}
