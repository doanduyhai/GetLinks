package fr.getlinks.domain;

public class NetworkLink
{
	private String label;
	private String image;
	private String url;

	public NetworkLink(String label, String image, String url) {
		super();
		this.label = label;
		this.image = image;
		this.url = url;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
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
