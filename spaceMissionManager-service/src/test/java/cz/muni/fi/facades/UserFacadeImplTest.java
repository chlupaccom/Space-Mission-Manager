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
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)

public class UserFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private CraftComponentFacade craftComponentFacade;
    @Autowired
    MissionFacade missionFacade;
    @Autowired
    SpacecraftFacade spacecraftFacade;


    private UserDTO user;

    public UserCreateDTO createUser() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setName("name");
        userCreateDTO.setBirthDate(LocalDate.of(1990, Month.FEBRUARY, 13));
        userCreateDTO.setEmail("name@mail.com");
        userCreateDTO.setPassword("pass");
        userCreateDTO.setExperienceLevel(3);
        return userCreateDTO;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        UserCreateDTO user = createUser();
        Long id = userFacade.addUser(user);
        this.user = userFacade.findUserById(id);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        for (UserDTO user : userFacade.findAllUsers()) {
            userFacade.deleteUser(user);
        }
        for (MissionDTO mission : missionFacade.findAllMissions()) {
            missionFacade.cancelMission(mission);
        }
        for (SpacecraftDTO spacecraft : spacecraftFacade.findAllSpacecrafts()) {
            spacecraftFacade.removeSpacecraft(spacecraft);
        }
        for (CraftComponentDTO cc : craftComponentFacade.findAllComponents()) {
            craftComponentFacade.removeComponent(cc);
        }
    }

    @Test
    public void testAddUser() {
        int sizeBefore = userFacade.findAllUsers().size();
        UserCreateDTO user = createUser();
        user.setEmail("sfdljsdfl@mail.cz");
        Long id = userFacade.addUser(user);
        assertThat(userFacade.findAllUsers()).hasSize(sizeBefore + 1);
        assertThat(userFacade.findAllUsers()).contains(userFacade.findUserById(id));
    }

    @Test
    public void testUpdateUser() throws Exception {
        user.setEmail("new@super.com");
        userFacade.updateUser(user);
        UserDTO updatedUser = userFacade.findUserById(user.getId());
        assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void testDeleteUser() throws Exception {
        int sizeBefore = userFacade.findAllUsers().size();
        Long id = user.getId();
        userFacade.deleteUser(user);
        assertThat(userFacade.findAllUsers()).hasSize(sizeBefore - 1);
        assertThat(userFacade.findAllUsers()).doesNotContain(user);
    }

    @Test
    public void testFindAllUsers() throws Exception {
        int sizeBefore = userFacade.findAllUsers().size();
        UserCreateDTO user1 = createUser();
        user1.setEmail("1@users.com");
        Long id1 = userFacade.addUser(user1);
        UserCreateDTO user2 = createUser();
        user2.setEmail("2@users.com");
        Long id2 = userFacade.addUser(user2);
        assertThat(userFacade.findAllUsers()).hasSize(sizeBefore + 2);
        assertThat(userFacade.findAllUsers()).contains(userFacade.findUserById(id1));
        assertThat(userFacade.findAllUsers()).contains(userFacade.findUserById(id2));
    }

    @Test
    public void testFindAllAstronauts() throws Exception {
        int sizeBefore = userFacade.findAllAstronauts().size();
        UserCreateDTO user1 = createUser();
        user1.setEmail("1@users.com");
        user1.setManager(true);
        Long id1 = userFacade.addUser(user1);
        UserCreateDTO user2 = createUser();
        user2.setEmail("2@users.com");
        Long id2 = userFacade.addUser(user2);
        //One user is manager, so is not astronaut
        assertThat(userFacade.findAllAstronauts()).hasSize(sizeBefore + 1);
    }

    @Test
    public void testFindUserById() throws Exception {
        assertThat(userFacade.findUserById(user.getId())).isEqualTo(user);
    }

    @Test
    public void testFindAllAvailableAstronauts() throws Exception {
        CraftComponentCreateDTO craftComponentCreateDTO = new CraftComponentCreateDTO();
        craftComponentCreateDTO.setName("Cmp");
        craftComponentCreateDTO.setReadyDate(ZonedDateTime.now().plusDays(20));
        Long componentId = craftComponentFacade.addComponent(craftComponentCreateDTO);
        SpacecraftCreateDTO spacecraftCreateDTO = new SpacecraftCreateDTO();
        spacecraftCreateDTO.setName("Name");
        spacecraftCreateDTO.setComponents(Collections.singleton(craftComponentFacade.findComponentById(componentId)));
        SpacecraftDTO spacecraft = spacecraftFacade.findSpacecraftById(spacecraftFacade.addSpacecraft(spacecraftCreateDTO));

        MissionCreateDTO missionCreateDTO = new MissionCreateDTO();
        missionCreateDTO.setName("Name");
        missionCreateDTO.setDestination("Dest");
        missionCreateDTO.setEta(ZonedDateTime.now().plusDays(100));
        missionCreateDTO.setMissionDescription("Desc");
        missionCreateDTO.setSpacecrafts(Collections.singleton(spacecraft));
        Long missionId = missionFacade.createMission(missionCreateDTO);
        MissionDTO mission = missionFacade.findMissionById(missionId);

        int sizeBefore = userFacade.findAllAvailableAstronauts().size();
        UserCreateDTO user1 = createUser();
        user1.setEmail("1@users.com");
        user1.setMission(mission);
        Long id1 = userFacade.addUser(user1);

        assertThat(userFacade.findAllAvailableAstronauts()).hasSize(sizeBefore);

        UserDTO user2 = userFacade.findUserById(id1);
        user2.setMission(null);
        userFacade.updateUser(user2);

        assertThat(userFacade.findAllAvailableAstronauts()).hasSize(sizeBefore+1);
    }

	@Test
	public void testAcceptMission() throws Exception {
		MissionCreateDTO missionCreateDTO = TestUtils.getMissionCreateDTO("Enterprise");
		CraftComponentCreateDTO craftComponentCreateDTO = TestUtils.getCraftComponentCreateDTO("Enterprise");
		Long componentId = craftComponentFacade.addComponent(craftComponentCreateDTO);
		SpacecraftCreateDTO spacecraftCreateDTO = TestUtils.getSpacecraftCreateDTO("Enterprise");
		spacecraftCreateDTO.setComponents(Collections.singleton(craftComponentFacade.findComponentById(componentId)));
		missionCreateDTO.setSpacecrafts(Collections.singleton(spacecraftFacade.findSpacecraftById(spacecraftFacade.addSpacecraft(spacecraftCreateDTO))));
		Long id = missionFacade.createMission(missionCreateDTO);
		UserCreateDTO userCreateDTO = createUser();
		userCreateDTO.setEmail("ASH@mail.com");
		userCreateDTO.setMission(missionFacade.findMissionById(id));
		Long us = userFacade.addUser(userCreateDTO);
		userFacade.acceptAssignedMission(userFacade.findUserById(us));
		assertThat(userFacade.findUserById(us).getMission()).isNotNull();
		assertThat(userFacade.findUserById(us).getAcceptedMission()).isTrue();

	}

	@Test
	public void testRejectMission() throws Exception {
		MissionCreateDTO missionCreateDTO = TestUtils.getMissionCreateDTO("Enterprise");
		CraftComponentCreateDTO craftComponentCreateDTO = TestUtils.getCraftComponentCreateDTO("Enterprise");
		Long componentId = craftComponentFacade.addComponent(craftComponentCreateDTO);
		SpacecraftCreateDTO spacecraftCreateDTO = TestUtils.getSpacecraftCreateDTO("Enterprise");
		spacecraftCreateDTO.setComponents(Collections.singleton(craftComponentFacade.findComponentById(componentId)));
		missionCreateDTO.setSpacecrafts(Collections.singleton(spacecraftFacade.findSpacecraftById(spacecraftFacade.addSpacecraft(spacecraftCreateDTO))));
		Long id = missionFacade.createMission(missionCreateDTO);
		UserCreateDTO userCreateDTO = createUser();
		userCreateDTO.setEmail("ASH@mail.com");
		userCreateDTO.setMission(missionFacade.findMissionById(id));
		Long us = userFacade.addUser(userCreateDTO);
		userFacade.rejectAssignedMission(userFacade.findUserById(us), "My mom won't let me go.");
		assertThat(userFacade.findUserById(us).getMission()).isNull();
		assertThat(userFacade.findUserById(us).getAcceptedMission()).isFalse();

	}
}