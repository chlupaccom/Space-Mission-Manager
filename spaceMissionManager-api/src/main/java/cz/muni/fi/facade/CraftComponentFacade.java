package cz.muni.fi.facade;

import cz.muni.fi.dto.CraftComponentCreateDTO;
import cz.muni.fi.dto.CraftComponentDTO;

import java.util.List;

public interface CraftComponentFacade {
	/**
	 *
	 * @param craftComponent component
	 * @return Id of created component
	 */
	Long addComponent(CraftComponentCreateDTO craftComponent);

	List<CraftComponentDTO> findAllComponents();

	/**
	 *
	 * @param id id off component to find
	 */
	CraftComponentDTO findComponentById(Long id);

	/**
	 *
	 * @param craftComponent component to update
	 */
	void updateComponent(CraftComponentDTO craftComponent);

	/**
	 *
	 * @param craftComponent component to remove
	 */
	void removeComponent(CraftComponentDTO craftComponent);
}
