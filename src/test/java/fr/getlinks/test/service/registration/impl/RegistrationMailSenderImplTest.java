package fr.getlinks.test.service.registration.impl;

import javax.mail.MessagingException;

import org.testng.annotations.Test;

import fr.getlinks.test.AbstractGetlinksTest;

@Test(groups = "registrationMailSenderTestGroup", dependsOnGroups = "mailServiceTestGroup")
public class RegistrationMailSenderImplTest extends AbstractGetlinksTest
{

	@Test
	public void testSendRegistrationEmail_SERVICE() throws MessagingException
	{
		String message = "Welcome to Getlinks";
		this.registrationMailSender.sendRegistrationEmail("doanduyhai@gmail.com", "Duy Hai", message);
	}

	@Test
	public void testSendUnblockEmail_SERVICE() throws MessagingException
	{
		String message = "Click this link to reactivate your account";
		this.registrationMailSender.sendReactivationEmail("doanduyhai@gmail.com", "Duy Hai", message);
	}
}
