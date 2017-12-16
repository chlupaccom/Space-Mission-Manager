package cz.muni.fi;


import cz.muni.fi.entity.CraftComponent;
import cz.muni.fi.entity.Spacecraft;
import cz.muni.fi.entity.User;
import cz.muni.fi.services.CraftComponentService;
import cz.muni.fi.services.MissionService;
import cz.muni.fi.services.SpacecraftService;
import cz.muni.fi.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

	final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

	@Autowired
	private UserService userService;
	@Autowired
	private CraftComponentService craftComponentService;
	@Autowired
	private MissionService missionService;
	@Autowired
	private SpacecraftService spacecraftService;


	@Override
	public void loadData() throws IOException {
		loadCC();
		loadUsers();
	}


	private void loadUsers() {

		User user = new User();
		user.setName("ADMIN");
		user.setBirthDate(LocalDate.now().minusYears(20));
		user.setEmail("admin@admin.com");
		user.setPassword(createHash("password"));
		user.setManager(true);
		userService.addUser(user);

		User gagarin = new User();
		gagarin.setName("Gagarin");
		gagarin.setBirthDate(LocalDate.of(1934, Month.MARCH, 9));
		gagarin.setEmail("gagarin@gmail.com");
		gagarin.setPassword(createHash("gagarin"));
		gagarin.setManager(false);
		gagarin.setExperienceLevel(10);
		userService.addUser(gagarin);

	}


	private void loadCC() {
		CraftComponent craftComponent = new CraftComponent();
		craftComponent.setName("Wing");
		craftComponent.setReadyToUse(false);
		craftComponent.setReadyDate(ZonedDateTime.now().plusDays(5));
		craftComponentService.addComponent(craftComponent);

		craftComponent = new CraftComponent();
		craftComponent.setName("Fuel tank");
		craftComponent.setReadyToUse(true);
		//craftComponent.setReadyDate(ZonedDateTime.now().plusDays(5));
		craftComponentService.addComponent(craftComponent);

		craftComponent = new CraftComponent();
		craftComponent.setName("Engine");
		craftComponent.setReadyToUse(false);
		craftComponent.setReadyDate(ZonedDateTime.now().plusDays(40));
		craftComponentService.addComponent(craftComponent);

		craftComponent = new CraftComponent();
		craftComponent.setName("Cockpit");
		craftComponent.setReadyToUse(true);
//		craftComponent.setReadyDate(ZonedDateTime.now().plusDays(5));
		craftComponentService.addComponent(craftComponent);

	}

	private void loadSpacecrafts() {
		Spacecraft spacecraft = new Spacecraft();
		spacecraft.setName("Apollo 11");
		spacecraft.setType("Manned spacecraft");
	}



	//see  https://crackstation.net/hashing-security.htm#javasourcecode
	private static String createHash(String password) {
		final int SALT_BYTE_SIZE = 24;
		final int HASH_BYTE_SIZE = 24;
		final int PBKDF2_ITERATIONS = 1000;
		// Generate a random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		// Hash the password
		byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		// format iterations:salt:hash
		return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
		try {
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
			return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean validatePassword(String password, String correctHash) {
		if (password == null) return false;
		if (correctHash == null) throw new IllegalArgumentException("password hash is null");
		String[] params = correctHash.split(":");
		int iterations = Integer.parseInt(params[0]);
		byte[] salt = fromHex(params[1]);
		byte[] hash = fromHex(params[2]);
		byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
		return slowEquals(hash, testHash);
	}

	/**
	 * Compares two byte arrays in length-constant time. This comparison method
	 * is used so that password hashes cannot be extracted from an on-line
	 * system using a timing attack and then attacked off-line.
	 *
	 * @param a the first byte array
	 * @param b the second byte array
	 * @return true if both byte arrays are the same, false if not
	 */
	private static boolean slowEquals(byte[] a, byte[] b) {
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
	}

	private static byte[] fromHex(String hex) {
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
	}



}
