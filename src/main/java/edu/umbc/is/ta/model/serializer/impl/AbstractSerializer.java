package edu.umbc.is.ta.model.serializer.impl;

import org.apache.commons.lang3.Validate;

import edu.umbc.is.ta.model.serializer.Serializer;

public abstract class AbstractSerializer<K, T> implements Serializer<K, T> {

	private final Class<T> type;
	
	public AbstractSerializer(Class<T> type) {
		Validate.notNull(type, "type must not be null");
		this.type = type;
	}
	
	@Override
	public Class<T> getType() {
		return type;
	}
	
	protected boolean isType(Object obj) {
		return getType().isInstance(obj);
	}
	
	protected T castAsType(Object obj) {
		return getType().cast(obj);
	}
}
