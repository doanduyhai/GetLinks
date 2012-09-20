package fr.getlinks.domain.cassandra;

import java.util.List;

import lombok.Data;
import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;
import fr.getlinks.domain.NetworkUsage;

@Data
public class UserNetworkRanking
{
	@Id
	String login;

	@Column(name = "networkUsage")
	List<NetworkUsage> networkUsages;

	public UserNetworkRanking() {}

	public UserNetworkRanking(String login, List<NetworkUsage> networkUsages) {
		super();
		this.login = login;
		this.networkUsages = networkUsages;
	}
}
