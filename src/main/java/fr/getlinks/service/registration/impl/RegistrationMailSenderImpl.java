package fr.getlinks.service.registration.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;

import fr.getlinks.service.mail.MailService;
import fr.getlinks.service.registration.RegistrationMailSender;

public class RegistrationMailSenderImpl implements RegistrationMailSender
{

	private String registrationEmailTemplate;
	private String reactivationEmailTemplate;
	private String resetPasswordEmailTemplate;
	private MailService mailService;
	private static final String REGISTRATION_EMAIL_SUBJECT = "Getlinks Activation";
	private static final String REACTIVATION_EMAIL_SUBJECT = "Getlinks Account Reactivation";
	private static final String RESET_PASSWORD_EMAIL_SUBJECT = "Getlinks Password Reset";

	@Override
	public void sendRegistrationEmail(String email, String username, String message) throws MessagingException
	{
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("userName", username);
		variables.put("message", message);

		this.mailService.sendMail(REGISTRATION_EMAIL_SUBJECT, new String[]
		{
			email
		}, registrationEmailTemplate, variables, new Locale("en"));

	}

	@Override
	public void sendReactivationEmail(String email, String username, String message) throws MessagingException
	{
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("userName", username);
		variables.put("message", message);

		this.mailService.sendMail(REACTIVATION_EMAIL_SUBJECT, new String[]
		{
			email
		}, reactivationEmailTemplate, variables, new Locale("en"));

	}

	@Override
	public void sendResetPasswordEmail(String email, String username, String message) throws MessagingException
	{
		Map<String, String> variables = new HashMap<String, String>();
		variables.put("userName", username);
		variables.put("message", message);

		this.mailService.sendMail(RESET_PASSWORD_EMAIL_SUBJECT, new String[]
		{
			email
		}, resetPasswordEmailTemplate, variables, new Locale("en"));

	}

	public void setRegistrationEmailTemplate(String registrationEmailTemplate)
	{
		this.registrationEmailTemplate = registrationEmailTemplate;
	}

	public void setReactivationEmailTemplate(String reactivationEmailTemplate)
	{
		this.reactivationEmailTemplate = reactivationEmailTemplate;
	}

	public void setResetPasswordEmailTemplate(String resetPasswordEmailTemplate)
	{
		this.resetPasswordEmailTemplate = resetPasswordEmailTemplate;
	}

	public void setMailService(MailService mailService)
	{
		this.mailService = mailService;
	}

}
