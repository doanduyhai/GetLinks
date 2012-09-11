package fr.getlinks.service.registration.impl;

import java.text.MessageFormat;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Value;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.repository.UserRegistrationRepository;
import fr.getlinks.service.registration.RandomStringGenerator;
import fr.getlinks.service.registration.RegistrationMailSender;
import fr.getlinks.service.registration.UserRegistrationModule;

public class UserRegistrationModuleImpl implements UserRegistrationModule
{
	@Value("${registration.welcome.message}")
	String welcomeMessage;

	@Value("${registration.reactivate.message}")
	String reactivationMessage;

	private RandomStringGenerator randomStringGenerator;
	private RegistrationMailSender registrationMailSender;
	private UserRegistrationRepository userRegistrationRepository;
	private static final String ACTIVATION_URL_PREFIX = "http://localhost:8080/getlinks/activate/";

	@Override
	public String registerUser(User user) throws MessagingException
	{

		String randomString = this.randomStringGenerator.generateRandomTimestampString(16);
		user.setActive(false);
		this.userRegistrationRepository.saveUser(user);
		this.userRegistrationRepository.bindUser(user, randomString);

		String message = MessageFormat.format(welcomeMessage, ACTIVATION_URL_PREFIX + randomString);
		this.registrationMailSender.sendRegistrationEmail(user.getContactEmail(), user.getFirstname(), message);

		return randomString;
	}

	@Override
	public User activateUser(String randomToken)
	{
		return this.userRegistrationRepository.activateUserFromRegistration(randomToken);
	}

	@Override
	public String generateReactivationEmail(User user) throws MessagingException
	{
		String randomString = "";
		user.setActive(false);
		User deactivatedUser = this.userRegistrationRepository.deactivateUser(user.getLogin());

		if (deactivatedUser != null && !deactivatedUser.isActive())
		{
			randomString = this.randomStringGenerator.generateRandomTimestampString(16);
			this.userRegistrationRepository.bindUser(user, randomString);
			String message = MessageFormat.format(reactivationMessage, ACTIVATION_URL_PREFIX + randomString);
			this.registrationMailSender.sendReactivationEmail(user.getContactEmail(), user.getFirstname(), message);
		}

		return randomString;
	}

	@Override
	public void setUserRegistrationRepository(UserRegistrationRepository userRegistrationRepository)
	{
		this.userRegistrationRepository = userRegistrationRepository;
	}

	@Override
	public void setRandomStringGenerator(RandomStringGenerator randomStringGenerator)
	{
		this.randomStringGenerator = randomStringGenerator;
	}

	@Override
	public void setRegistrationMailSender(RegistrationMailSender registrationMailSender)
	{
		this.registrationMailSender = registrationMailSender;
	}

}
