package cz.muni.fi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.config.LocalDateDeserializer;
import cz.muni.fi.config.LocalDateSerializer;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UserCreateDTO {

	@NotNull
	private String name;

	@NotNull
	@Past
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthDate;

	@NotNull
	@Pattern(regexp = ".+@.+\\....?")
	@Size(min = 3, max = 150)
	private String email;

	@NotNull
	@Size(min = 3, max = 150)
	private String password;

	private boolean isManager;

	@Min(0)
	private int experienceLevel;


	private boolean rejectedMission;

	private String explanation;

	private MissionDTO mission;


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

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isRejectedMission() {
		return rejectedMission;
	}

	public void setRejectedMission(boolean rejectedMission) {
		this.rejectedMission = rejectedMission;
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
}
