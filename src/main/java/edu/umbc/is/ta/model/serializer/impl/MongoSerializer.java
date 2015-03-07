package edu.umbc.is.ta.model.serializer.impl;

import org.apache.commons.lang3.Validate;

import com.mongodb.DBObject;

public abstract class MongoSerializer<T> extends AbstractSerializer<DBObject, T> {

	final JsonSerializer<T> jsonSerializer;
	
	public MongoSerializer(Class<T> type, JsonSerializer<T> jsonSerializer) {
		super(type);
		Validate.notNull(jsonSerializer, "jsonSerializer must not be null");
		this.jsonSerializer = jsonSerializer;
	}
	
	@Override
	public DBObject serialize(Object obj) {
		if(!isType(obj)) {
			return null;
		}
		
		return getSerialization(castAsType(obj));
	}

	@Override
	public T deserialize(DBObject obj) {
		if(obj == null) {
			return null;
		}
		
		return getDeserialization(obj);
	}
	
	protected abstract DBObject getSerialization(T fromObj);
	
	protected T getDeserialization(DBObject fromObj) {
		return jsonSerializer.deserialize(fromObj.toString());
	}

}
