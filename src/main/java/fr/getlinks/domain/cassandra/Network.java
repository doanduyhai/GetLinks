package fr.getlinks.domain.cassandra;

import static fr.getlinks.repository.configuration.ColumnFamilyKeys.NETWORK_CF;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;

@Data
@Entity
@Table(name = NETWORK_CF)
public class Network
{
	@Id
	String name;

	@Column(name = "description")
	String description;

	@Column(name = "url")
	String url;

	public Network() {}

	public Network(String name, String description, String url) {
		super();
		this.name = name;
		this.description = description;
		this.url = url;
	}

}
