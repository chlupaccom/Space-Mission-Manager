package cz.muni.fi.facade;

import cz.muni.fi.dto.UserCreateDTO;
import cz.muni.fi.dto.UserDTO;

import java.util.List;

public interface UserFacade {
	/**
	 * Confirm mission for given user, leaving explanation empty.
	 * Set acceptedMission to true.
	 * @param user given user to change
	 */
	void acceptAssignedMission(UserDTO user);

	/**
	 * Reject mission for given user and add explanation.
	 * Leave acceptedMission false and add astronauts explanation.
	 * Also removes the astronaut from the mission's list of astronauts.
	 * @param user given user to change
	 * @param explanation nonempty explanation why user did not accepted given mission
	 * @throws IllegalArgumentException when the explanation is empty
	 */
	void rejectAssignedMission(UserDTO user, String explanation);
	/**
	 * Persist user into database
	 *
	 * @param user instance of user
	 * @return id of created user
	 */
	Long addUser(UserCreateDTO user);

	/**
	 * Update user in database
	 *
	 * @param user Instance of user
	 */
	void updateUser(UserDTO user);

	/**
	 * Delete user from database
	 *
	 * @param user instance of user
	 */
	void deleteUser(UserDTO user);

	/**
	 * Find all users
	 *
	 * @return List of all users, null if none
	 */
	List<UserDTO> findAllUsers();

	/**
	 * Find all astronauts
	 *
	 * @return list of
	 */

	List<UserDTO> findAllAstronauts();

	/**
	 * Find user by id
	 *
	 * @param id User id
	 * @return User instance of user
	 */
	UserDTO findUserById(Long id);

	/**
	 * Find all available astronauts
	 *
	 * @return List of available astronauts
	 */
	List<UserDTO> findAllAvailableAstronauts();

}
