package edu.umbc.is.ta.model.serializer;

public interface Serializer<K,T> {
	
	K serialize(Object obj);
	T deserialize(K obj);
	Class<T> getType();
}
