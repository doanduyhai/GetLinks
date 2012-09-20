package fr.getlinks.test.repository.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.test.AbstractGetlinksTest;

@Test(groups = "userRepositoryTestGroup")
public class UserRepositoryImplTest extends AbstractGetlinksTest
{

	private User user;
	private String login = "testUserRepository";

	@Test(groups = "userRepositoryTestGroup")
	public void testSaveUser_REPOSITORY()
	{
		this.user = new User(login, "123456", "abcdef");
		this.userRepository.saveUser(user);

		User savedUser = this.entityManager.find(User.class, login);

		assertNotNull(savedUser, "savedUser should not be null");
		assertEquals(savedUser, this.user, "savedUser should be equals to user");
	}

	@Test(groups = "userRepositoryTestGroup", dependsOnMethods = "testSaveUser_REPOSITORY")
	public void testFindUserByLogin_REPOSITORY()
	{
		User foundUser = this.userRepository.findUserByLogin(login);

		assertNotNull(foundUser, "foundUser should not be null");
		assertEquals(foundUser, this.user, "foundUser should be equals to user");
	}
}
