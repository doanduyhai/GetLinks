package fr.getlinks.repository;

import fr.getlinks.domain.cassandra.User;

public interface UserRegistrationRepository
{
	void saveUser(User user);

	boolean bindUser(User user, String randomToken);

	User activateUserFromRegistration(String randomToken);

	User deactivateUser(String login);

}
