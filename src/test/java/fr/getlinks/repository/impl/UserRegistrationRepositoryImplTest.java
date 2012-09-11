package fr.getlinks.repository.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import fr.getlinks.AbstractGetlinksTest;
import fr.getlinks.domain.cassandra.User;
import fr.getlinks.domain.cassandra.UserRegistration;
import fr.getlinks.service.registration.impl.RandomStringGeneratorImpl;

public class UserRegistrationRepositoryImplTest extends AbstractGetlinksTest
{

	private User testUser;
	private String randomToken;

	@BeforeSuite
	public void init()
	{
		testUser = new User("doanduyhai", "abcdef", "DuyHai", "DOAN", "doanduyhai@gmail.com", false);
	}

	@Test
	public void testSaveUser()
	{
		this.userRegistrationRepository.saveUser(testUser);

		User savedUser = this.entityManager.find(User.class, "doanduyhai");
		assertNotNull(savedUser, "User should be persisted and searchable by login");
		assertFalse(savedUser.isActive(), "Saved user should be still inactive");
	}

	@Test(dependsOnMethods = "testSaveUser")
	public void testBindUser()
	{
		RandomStringGeneratorImpl randomGenerator = new RandomStringGeneratorImpl();
		randomToken = randomGenerator.generateRandomTimestampString(16);

		this.userRegistrationRepository.bindUser(testUser, randomToken);

		UserRegistration userRegistration = this.entityManager.find(UserRegistration.class, randomToken);

		assertNotNull(userRegistration, "UserRegistration should not be null");
		assertEquals(userRegistration.getUserId(), testUser.getLogin(), "UserRegistration userId should be 'doanduyhai'");
	}

	@Test(dependsOnMethods = "testBindUser")
	public void testActivateUserFromRegistration()
	{
		User activatedUser = this.userRegistrationRepository.activateUserFromRegistration(randomToken);

		assertNotNull(activatedUser, "activatedUser should not be null");
		assertTrue(activatedUser.isActive(), "activatedUser is now active");

		UserRegistration userRegistration = this.entityManager.find(UserRegistration.class, randomToken);

		assertNull(userRegistration, "UserRegistration should be removed after activation");
	}

	@Test(dependsOnMethods = "testActivateUserFromRegistration")
	public void testDeactivateUser()
	{
		this.userRegistrationRepository.deactivateUser(testUser.getLogin());

		User deactivatedUser = this.entityManager.find(User.class, testUser.getLogin());

		assertFalse(deactivatedUser.isActive(), "deactivatedUser should be inactive");
	}
}
