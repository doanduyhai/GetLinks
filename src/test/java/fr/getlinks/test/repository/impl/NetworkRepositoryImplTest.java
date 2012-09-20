package fr.getlinks.test.repository.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import fr.getlinks.domain.cassandra.Network;
import fr.getlinks.test.AbstractGetlinksTest;

@Test(groups = "networkRepositoryTestGroup")
public class NetworkRepositoryImplTest extends AbstractGetlinksTest
{
	private Network network;
	private String networkName = "TEST_NETWORK_REPOSITORY";

	@Test
	public void testSaveNetwork_REPOSITORY()
	{
		network = new Network(networkName, "Test network", "http://fr.linkedin.com/pub/{0}/{1}/{2}/{3}");
		this.networkRepository.saveNetwork(network);

		Network savedNetwork = this.entityManager.find(Network.class, networkName);

		assertNotNull(savedNetwork, "savedNetwork should not be null");
		assertEquals(savedNetwork, this.network, "savedNetwork should be equals to network");
	}

	@Test(dependsOnMethods = "testSaveNetwork_REPOSITORY")
	public void testFindNetworkByName_REPOSITORY()
	{
		Network foundNetwork = this.networkRepository.findNetworkByName(networkName);
		assertNotNull(foundNetwork, "foundNetwork should not be null");
		assertEquals(foundNetwork, this.network, "foundNetwork should be equals to network");
	}
}
