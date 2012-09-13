package fr.getlinks.service.registration;

import javax.mail.MessagingException;

public interface RegistrationMailSender
{
	void sendRegistrationEmail(String email, String username, String message) throws MessagingException;

	void sendReactivationEmail(String email, String username, String message) throws MessagingException;

	void sendResetPasswordEmail(String email, String username, String message) throws MessagingException;
}
