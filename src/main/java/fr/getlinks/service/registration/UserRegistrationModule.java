package fr.getlinks.service.registration;

import javax.mail.MessagingException;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.repository.UserRegistrationRepository;

public interface UserRegistrationModule
{
	String registerUser(User user) throws MessagingException;

	User activateUser(String randomToken);

	String generateReactivationEmail(User user) throws MessagingException;

	void setRegistrationMailSender(RegistrationMailSender registrationMailSender);

	void setUserRegistrationRepository(UserRegistrationRepository userRegistrationRepository);

	void setRandomStringGenerator(RandomStringGenerator randomStringGenerator);
}
