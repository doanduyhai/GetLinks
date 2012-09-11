package fr.getlinks.service.mail.registration.impl;

import javax.mail.MessagingException;

import org.testng.annotations.Test;

import fr.getlinks.AbstractGetlinksTest;

public class RegistrationMailSenderImplTest extends AbstractGetlinksTest
{

	@Test
	public void testSendRegistrationEmail() throws MessagingException
	{
		String message = "Welcome to Getlinks";
		this.registrationMailSender.sendRegistrationEmail("doanduyhai@gmail.com", "Duy Hai", message);
	}

	@Test
	public void testSendUnblockEmail() throws MessagingException
	{
		String message = "Click this link to reactivate your account";
		this.registrationMailSender.sendReactivationEmail("doanduyhai@gmail.com", "Duy Hai", message);
	}
}
