package fr.getlinks.repository;

import java.util.List;

import fr.getlinks.domain.NetworkUsage;

public interface UserNetworkRepository
{

	void saveNetworkUsage(String login, NetworkUsage networkUsage);

	List<NetworkUsage> findNetworkUsagesByLogin(String login);

	NetworkUsage findNetworkUsageByLoginAndNetwork(String login, String networkName);

	void setNetworkRepository(NetworkRepository networkRepository);
}
