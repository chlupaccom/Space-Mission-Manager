package cz.muni.fi.facade;

import cz.muni.fi.dto.MissionCreateDTO;
import cz.muni.fi.dto.MissionDTO;

import java.time.LocalDate;
import java.util.List;

public interface MissionFacade {
	/**
	 * Archives given mission.
	 * Sets the mission's end date and result - saving attributes of the mission object.
	 * Result string will contain all details about the completed mission.
	 * @param mission mission to archive
	 * @param endDate end date of the mission. Must be in the past, not in the future
	 */
	void archive(MissionDTO mission, LocalDate endDate);

	/**
	 * Save given mission
	 * @param mission mission to be saved in the database
	 * @return id of created mission
	 */
	Long createMission(MissionCreateDTO mission);

	/**
	 * Cancel given mission
	 * @param mission mission to be deleted from the database
	 */
	void cancelMission(MissionDTO mission);

	/**
	 * List all missions
	 * @return list of all missions in the database
	 */
	List<MissionDTO> findAllMissions();

	/**
	 * List all missions with given state
	 * @param active state of the mission
	 * @return list of the missions
	 */
	List<MissionDTO> findAllMissions(boolean active);

	/**
	 * Find mission with given id
	 * @param id id of desired mission
	 * @return mission with given id or null
	 */
	MissionDTO findMissionById(Long id);

	/**
	 * Update given mission
	 * @param mission mission to be updated
	 */
	void updateMission(MissionDTO mission);

}
