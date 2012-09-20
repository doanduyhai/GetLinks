package fr.getlinks.repository;

import fr.getlinks.domain.cassandra.User;

public interface UserRepository
{
	void saveUser(User user);

	User findUserByLogin(String login);
}
