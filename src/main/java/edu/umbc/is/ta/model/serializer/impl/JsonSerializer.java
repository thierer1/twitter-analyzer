package edu.umbc.is.ta.model.serializer.impl;

import org.apache.commons.lang3.Validate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.umbc.is.ta.model.serializer.Serializer;

public class JsonSerializer<T> implements Serializer<String, T> {
	
	private final Class<T> type;
	
	public JsonSerializer(Class<T> type) {
		Validate.notNull(type, "type must not be null");
		this.type = type;
	}

	@Override
	public String serialize(Object obj) {
		if(!type.isInstance(obj)) {
			return null;
		}
		
		return getParser().toJson(getType().cast(obj), obj.getClass());
	}
	
	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public T deserialize(String obj) {
		return getParser().fromJson(obj, getType());
	}
	
	public GsonBuilder registerTypes(GsonBuilder forBuilder) {
		Validate.notNull(forBuilder, "forBuilder must not be null");
		return forBuilder;
	}
	
	protected Gson getParser() {
		return new Gson();
	}
}
