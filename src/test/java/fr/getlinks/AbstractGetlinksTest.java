package fr.getlinks;

import javax.inject.Inject;

import me.prettyprint.hom.EntityManagerImpl;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeSuite;

import fr.getlinks.repository.UserRegistrationRepository;
import fr.getlinks.service.mail.MailService;
import fr.getlinks.service.registration.RegistrationMailSender;
import fr.getlinks.service.registration.UserRegistrationModule;
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
	protected MailService mailService;

	@Inject
	protected RandomStringGeneratorImpl randomStringGenerator;

	@Inject
	protected RegistrationMailSender registrationMailSender;

	@Inject
	protected UserRegistrationModule userRegistrationModule;

	@Inject
	protected EntityManagerImpl entityManager;

	@Inject
	protected UserRegistrationRepository userRegistrationRepository;

	@BeforeSuite
	public void prepareCassandraCluster() throws Exception
	{
		if (!isInitialized)
		{
			EmbeddedCassandraServerHelper.startEmbeddedCassandra("cassandraUnitConfig.yaml");
			isInitialized = true;
			super.springTestContextPrepareTestInstance();
		}

	}
}
