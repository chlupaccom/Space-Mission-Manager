package cz.muni.fi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import cz.muni.fi.config.ZonedDateTimeDeserializer;
import cz.muni.fi.config.ZonedDateTimeSerializer;

import java.time.ZonedDateTime;

public class CraftComponentDTO {
	private Long id;

	private boolean readyToUse;

	private String name;

	private SpacecraftDTO spacecraft;

	@JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private ZonedDateTime readyDate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isReadyToUse() {
		return readyToUse;
	}

	public void setReadyToUse(boolean readyToUse) {
		this.readyToUse = readyToUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpacecraftDTO getSpacecraft() {
		return spacecraft;
	}

	public void setSpacecraft(SpacecraftDTO spacecraft) {
		this.spacecraft = spacecraft;
	}

	public ZonedDateTime getReadyDate() {
		return readyDate;
	}

	public void setReadyDate(ZonedDateTime readyDate) {
		this.readyDate = readyDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof CraftComponentDTO)) return false;

		CraftComponentDTO that = (CraftComponentDTO) o;

		return getName().equals(that.getName());
	}

	@Override
	public int hashCode() {
		return getName() != null ? getName().hashCode() : 0;
	}
}
