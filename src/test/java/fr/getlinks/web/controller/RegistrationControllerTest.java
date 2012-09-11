package fr.getlinks.web.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.view;

import javax.inject.Inject;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.server.MockMvc;
import org.springframework.test.web.server.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.service.registration.UserRegistrationModule;
import fr.getlinks.web.controller.testLoader.TestGenericWebXmlContextLoader;
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
	private UserRegistrationModule mockedRegistrationModule;

	private User user;

	@BeforeSuite
	public void init() throws Exception
	{
		super.springTestContextPrepareTestInstance();

		user = new User("doanduyhai", "test", "DuyHai", "DOAN", "doanduyhai@gmail.com", true);
		RegistrationController registrationController = new RegistrationController();
		registrationController.setUserRegistrationModule(mockedRegistrationModule);
		mockedRegistrationController = MockMvcBuilders.webApplicationContextSetup(this.wac).build();
	}

	@Test
	public void testActivateOK_HTTPAction() throws Exception
	{
		when(mockedRegistrationModule.activateUser(anyString())).thenReturn(user);
		mockedRegistrationController.perform(get("/activate/azsdfdf")).andExpect(view().name(ViewConstants.VIEW_USER_ACTIVATION));
	}

	@Test
	public void testActivateKO_HTTPAction() throws Exception
	{
		when(mockedRegistrationModule.activateUser(anyString())).thenReturn(null);
		mockedRegistrationController.perform(get("/activate/azsdfdf")).andExpect(view().name(ViewConstants.VIEW_USER_ACTIVATION_INVALID));
	}
}
