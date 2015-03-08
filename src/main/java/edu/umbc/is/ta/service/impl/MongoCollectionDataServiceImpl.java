package edu.umbc.is.ta.service.impl;

import org.apache.commons.lang3.Validate;

import com.mongodb.DB;
import com.mongodb.DBObject;

import edu.umbc.is.ta.model.serializer.Serializer;

public class MongoCollectionDataServiceImpl<T> extends MongoDataServiceImpl<T> {
	
	private final String collectionName;
	
	public MongoCollectionDataServiceImpl(DB mongoDB,
			Serializer<DBObject, T> serializer, String collectionName) {
		super(mongoDB, serializer);
		Validate.notBlank(collectionName, "collectionName must not be blank");
		this.collectionName = collectionName;
	}
	
	@Override
	protected String getCollectionName() {
		return collectionName;
	}
	
	
}
