package fr.getlinks.service.registration;

import javax.mail.MessagingException;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.repository.UserRegistrationRepository;

public interface UserRegistrationService
{
	String registerUser(User user, String clearPassword) throws MessagingException;

	User activateUser(String randomToken);

	String generateReactivationEmail(User user) throws MessagingException;

	void generateResetPasswordEmail(User user) throws MessagingException;

	void setRegistrationMailSender(RegistrationMailSender registrationMailSender);

	void setUserRegistrationRepository(UserRegistrationRepository userRegistrationRepository);

	void setRandomStringGenerator(RandomStringGenerator randomStringGenerator);

	void setPasswordEncoder(PasswordEncoder passwordEncoder);
}
