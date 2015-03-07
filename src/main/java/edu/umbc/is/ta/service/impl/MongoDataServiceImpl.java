package edu.umbc.is.ta.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;

import edu.umbc.is.ta.model.serializer.Serializer;
import edu.umbc.is.ta.service.MongoDataService;

public class MongoDataServiceImpl<T> implements MongoDataService<T> {
	
	private static final Logger LOGGER = LogManager.getLogger(MongoDataServiceImpl.class.getName());

	private final DB mongoDB;
	private final Serializer<DBObject, T> serializer;
	
	public MongoDataServiceImpl(DB mongoDB, Serializer<DBObject, T> serializer) {
		Validate.notNull(mongoDB, "client must not be null");
		Validate.notNull(serializer, "serializer must not be null");
		this.mongoDB = mongoDB;
		this.serializer = serializer;
	}
	
	@Override
	public boolean insert(T document) {
		final DBObject toInsert = serialize(document);
		boolean success = false;
		
		try {
			success = getCollection().insert(toInsert).getN() > 0;
		} catch (MongoException e) {
			LOGGER.error(e);
			success = false;
		}
		
		return success;
	}

	@Override
	public Collection<T> findAll() {
		final DBCursor cursor = getCollection().find();
		final List<T> found = new ArrayList<T>();
		
		while(cursor.hasNext()) {
			found.add(deserialize(cursor.next()));
		}
		
		return found;
	}
	
	protected final DB getDatabase() {
		return mongoDB;
	}
	
	protected final Serializer<DBObject, T> getSerializer() {
		return serializer;
	}
	
	protected final DBObject serialize(T item) {
		return getSerializer().serialize(item);
	}
	
	protected final T deserialize(DBObject item) {
		return getSerializer().deserialize(item);
	}
	
	protected DBCollection getCollection() {
		return getCollection(getCollectionName());
	}
	
	protected DBCollection getCollection(String name) {
		return getDatabase().getCollection(name);
	}

	protected String getCollectionName() {
		return serializer.getType().getSimpleName() + "Collection";
	}
	
	public static DB getDatabaseConnection(String host, int port, String dbName) throws Exception {
		Validate.notBlank(host, "host must not be blank");
		Validate.isTrue(port > 0, "port must be greater than 0");
		Validate.notBlank(dbName, "dbName must not be blank");
		return MongoClientFactoryImpl.getMongoClient(host, port).getDB(dbName);
	}
}
