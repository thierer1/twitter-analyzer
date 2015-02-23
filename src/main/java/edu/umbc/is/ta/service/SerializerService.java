package edu.umbc.is.ta.service;

import edu.umbc.is.ta.model.serializer.Serializer;

public interface SerializerService<K> {

	void addSerializer(Serializer<K, ?> serializer);
	boolean hasSerializer(Class<?> forType);
	K serialize(Object obj);
	
}
