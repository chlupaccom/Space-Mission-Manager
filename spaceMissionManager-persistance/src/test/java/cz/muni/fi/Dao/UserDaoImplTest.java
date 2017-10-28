package cz.muni.fi.Dao;

import cz.muni.fi.ApplicationContext;
import cz.muni.fi.Entity.Mission;
import cz.muni.fi.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static org.testng.Assert.*;
import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(classes = ApplicationContext.class)
public class UserDaoImplTest extends AbstractTestNGSpringContextTests {

	@Autowired
	UserDao userDao;

	@Autowired
	MissionDao missionDao;

	@PersistenceUnit
	EntityManagerFactory entityManagerFactory;

	private User gagarin;
	private User armstrong;
	private User vonBraun;

	@BeforeMethod
	public void setUp() throws Exception {
		gagarin = new User();
		gagarin.setName("Yuri Gagarin");
		gagarin.setBirthDate(LocalDate.of(1934, Month.MARCH, 9));
		gagarin.setEmail("yuri420@azet.sk");
		gagarin.setPassword("topAstronaut");
		gagarin.setManager(false);

		armstrong = new User();
		armstrong.setName("Neil Armstrong");
		armstrong.setBirthDate(LocalDate.of(1930, Month.AUGUST, 5));
		armstrong.setEmail("notDruggedArmstrong@zoznam.cz");
		armstrong.setPassword("america#1");
		armstrong.setManager(false);

		vonBraun = new User();
		vonBraun.setName("Wernher von Braun");
		vonBraun.setBirthDate(LocalDate.of(1912, Month.MARCH, 23));
		vonBraun.setEmail("secretNaziBraun@azet.sk");
		vonBraun.setPassword("topManager");
		vonBraun.setManager(true);

	}

	@AfterMethod
	public void tearDown() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.createQuery("delete from User ").executeUpdate();
		entityManager.createQuery("delete from Mission ").executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Test
	public void testAddUser() throws Exception {
		userDao.addUser(gagarin);
		assertThat(userDao.findAllAstronauts()).hasSize(1);
		userDao.addUser(armstrong);
		assertThat(userDao.findAllAstronauts()).hasSize(2);
		assertThat(userDao.findUserById(gagarin.getId())).isEqualTo(gagarin);
	}


	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAddUserWithId() throws Exception {
		//TODO gagarin.setId(1L);
		userDao.addUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAddUserBirthdayInFuture() throws Exception {
		gagarin.setBirthDate(LocalDate.now().plusMonths(3));
		userDao.addUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAddUserWithNullName() throws Exception {
		gagarin.setName(null);
		userDao.addUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAddUserWithNullBirthDate() throws Exception {
		gagarin.setBirthDate(null);
		userDao.addUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAddUserWithNullMail() throws Exception {
		gagarin.setEmail(null);
		userDao.addUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAddUserWithWrongMail() throws Exception {
		gagarin.setEmail("wrongMail");
		userDao.addUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testAddUserWithNullPassword() throws Exception {
		gagarin.setPassword(null);
		userDao.addUser(gagarin);
	}

	@Test
	public void testUpdateUser() throws Exception {
		userDao.addUser(gagarin);
		gagarin.setName("Yuri #1 Gagarin");
		gagarin.setBirthDate(LocalDate.of(1935, Month.MARCH, 9));
		gagarin.setEmail("yuri421@azet.sk");
		gagarin.setPassword("topAstronaut1");
		userDao.updateUser(gagarin);
		assertThat(userDao.findUserById(gagarin.getId())).isEqualToComparingFieldByField(gagarin);
	}


	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateUserWithId() throws Exception {
		userDao.addUser(gagarin);
		//TODO gagarin.setId(null);
		userDao.updateUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateUserBirthdayInFuture() throws Exception {
		userDao.addUser(gagarin);
		gagarin.setBirthDate(LocalDate.now().plusMonths(3));
		userDao.updateUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateUserWithNullName() throws Exception {
		userDao.addUser(gagarin);
		gagarin.setName(null);
		userDao.updateUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateUserWithNull() throws Exception {
		userDao.addUser(gagarin);
		gagarin.setBirthDate(null);
		userDao.updateUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateUserWithNullMail() throws Exception {
		userDao.addUser(gagarin);
		gagarin.setEmail(null);
		userDao.updateUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateUserWithWrongMail() throws Exception {
		userDao.addUser(gagarin);
		gagarin.setEmail("wrongMail");
		userDao.updateUser(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testUpdateUserWithNullPassword() throws Exception {
		userDao.addUser(gagarin);
		gagarin.setPassword(null);
		userDao.updateUser(gagarin);
	}

	@Test
	public void testDeleteUser() throws Exception {
		userDao.addUser(gagarin);
		assertThat(userDao.findAllAstronauts()).isEqualTo(Collections.singletonList(gagarin));
		userDao.deleteUser(gagarin);
		assertThat(userDao.findAllAstronauts()).isEmpty();
	}

	@Test
	public void testFindAllUsers() throws Exception {
		userDao.addUser(gagarin);
		userDao.addUser(armstrong);
		userDao.addUser(vonBraun);
		assertThat(userDao.findAllUsers()).hasSize(3);
	}

	@Test
	public void testFindAllAstronauts() throws Exception {
		userDao.addUser(gagarin);
		userDao.addUser(armstrong);
		userDao.addUser(vonBraun);
		assertThat(userDao.findAllAstronauts()).hasSize(2); //TODO doesnt work

	}

	@Test
	public void testFindUserById() throws Exception {
		userDao.addUser(gagarin);
		assertThat(userDao.findUserById(gagarin.getId())).isEqualToComparingFieldByField(gagarin);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testFindUserByIdWithNullId() throws Exception {
		userDao.addUser(gagarin);
		userDao.findUserById(null);
	}


	@Test
	public void testFindAllAvailableAstronauts() throws Exception {
		userDao.addUser(gagarin);
		Mission mission = new Mission();
		mission.setName("Apollo");
		missionDao.createMission(mission);
		armstrong.setMission(mission);
		userDao.addUser(armstrong);
		userDao.addUser(vonBraun);
		assertThat(userDao.findAllAvailableAstronauts()).hasSize(1);
	}

}