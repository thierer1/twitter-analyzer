package edu.umbc.is.ta.service;

import java.util.List;

import edu.umbc.is.ta.model.serializer.Serializer;

public interface SerializerService<K> {

	void addSerializer(Serializer<K, ?> serializer);
	boolean hasSerializer(Class<?> forType);
	K serialize(Object obj);
	<T> T deserialize(K object, Class<T> toType);
	<T> List<T> deserializeArray(K array, Class<T> elementType);
	
}
