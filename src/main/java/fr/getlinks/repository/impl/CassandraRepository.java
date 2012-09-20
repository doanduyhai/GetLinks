package fr.getlinks.repository.impl;

import static me.prettyprint.hector.api.factory.HFactory.createSliceQuery;

import java.util.Collection;
import java.util.List;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TimeUUIDSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hom.EntityManagerImpl;

import com.eaio.uuid.UUID;

public class CassandraRepository
{
	public static final Serializer<String> se = StringSerializer.get();

	public static final Serializer<Long> le = LongSerializer.get();

	public static final Serializer<Integer> ie = IntegerSerializer.get();

	public static final Serializer<Object> oe = ObjectSerializer.get();

	public static final Serializer<UUID> te = TimeUUIDSerializer.get();

	protected EntityManagerImpl em;

	protected Keyspace keyspace;

	public void setEm(EntityManagerImpl em)
	{
		this.em = em;
	}

	public void setKeyspace(Keyspace keyspace)
	{
		this.keyspace = keyspace;
	}

	protected void removeRowFromCF(String CF, String key)
	{
		CqlQuery<String, String, Object> cqlQuery = new CqlQuery<String, String, Object>(keyspace, se, se, oe);
		cqlQuery.setQuery(" DELETE FROM " + CF + " WHERE KEY = '" + key + "';");
		cqlQuery.execute();

	}

	protected void insertIntoCF(String CF, String key, String itemId)
	{
		Mutator<String> mutator = HFactory.createMutator(keyspace, se);
		mutator.insert(key, CF, HFactory.createColumn(itemId, "", se, oe));
	}

	protected void insertIntoCFWithValue(String CF, String key, String itemId, Object value)
	{
		Mutator<String> mutator = HFactory.createMutator(keyspace, se);
		mutator.insert(key, CF, HFactory.createColumn(itemId, value, se, oe));
	}

	public Object getValueFromCF(String CF, String key, String itemId)
	{
		Object result = null;
		HColumn<String, Object> column = HFactory.createColumnQuery(keyspace, se, se, oe).setColumnFamily(CF).setKey(key).setName(itemId).execute()
				.get();
		if (column != null)
		{
			result = column.getValue();
		}
		return result;
	}

	protected Collection<HColumn<String, Object>> findColumnsRangeFromCF(String CF, String key, String startItemId, boolean reverse, int count)
	{
		List<HColumn<String, Object>> columns = createSliceQuery(keyspace, se, se, oe).setColumnFamily(CF).setKey(key)
				.setRange(startItemId, null, reverse, count).execute().get().getColumns();

		return columns;
	}

	protected void removeFromCF(String CF, String key, String itemId)
	{
		Mutator<String> mutator = HFactory.createMutator(keyspace, se);
		mutator.delete(key, CF, itemId, se);
	}
}
