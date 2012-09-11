package fr.getlinks.domain.cassandra;

import static fr.getlinks.repository.configuration.ColumnFamilyKeys.USER_REGISTRATION_CF;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;

@Data
@Entity
@Table(name = USER_REGISTRATION_CF)
public class UserRegistration
{

	@Id
	private String registrationKey;

	@Column(name = "userId")
	private String userId;

	public UserRegistration() {}

	public UserRegistration(String registrationKey, String userId) {
		super();
		this.registrationKey = registrationKey;
		this.userId = userId;
	}
}
