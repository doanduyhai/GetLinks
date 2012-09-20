package fr.getlinks.repository.impl;

import static fr.getlinks.repository.configuration.ColumnFamilyKeys.USER_NETWORK_USAGE_CF;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.QueryResult;
import fr.getlinks.domain.NetworkUsage;
import fr.getlinks.domain.cassandra.Network;
import fr.getlinks.repository.NetworkRepository;
import fr.getlinks.repository.UserNetworkRepository;

public class UserNetworkRepositoryImpl extends CassandraRepository implements UserNetworkRepository
{
	private NetworkRepository networkRepository;

	@Override
	public void saveNetworkUsage(String login, NetworkUsage networkUsage)
	{
		this.insertIntoCFWithValue(USER_NETWORK_USAGE_CF, login, networkUsage.getNetworkName(), networkUsage.getShareCounter());
	}

	@Override
	public List<NetworkUsage> findNetworkUsagesByLogin(String login)
	{
		List<NetworkUsage> networkUsages = new ArrayList<NetworkUsage>();

		CqlQuery<String, String, Integer> cqlQuery = new CqlQuery<String, String, Integer>(keyspace, se, se, ie);
		cqlQuery.setQuery("SELECT * FROM " + USER_NETWORK_USAGE_CF + " WHERE KEY = '" + login + "'");
		QueryResult<CqlRows<String, String, Integer>> results = cqlQuery.execute();

		Network network;
		for (HColumn<String, Integer> column : results.get().getByKey(login).getColumnSlice().getColumns())
		{
			network = this.networkRepository.findNetworkByName(column.getName());
			if (network != null)
			{
				networkUsages.add(new NetworkUsage(network, column.getValue()));
			}
		}
		return networkUsages;
	}

	@Override
	public NetworkUsage findNetworkUsageByLoginAndNetwork(String login, String networkName)
	{
		NetworkUsage networkUsage = null;
		Collection<HColumn<String, Object>> columns = this.findColumnsRangeFromCF(USER_NETWORK_USAGE_CF, login, networkName, false, 1);
		HColumn<String, Object> column = columns.iterator().next();

		if (column != null)
		{
			Network network = this.networkRepository.findNetworkByName(column.getName());
			if (network != null)
			{
				networkUsage = new NetworkUsage(network, (Integer) column.getValue());
			}
		}

		return networkUsage;
	}

	@Override
	public void setNetworkRepository(NetworkRepository networkRepository)
	{
		this.networkRepository = networkRepository;
	}

}
