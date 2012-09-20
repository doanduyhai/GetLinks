package fr.getlinks.domain;

import java.io.Serializable;

import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import fr.getlinks.domain.cassandra.Network;

@ToString(of =
{
		"networkName",
		"shareCounter"
})
@EqualsAndHashCode(of =
{
		"networkName",
		"shareCounter"
})
@Getter
@Setter
public class NetworkUsage implements Comparable<NetworkUsage>, Serializable
{
	private static final long serialVersionUID = 1L;

	@Transient
	Network network;

	private String networkName;
	private int shareCounter = 0;

	public NetworkUsage(Network network, int shareCounter) {
		super();
		this.network = network;
		this.networkName = network.getName();
		this.shareCounter = shareCounter;
	}

	@Override
	public int compareTo(NetworkUsage o)
	{
		if (o == null)
		{
			return 1;
		}
		else
		{
			if (this.shareCounter > o.getShareCounter())
			{
				return 1;
			}
			else if (this.shareCounter == o.getShareCounter())
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
	}

}
