package fr.getlinks.repository.impl;

import fr.getlinks.domain.cassandra.User;
import fr.getlinks.repository.UserRepository;

public class UserRepositoryImpl extends CassandraRepository implements UserRepository
{

	@Override
	public void saveUser(User user)
	{
		this.em.persist(user);

	}

	@Override
	public User findUserByLogin(String login)
	{
		return this.em.find(User.class, login);
	}

}
