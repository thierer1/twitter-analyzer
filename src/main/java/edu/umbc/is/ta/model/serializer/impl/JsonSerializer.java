package edu.umbc.is.ta.model.serializer.impl;

import org.apache.commons.lang3.Validate;

import com.google.gson.Gson;

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
		
		return (new Gson()).toJson(type.cast(obj), obj.getClass());
	}
	
	@Override
	public Class<T> getType() {
		return type;
	}
}
