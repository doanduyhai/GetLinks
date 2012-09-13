package fr.getlinks.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.service.registration.UserRegistrationService;
import fr.getlinks.web.view.ViewConstants;

@Controller
public class RegistrationController
{

	private UserRegistrationService userRegistrationService;

	@RequestMapping(value = ViewConstants.URL_ACTIVATE, method = RequestMethod.GET)
	public String activateUser(@PathVariable("randomToken") String randomToken)
	{

		User activatedUser = userRegistrationService.activateUser(randomToken);
		if (activatedUser != null)
		{
			return ViewConstants.VIEW_USER_ACTIVATION;
		}
		else
		{
			return ViewConstants.VIEW_USER_ACTIVATION_INVALID;
		}
	}

	public void setUserRegistrationService(UserRegistrationService userRegistrationService)
	{
		this.userRegistrationService = userRegistrationService;
	}

}
