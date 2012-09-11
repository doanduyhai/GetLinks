package fr.getlinks.repository.impl;

import static fr.getlinks.repository.configuration.ColumnFamilyKeys.USER_REGISTRATION_CF;
import fr.getlinks.domain.cassandra.User;
import fr.getlinks.domain.cassandra.UserRegistration;
import fr.getlinks.repository.UserRegistrationRepository;

public class UserRegistrationRepositoryImpl extends CassandraAbstractRepository implements UserRegistrationRepository
{

	@Override
	public void saveUser(User user)
	{
		this.em.persist(user);
	}

	@Override
	public boolean bindUser(User user, String randomToken)
	{
		UserRegistration userRegistration = new UserRegistration(randomToken, user.getLogin());
		this.em.persist(userRegistration);
		return true;
	}

	@Override
	public User deactivateUser(String login)
	{
		User user = this.em.find(User.class, login);
		if (user != null)
		{
			user.setActive(false);
		}
		return user;
	}

	@Override
	public User activateUserFromRegistration(String randomToken)
	{
		UserRegistration userRegistration = this.em.find(UserRegistration.class, randomToken);
		User activatedUser = null;
		if (userRegistration != null)
		{
			this.removeRowFromCF(USER_REGISTRATION_CF, randomToken);
			activatedUser = this.em.find(User.class, userRegistration.getUserId());
			if (activatedUser != null)
			{
				activatedUser.setActive(true);
			}
		}
		return activatedUser;
	}
}
