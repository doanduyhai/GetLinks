package fr.getlinks.repository.configuration;

import static fr.getlinks.repository.configuration.ColumnFamilyKeys.USER_CF;
import static fr.getlinks.repository.configuration.ColumnFamilyKeys.USER_REGISTRATION_CF;
import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TimeUUIDSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftCluster;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.eaio.uuid.UUID;

public class CassandraConfiguration implements InitializingBean
{

	private final Log log = LogFactory.getLog(CassandraConfiguration.class);
	private static final Serializer<String> se = StringSerializer.get();
	private static final Serializer<Long> le = LongSerializer.get();
	private static final Serializer<Object> oe = ObjectSerializer.get();
	private static final Serializer<UUID> te = TimeUUIDSerializer.get();

	private String cassandraHost = "localhost:9171";
	private String cassandraClusterName = "Tatami test cluster";
	private String cassandraKeyspace = "tatami-test";
	private Keyspace keyspace;
	private HConsistencyLevel consistencyLevel;

	public void setCassandraHost(String cassandraHost)
	{
		this.cassandraHost = cassandraHost;
	}

	public void setCassandraClusterName(String cassandraClusterName)
	{
		this.cassandraClusterName = cassandraClusterName;
	}

	public void setCassandraKeyspace(String cassandraKeyspace)
	{
		this.cassandraKeyspace = cassandraKeyspace;
	}

	public void setConsistencyLevel(HConsistencyLevel consistencyLevel)
	{
		this.consistencyLevel = consistencyLevel;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{

		CassandraHostConfigurator cassandraHostConfigurator = new CassandraHostConfigurator(cassandraHost);
		ThriftCluster cluster = new ThriftCluster(cassandraClusterName, cassandraHostConfigurator);
		ConfigurableConsistencyLevel consistencyLevelPolicy = new ConfigurableConsistencyLevel();
		consistencyLevelPolicy.setDefaultReadConsistencyLevel(this.consistencyLevel);

		KeyspaceDefinition keyspaceDef = cluster.describeKeyspace(cassandraKeyspace);

		// Create keyspace if it does not exist yet
		if (keyspaceDef == null)
		{
			keyspaceDef = new ThriftKsDef(cassandraKeyspace);
			cluster.addKeyspace(keyspaceDef, true);

			// User column family
			ColumnFamilyDefinition userCfd = HFactory.createColumnFamilyDefinition(this.cassandraKeyspace, USER_CF, ComparatorType.UTF8TYPE);
			userCfd.setKeyValidationClass("org.apache.cassandra.db.marshal.UTF8Type");
			cluster.addColumnFamily(userCfd);

			ColumnFamilyDefinition userRegistrationCfd = HFactory.createColumnFamilyDefinition(this.cassandraKeyspace, USER_REGISTRATION_CF,
					ComparatorType.UTF8TYPE);
			userRegistrationCfd.setKeyValidationClass("org.apache.cassandra.db.marshal.UTF8Type");
			cluster.addColumnFamily(userRegistrationCfd);

		}

		this.keyspace = HFactory.createKeyspace(cassandraKeyspace, cluster, consistencyLevelPolicy);
	}

	public Keyspace getKeyspace()
	{
		return keyspace;
	}

}
