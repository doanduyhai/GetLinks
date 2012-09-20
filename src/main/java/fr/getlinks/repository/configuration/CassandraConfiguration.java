package fr.getlinks.repository.configuration;

import java.lang.reflect.Field;

import javassist.Modifier;
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
	public static final Serializer<String> se = StringSerializer.get();
	public static final Serializer<Long> le = LongSerializer.get();
	public static final Serializer<Object> oe = ObjectSerializer.get();
	public static final Serializer<UUID> te = TimeUUIDSerializer.get();
	private static final String UTF8TYPE = "org.apache.cassandra.db.marshal.UTF8Type";

	private String cassandraHost = "localhost:9171";
	private String cassandraClusterName = "Tatami test cluster";
	private String cassandraKeyspace = "tatami-test";
	private Keyspace keyspace;
	private HConsistencyLevel consistencyLevel;

	private void addColumnFamilyWithStringColumn(ThriftCluster cluster, String cfName)
	{

		ColumnFamilyDefinition cfd = HFactory.createColumnFamilyDefinition(this.cassandraKeyspace, cfName, ComparatorType.UTF8TYPE);
		cfd.setKeyValidationClass("org.apache.cassandra.db.marshal.UTF8Type");
		cfd.setComparatorType(ComparatorType.UTF8TYPE);
		cluster.addColumnFamily(cfd);
	}

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

			// ColumnFamilyDefinition userCfd = HFactory.createColumnFamilyDefinition(this.cassandraKeyspace, USER_CF, ComparatorType.UTF8TYPE);
			// userCfd.setKeyValidationClass(UTF8TYPE);
			// cluster.addColumnFamily(userCfd);
			//
			//
			// ColumnFamilyDefinition userRegistrationCfd = HFactory.createColumnFamilyDefinition(this.cassandraKeyspace, USER_REGISTRATION_CF,
			// ComparatorType.UTF8TYPE);
			// userRegistrationCfd.setKeyValidationClass(UTF8TYPE);
			// userRegistrationCfd.setComparatorType(ComparatorType.UTF8TYPE);
			// cluster.addColumnFamily(userRegistrationCfd);
			//
			//
			// ColumnFamilyDefinition userNetworkRandingCfd = HFactory.createColumnFamilyDefinition(this.cassandraKeyspace, USER_NETWORK_RANKING_CF,
			// ComparatorType.UTF8TYPE);
			// userNetworkRandingCfd.setKeyValidationClass(UTF8TYPE);
			// userNetworkRandingCfd.setComparatorType(ComparatorType.UTF8TYPE);
			// cluster.addColumnFamily(userNetworkRandingCfd);
			//
			//
			// ColumnFamilyDefinition userNetworkUsageCfd = HFactory.createColumnFamilyDefinition(this.cassandraKeyspace, USER_NETWORK_USAGE_CF,
			// ComparatorType.UTF8TYPE);
			// userNetworkUsageCfd.setKeyValidationClass(UTF8TYPE);
			// cluster.addColumnFamily(userNetworkUsageCfd);

			for (Field field : ColumnFamilyKeys.class.getDeclaredFields())
			{
				if (Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))
				{
					String cf = (String) field.get(null);
					addColumnFamilyWithStringColumn(cluster, cf);

				}
			}
		}

		this.keyspace = HFactory.createKeyspace(cassandraKeyspace, cluster, consistencyLevelPolicy);

		// CqlQuery<String, String, Long> cqlQuery = new CqlQuery<String, String, Long>(this.keyspace, se, se, le);
		// StringBuilder query = new StringBuilder();
		// query.append("CREATE TABLE USER_NETWORK_USAGE_CF (");
		// query.append("  login varchar,");
		// query.append("  networkName varchar,");
		// query.append("  shareCounter int,");
		// query.append("  PRIMARY KEY (login, networkName)");
		// query.append(");");
		// cqlQuery.setQuery(query.toString());
		// cqlQuery.execute();
	}

	public Keyspace getKeyspace()
	{
		return keyspace;
	}

}
