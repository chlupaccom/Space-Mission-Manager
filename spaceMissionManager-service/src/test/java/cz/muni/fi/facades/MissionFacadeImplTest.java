package cz.muni.fi.facades;

import cz.muni.fi.config.ServiceConfiguration;
import cz.muni.fi.dto.*;
import cz.muni.fi.facade.CraftComponentFacade;
import cz.muni.fi.facade.MissionFacade;
import cz.muni.fi.facade.SpacecraftFacade;
import cz.muni.fi.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class MissionFacadeImplTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private MissionFacade missionFacade;

	@Autowired
	private SpacecraftFacade spacecraftFacade;

	@Autowired
	private CraftComponentFacade craftComponentFacade;

	@Autowired
	private UserFacade userFacade;

	private MissionCreateDTO missionCreateDTO;
	private MissionDTO mission1;
	private MissionDTO mission2;

	@BeforeMethod
	public void setUp() throws Exception {
		UserCreateDTO userCreateDTO = new UserCreateDTO();
		userCreateDTO.setName("name");
		userCreateDTO.setBirthDate(LocalDate.of(1990, Month.FEBRUARY, 13));
		userCreateDTO.setEmail("name@mail.com");
		userCreateDTO.setPassword("pass");
		userCreateDTO.setExperienceLevel(3);
		Long userId = userFacade.addUser(userCreateDTO);
		missionCreateDTO = getMissionCreateDTO("Enterprise");
		missionCreateDTO.setAstronauts(Collections.singleton(userFacade.findUserById(userId)));
		MissionCreateDTO missionCreateDTO1 = getMissionCreateDTO("Discovery");
		missionCreateDTO1.setActive(true);
		mission1 = missionFacade.findMissionById(missionFacade.createMission(missionCreateDTO1));
		missionCreateDTO1 = getMissionCreateDTO("Glenn");
		missionCreateDTO1.setActive(false);
		mission2 = missionFacade.findMissionById(missionFacade.createMission(missionCreateDTO1));
	}

	private SpacecraftDTO getSpacecraft(String name){
		CraftComponentCreateDTO craftComponentCreateDTO = TestUtils.getCraftComponentCreateDTO(name);
		Long componentId = craftComponentFacade.addComponent(craftComponentCreateDTO);
		SpacecraftCreateDTO spacecraftCreateDTO = TestUtils.getSpacecraftCreateDTO(name);
		spacecraftCreateDTO.setComponents(Collections.singleton(craftComponentFacade.findComponentById(componentId)));
		return spacecraftFacade.findSpacecraftById(spacecraftFacade.addSpacecraft(spacecraftCreateDTO));
	}

	private MissionCreateDTO getMissionCreateDTO(String name){
		MissionCreateDTO missionCreateDTO = TestUtils.getMissionCreateDTO(name);
		missionCreateDTO.setSpacecrafts(Collections.singleton(getSpacecraft(name)));
		return missionCreateDTO;
	}

	@AfterMethod
	public void tearDown() throws Exception {
		for (MissionDTO mission :
				missionFacade.findAllMissions()) {
			missionFacade.cancelMission(mission);
		}
		for (UserDTO user :
				userFacade.findAllAstronauts()) {
			userFacade.deleteUser(user);
		}
		for (SpacecraftDTO spacecraft :
				spacecraftFacade.findAllSpacecrafts()) {
			spacecraftFacade.removeSpacecraft(spacecraft);
		}
		for (CraftComponentDTO craftComponent :
				craftComponentFacade.findAllComponents()) {
			craftComponentFacade.removeComponent(craftComponent);
		}
		assertThat(missionFacade.findAllMissions()).isEmpty();
		assertThat(userFacade.findAllUsers().isEmpty());
		assertThat(spacecraftFacade.findAllSpacecrafts()).isEmpty();
		assertThat(craftComponentFacade.findAllComponents()).isEmpty();
		missionCreateDTO = null;
		mission1 = null;
		mission2 = null;
	}



	@Test
	public void testCreateMission() throws Exception {
		assertThat(missionFacade.findAllMissions()).hasSize(2);
		MissionDTO missionDTO = missionFacade.findMissionById(missionFacade.createMission(missionCreateDTO));
		assertThat(missionDTO).isEqualToIgnoringGivenFields(missionCreateDTO, "id");
		assertThat(missionFacade.findAllMissions()).contains(missionDTO);
	}

	@Test
	public void testCancelMission() throws Exception {

		assertThat(missionFacade.findAllMissions()).hasSize(2).contains(mission1, mission2);
		missionFacade.cancelMission(mission1);
		assertThat(missionFacade.findAllMissions()).hasSize(1).contains(mission2);

	}

	@Test
	public void testFindAllMissions() throws Exception {
		assertThat(missionFacade.findAllMissions()).hasSize(2).contains(mission1, mission2);

	}

	@Test
	public void testFindAllMissionsActive() throws Exception {
		assertThat(missionFacade.findAllMissions(true)).hasSize(1).contains(mission1);
		assertThat(missionFacade.findAllMissions(false)).hasSize(1).contains(mission2);
		missionCreateDTO.setActive(true);
		Long id = missionFacade.createMission(missionCreateDTO);
		assertThat(missionFacade.findAllMissions(true)).hasSize(2).contains(mission1, missionFacade.findMissionById(id));
	}


	@Test
	public void testFindMissionById() throws Exception {
		assertThat(missionFacade.findMissionById(mission1.getId())).isEqualTo(mission1);
		assertThat(missionFacade.findMissionById(mission2.getId())).isEqualTo(mission2);
	}

	@Test
	public void testUpdateMission() throws Exception {
		mission1.setDestination("UPDATED");
		missionFacade.updateMission(mission1);
		assertThat(missionFacade.findMissionById(mission1.getId())).isEqualToComparingFieldByField(mission1);
	}

	@Test
	public void testArchive() throws Exception {
		missionFacade.archive(mission1, LocalDate.now().minusDays(6));
		assertThat(missionFacade.findMissionById(mission1.getId())).hasFieldOrPropertyWithValue("active", false);
		assertThat(missionFacade.findMissionById(mission1.getId()).getResult()).isNotEmpty();
	}
}