package fr.getlinks.service.mail.registration.impl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.PropertySource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import fr.getlinks.AbstractGetlinksTest;
import fr.getlinks.domain.cassandra.User;
import fr.getlinks.repository.UserRegistrationRepository;

@PropertySource(
{
	"classpath:/messages/messages.properties"
})
public class UserRegistrationModuleImplTest extends AbstractGetlinksTest
{

	private User user;
	private UserRegistrationRepository mockedUserRegistrationRepository;
	private String randomString;

	@BeforeClass
	public void init()
	{
		user = new User("doanduyhai", "test", "DuyHai", "DOAN", "doanduyhai@gmail.com", true);
		mockedUserRegistrationRepository = mock(UserRegistrationRepository.class);
		this.userRegistrationModule.setUserRegistrationRepository(mockedUserRegistrationRepository);
	}

	@Test
	public void testRegisterUser() throws MessagingException
	{
		randomString = this.userRegistrationModule.registerUser(user);
		assertFalse(user.isActive(), "user should be inactive");
	}

	@Test(dependsOnMethods = "testRegisterUser")
	public void testActivateUser()
	{
		when(mockedUserRegistrationRepository.activateUserFromRegistration(eq(randomString))).thenAnswer(new Answer<User>()
		{

			@Override
			public User answer(InvocationOnMock invocation) throws Throwable
			{
				user.setActive(true);
				return user;
			}

		});

		User user = this.userRegistrationModule.activateUser(randomString);
		assertEquals(user.getLogin(), "doanduyhai", "Activated user should be 'doanduyhai'");
		assertTrue(user.isActive(), "User 'doanduyhai' should be now active");
	}

	@Test(dependsOnMethods = "testActivateUser")
	public void generateReactivationEmail() throws MessagingException
	{
		when(mockedUserRegistrationRepository.deactivateUser(anyString())).thenReturn(user);
		user.setActive(false);
		randomString = this.userRegistrationModule.generateReactivationEmail(user);
		assertTrue(StringUtils.isNotBlank(randomString), "Generated randomString for reactivation is not blank");
	}

}
