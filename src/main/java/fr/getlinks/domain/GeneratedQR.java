package fr.getlinks.domain;

import java.util.List;

public class GeneratedQR
{
	private String uniqueTimeUUID;

	private List<String> networks;

	private String url;

	public GeneratedQR(String uniqueTimeUUID, List<String> networks, String url) {
		super();
		this.uniqueTimeUUID = uniqueTimeUUID;
		this.networks = networks;
		this.url = url;
	}

	public String getUniqueTimeUUID()
	{
		return uniqueTimeUUID;
	}

	public void setUniqueTimeUUID(String uniqueTimeUUID)
	{
		this.uniqueTimeUUID = uniqueTimeUUID;
	}

	public List<String> getNetworks()
	{
		return networks;
	}

	public void setNetworks(List<String> networks)
	{
		this.networks = networks;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

}
