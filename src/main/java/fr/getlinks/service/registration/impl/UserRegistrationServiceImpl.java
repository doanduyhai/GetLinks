package fr.getlinks.service.registration.impl;

import java.text.MessageFormat;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.repository.UserRegistrationRepository;
import fr.getlinks.service.registration.RandomStringGenerator;
import fr.getlinks.service.registration.RegistrationMailSender;
import fr.getlinks.service.registration.UserRegistrationService;

public class UserRegistrationServiceImpl implements UserRegistrationService
{
	@Value("${registration.welcome.message}")
	String welcomeMessage;

	@Value("${registration.reactivate.message}")
	String reactivationMessage;

	@Value("${registration.reset.password.message}")
	String resetPasswordMessage;

	private PasswordEncoder passwordEncoder;

	private RandomStringGenerator randomStringGenerator;
	private RegistrationMailSender registrationMailSender;
	private UserRegistrationRepository userRegistrationRepository;
	private static final String ACTIVATION_URL_PREFIX = "http://localhost:8080/getlinks/activate/";

	@Override
	public String registerUser(User user, String clearPassword) throws MessagingException
	{
		String salt = this.randomStringGenerator.generateRandomString(8);
		String hashedPassword = this.passwordEncoder.encodePassword(clearPassword, salt);
		user.setSalt(salt);
		user.setPasswordHash(hashedPassword);

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
	public void generateResetPasswordEmail(User user) throws MessagingException
	{
		String salt = this.randomStringGenerator.generateRandomString(8);
		String newPassword = this.randomStringGenerator.generateRandomString(8);
		String hashedPassword = this.passwordEncoder.encodePassword(newPassword, salt);
		user.setSalt(salt);
		user.setPasswordHash(hashedPassword);
		user.setShouldChangePassword(true);
		this.userRegistrationRepository.saveUser(user);
		String message = MessageFormat.format(resetPasswordMessage, newPassword);
		this.registrationMailSender.sendResetPasswordEmail(user.getContactEmail(), user.getFirstname(), message);
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

	@Override
	public void setPasswordEncoder(PasswordEncoder passwordEncoder)
	{
		this.passwordEncoder = passwordEncoder;
	}
}
