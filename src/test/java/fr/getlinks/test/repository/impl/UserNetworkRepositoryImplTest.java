package fr.getlinks.test.repository.impl;

import static fr.getlinks.repository.configuration.ColumnFamilyKeys.USER_NETWORK_USAGE_CF;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import fr.getlinks.domain.NetworkUsage;
import fr.getlinks.domain.cassandra.Network;
import fr.getlinks.test.AbstractGetlinksTest;

@Test(groups = "userNetworkRepositoryTestGroup", dependsOnGroups =
{
		"networkRepositoryTestGroup",
		"userRepositoryTestGroup"
})
public class UserNetworkRepositoryImplTest extends AbstractGetlinksTest
{

	private NetworkUsage networkUsage;
	private Network network;
	private String login = "testUserNetwork";
	private String networkName = "testUserNetworkName";

	@BeforeSuite
	public void init()
	{
		this.network = new Network(this.networkName, "Test user network", "http://fr.linkedin.com/pub/{0}/{1}/{2}/{3}");
		this.networkRepository.saveNetwork(this.network);
	}

	@Test
	public void testSaveNetworkUsage_REPOSITORY()
	{
		this.networkUsage = new NetworkUsage(this.network, 1);
		this.userNetworkRepository.saveNetworkUsage(this.login, this.networkUsage);

		Integer shareCounter = (Integer) this.cassandraRepository.getValueFromCF(USER_NETWORK_USAGE_CF, this.login, this.networkName);

		assertNotNull(shareCounter, "shareCounter is not null");
		assertEquals(shareCounter.intValue(), this.networkUsage.getShareCounter(), "sharedCounter should be equals to 1");
	}

	@Test(dependsOnMethods = "testSaveNetworkUsage_REPOSITORY")
	public void testFindNetworkUsageByLogin_REPOSITORY()
	{
		List<NetworkUsage> networkUsages = this.userNetworkRepository.findNetworkUsagesByLogin(this.login);

		assertNotNull(networkUsages, "networkUsages should not be null");
		assertEquals(networkUsages.size(), 1, "networkUsages should has only 1 item");

		NetworkUsage savedNetworkUsage = networkUsages.get(0);

		assertTrue(StringUtils.equals(savedNetworkUsage.getNetworkName(), this.networkUsage.getNetworkName()), "savedNetworkusage name should be '"
				+ this.networkName + "'");
		assertNotNull(savedNetworkUsage.getNetwork(), "savedNetworkusage.getNetwork() should not be null");
		assertEquals(savedNetworkUsage.getNetwork(), this.network, "savedNetworkusage.getNetwork() should be equal to 'network'");
	}

	@Test(dependsOnMethods = "testFindNetworkUsageByLogin_REPOSITORY")
	public void testFindNetworkUsageByLoginAndNetwork_REPOSITORY()
	{
		NetworkUsage savedNetworkUsage = this.userNetworkRepository.findNetworkUsageByLoginAndNetwork(this.login, this.networkName);

		assertTrue(StringUtils.equals(savedNetworkUsage.getNetworkName(), this.networkUsage.getNetworkName()), "savedNetworkusage name should be '"
				+ this.networkName + "'");
		assertNotNull(savedNetworkUsage.getNetwork(), "savedNetworkusage.getNetwork() should not be null");
		assertEquals(savedNetworkUsage.getNetwork(), this.network, "savedNetworkusage.getNetwork() should be equal to 'network'");

	}
}
