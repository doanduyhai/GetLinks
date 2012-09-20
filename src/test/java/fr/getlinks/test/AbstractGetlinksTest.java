package fr.getlinks.test;

import java.lang.reflect.Field;

import javassist.Modifier;

import javax.inject.Inject;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.hom.EntityManagerImpl;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

import fr.getlinks.repository.NetworkRepository;
import fr.getlinks.repository.UserNetworkRepository;
import fr.getlinks.repository.UserRegistrationRepository;
import fr.getlinks.repository.UserRepository;
import fr.getlinks.repository.configuration.CassandraConfiguration;
import fr.getlinks.repository.configuration.ColumnFamilyKeys;
import fr.getlinks.repository.impl.CassandraRepository;
import fr.getlinks.service.mail.MailService;
import fr.getlinks.service.registration.RegistrationMailSender;
import fr.getlinks.service.registration.UserRegistrationService;
import fr.getlinks.service.registration.impl.RandomStringGeneratorImpl;

@ContextConfiguration(locations =
{
		"classpath:getlinks-properties.xml",
		"classpath:getlinks-mail.xml",
		"classpath:getlinks-repository.xml",
		"classpath:getlinks-user-registration.xml"
})
public abstract class AbstractGetlinksTest extends AbstractTestNGSpringContextTests
{
	private static boolean isInitialized = false;

	@Inject
	protected CassandraConfiguration cassandraConfiguration;

	@Inject
	protected EntityManagerImpl entityManager;

	@Inject
	protected MailService mailService;

	@Inject
	protected RandomStringGeneratorImpl randomStringGenerator;

	@Inject
	protected RegistrationMailSender registrationMailSender;

	@Inject
	protected UserRegistrationService userRegistrationService;

	@Inject
	protected UserRegistrationRepository userRegistrationRepository;

	@Inject
	protected NetworkRepository networkRepository;

	@Inject
	protected UserRepository userRepository;

	@Inject
	protected UserNetworkRepository userNetworkRepository;

	protected CassandraRepository cassandraRepository;

	@BeforeSuite
	public void prepareCassandraCluster() throws Exception
	{
		if (!isInitialized)
		{
			EmbeddedCassandraServerHelper.startEmbeddedCassandra("cassandraUnitConfig.yaml");
			isInitialized = true;
			super.springTestContextPrepareTestInstance();
		}

		this.cassandraRepository = new CassandraRepository();
		this.cassandraRepository.setKeyspace(this.cassandraConfiguration.getKeyspace());
		this.cassandraRepository.setEm(this.entityManager);
		this.cleanCF();
	}

	// @BeforeClass
	public void cleanCF() throws IllegalArgumentException, IllegalAccessException
	{
		CqlQuery<String, String, String> cqlQuery = new CqlQuery<String, String, String>(this.cassandraConfiguration.getKeyspace(),
				CassandraConfiguration.se, CassandraConfiguration.se, CassandraConfiguration.se);

		for (Field field : ColumnFamilyKeys.class.getDeclaredFields())
		{
			if (Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))
			{
				cqlQuery.setQuery(" truncate " + field.get(null));
				cqlQuery.execute();
			}
		}

	}
}
