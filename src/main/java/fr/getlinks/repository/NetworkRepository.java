package fr.getlinks.repository;

import fr.getlinks.domain.cassandra.Network;

public interface NetworkRepository
{
	void saveNetwork(Network network);

	Network findNetworkByName(String name);
}
