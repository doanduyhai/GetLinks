package fr.getlinks.domain.cassandra;

import static fr.getlinks.repository.configuration.ColumnFamilyKeys.USER_CF;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;
import me.prettyprint.hom.converters.JodaTimeHectorConverter;

import org.hibernate.validator.constraints.Email;
import org.joda.time.DateTime;

@Entity
@Table(name = USER_CF)
@Data
public class User
{
	@Id
	private String login;

	@Column(name = "passwordHash")
	private String passwordHash;

	@Column(name = "salt")
	private String salt;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "birthdate", converter = JodaTimeHectorConverter.class)
	private DateTime birthdate;

	@Email
	@Column(name = "contactEmail")
	private String contactEmail;

	@Column(name = "corporate")
	private boolean corporate;

	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Column(name = "website")
	private String website;

	@Column(name = "blog")
	private String blog;

	@Column(name = "active")
	private boolean active = false;

	@Column(name = "shouldChangePassword")
	private boolean shouldChangePassword = false;

	public User() {}

	public User(String login, String passwordHash, String salt) {
		super();
		this.login = login;
		this.passwordHash = passwordHash;
		this.salt = salt;
	}

	public User(String login, String passwordHash, String firstname, String lastname, String contactEmail, boolean active) {
		super();
		this.login = login;
		this.passwordHash = passwordHash;
		this.firstname = firstname;
		this.lastname = lastname;
		this.contactEmail = contactEmail;
		this.active = active;
	}

}
