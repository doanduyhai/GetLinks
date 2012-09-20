package fr.getlinks.repository.impl;

import fr.getlinks.domain.cassandra.Network;
import fr.getlinks.repository.NetworkRepository;

public class NetworkRepositoryImpl extends CassandraRepository implements NetworkRepository
{

	@Override
	public void saveNetwork(Network network)
	{
		this.em.persist(network);
	}

	@Override
	public Network findNetworkByName(String name)
	{
		return this.em.find(Network.class, name);
	}

}
