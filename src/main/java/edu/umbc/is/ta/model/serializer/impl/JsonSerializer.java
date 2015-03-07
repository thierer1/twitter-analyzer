package edu.umbc.is.ta.model.serializer.impl;

import org.apache.commons.lang3.Validate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer<T> extends AbstractSerializer<String, T> {
	
	public JsonSerializer(Class<T> type) {
		super(type);
	}

	@Override
	public String serialize(Object obj) {
		if(!isType(obj)) {
			return null;
		}
		
		return getParser().toJson(castAsType(obj), obj.getClass());
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
