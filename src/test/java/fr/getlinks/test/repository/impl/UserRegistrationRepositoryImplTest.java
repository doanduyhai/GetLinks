package fr.getlinks.test.repository.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.domain.cassandra.UserRegistration;
import fr.getlinks.service.registration.impl.RandomStringGeneratorImpl;
import fr.getlinks.test.AbstractGetlinksTest;

@Test(groups = "userRegistrationRepositoryTestGroup", dependsOnGroups = "userRepositoryTestGroup")
public class UserRegistrationRepositoryImplTest extends AbstractGetlinksTest
{

	private User testUser;
	private String login = "testUserRegistrationRepository";
	private String firstname = "firstname_testUserRegistrationRepository";
	private String lastname = "lastname_testUserRegistrationRepository";
	private String email = "email@testUserRegistrationRepository.com";
	private String randomToken;

	@BeforeClass
	public void init()
	{
		testUser = new User(login, "abcdef", firstname, lastname, email, false);
	}

	@Test
	public void testSaveUser_REPOSITORY()
	{
		this.userRegistrationRepository.saveUser(testUser);

		User savedUser = this.entityManager.find(User.class, login);
		assertNotNull(savedUser, "User should be persisted and searchable by login");
		assertFalse(savedUser.isActive(), "Saved user should be still inactive");
	}

	@Test(dependsOnMethods = "testSaveUser_REPOSITORY")
	public void testBindUser_REPOSITORY()
	{
		RandomStringGeneratorImpl randomGenerator = new RandomStringGeneratorImpl();
		randomToken = randomGenerator.generateRandomTimestampString(16);

		this.userRegistrationRepository.bindUser(testUser, randomToken);

		UserRegistration userRegistration = this.entityManager.find(UserRegistration.class, randomToken);

		assertNotNull(userRegistration, "UserRegistration should not be null");
		assertEquals(userRegistration.getUserId(), testUser.getLogin(), "UserRegistration userId should be '" + login + "'");
	}

	@Test(dependsOnMethods = "testBindUser_REPOSITORY")
	public void testActivateUserFromRegistration_REPOSITORY()
	{
		User activatedUser = this.userRegistrationRepository.activateUserFromRegistration(randomToken);

		assertNotNull(activatedUser, "activatedUser should not be null");
		assertTrue(activatedUser.isActive(), "activatedUser is now active");

		UserRegistration userRegistration = this.entityManager.find(UserRegistration.class, randomToken);

		assertNull(userRegistration, "UserRegistration should be removed after activation");
	}

	@Test(dependsOnMethods = "testActivateUserFromRegistration_REPOSITORY")
	public void testDeactivateUser_REPOSITORY()
	{
		this.userRegistrationRepository.deactivateUser(testUser.getLogin());

		User deactivatedUser = this.entityManager.find(User.class, testUser.getLogin());

		assertFalse(deactivatedUser.isActive(), "deactivatedUser should be inactive");
	}
}
