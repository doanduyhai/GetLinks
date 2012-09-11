package fr.getlinks.repository.impl;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TimeUUIDSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.EntityManagerImpl;

import com.eaio.uuid.UUID;

public class CassandraAbstractRepository
{
	protected static final Serializer<String> se = StringSerializer.get();

	protected static final Serializer<Long> le = LongSerializer.get();

	protected static final Serializer<Object> oe = ObjectSerializer.get();

	protected static final Serializer<UUID> te = TimeUUIDSerializer.get();

	protected EntityManagerImpl em;

	protected Keyspace keyspaceOperator;

	public void setEm(EntityManagerImpl em)
	{
		this.em = em;
	}

	public void setKeyspaceOperator(Keyspace keyspaceOperator)
	{
		this.keyspaceOperator = keyspaceOperator;
	}

	protected void removeRowFromCF(String CF, String key)
	{
		CqlQuery<String, String, Object> cqlQuery = new CqlQuery<String, String, Object>(keyspaceOperator, se, se, oe);
		cqlQuery.setQuery(" DELETE FROM " + CF + " WHERE KEY = '" + key + "';");
		cqlQuery.execute();

	}
}
