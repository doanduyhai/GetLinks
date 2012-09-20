package fr.getlinks.test.web.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.service.registration.UserRegistrationService;
import fr.getlinks.test.web.controller.testLoader.TestGenericWebXmlContextLoader;
import fr.getlinks.web.controller.RegistrationController;
import fr.getlinks.web.view.ViewConstants;

@ContextConfiguration(loader = TestGenericWebXmlContextLoader.class, locations =
{
		"classpath:getlinks-user-registration-test.xml",
		"classpath:getlinks-mvc.xml"
})
public class RegistrationControllerTest extends AbstractTestNGSpringContextTests
{
	@Inject
	private WebApplicationContext wac;

	private MockMvc mockedRegistrationController;

	@Inject
	private UserRegistrationService mockedRegistrationModule;

	private User user;

	@BeforeSuite
	public void init() throws Exception
	{
		super.springTestContextPrepareTestInstance();

		user = new User("doanduyhai", "test", "DuyHai", "DOAN", "doanduyhai@gmail.com", true);
		RegistrationController registrationController = new RegistrationController();
		registrationController.setUserRegistrationService(mockedRegistrationModule);
		mockedRegistrationController = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
	}

	@Test
	public void testActivateOK_CONTROLLER() throws Exception
	{
		when(mockedRegistrationModule.activateUser(anyString())).thenReturn(user);
		mockedRegistrationController.perform(get("/activate/azsdfdf")).andExpect(view().name(ViewConstants.VIEW_USER_ACTIVATION));
	}

	@Test
	public void testActivateKO_CONTROLLER() throws Exception
	{
		when(mockedRegistrationModule.activateUser(anyString())).thenReturn(null);
		mockedRegistrationController.perform(get("/activate/azsdfdf")).andExpect(view().name(ViewConstants.VIEW_USER_ACTIVATION_INVALID));
	}

	@Test
	public void testChangePasswordOK_CONTROLLER() throws Exception
	{
		Authentication mockedAuthentication = mock(Authentication.class);
		user.setShouldChangePassword(true);
		when(mockedAuthentication.getPrincipal()).thenReturn(user);
		SecurityContextHolder.getContext().setAuthentication(mockedAuthentication);
		mockedRegistrationController.perform(get(ViewConstants.URL_CHANGE_PASSWORD)).andExpect(view().name(ViewConstants.VIEW_CHANGE_PASSWORD));
	}

	@Test
	public void testChangePasswordRedirectToHome_CONTROLLER() throws Exception
	{
		Authentication mockedAuthentication = mock(Authentication.class);
		user.setShouldChangePassword(false);
		when(mockedAuthentication.getPrincipal()).thenReturn(user);
		SecurityContextHolder.getContext().setAuthentication(mockedAuthentication);
		mockedRegistrationController.perform(get(ViewConstants.URL_CHANGE_PASSWORD)).andExpect(view().name(ViewConstants.VIEW_HOME));
	}
}
