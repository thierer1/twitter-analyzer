package edu.umbc.is.ta.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.umbc.is.ta.model.serializer.Serializer;
import edu.umbc.is.ta.service.SerializerService;

public abstract class AbstractSerializerService<K> implements SerializerService<K> {
	private static final Logger LOGGER = LogManager.getLogger(AbstractSerializerService.class.getName());
	
	private final Map<Class<?>, Serializer<K, ?>> serializers;
	
	public AbstractSerializerService() {
		this.serializers = new HashMap<Class<?>, Serializer<K, ?>>();
	}

	@Override
	public void addSerializer(Serializer<K, ?> serializer) {
		Validate.notNull(serializer, "serializer must not be null");
		serializers.put(serializer.getType(), serializer);
	}

	@Override
	public boolean hasSerializer(Class<?> forType) {
		if (forType == null) {
			return false;
		}
		
		boolean found = getSerializer(forType) != null;
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Checking for serializer for {}... {}found!",
				forType.getSimpleName(), (found ? "" : "NOT "));
		}
		
		return found;
	}

	@Override
	public K serialize(Object obj) {
		if(obj == null) {
			return null;
		}
		
		final Serializer<K, ?> serializer = getSerializer(obj.getClass());
		return (serializer != null) ? serializer.serialize(obj) : null;
	}
	
	protected final Serializer<K, ?> getSerializer(Class<?> forType) {
		Validate.notNull(forType, "forType must not be null");
		
		final Set<Class<?>> classSet = serializers.keySet();
		final Iterator<Class<?>> classIterator = classSet.iterator();
		Serializer<K, ?> serializer = null;
		
		while(serializer == null && classIterator.hasNext()) {
			final Class<?> aClass = classIterator.next();
			if(aClass.isAssignableFrom(forType)) {
				serializer = serializers.get(aClass);
			}
		}
		
		return serializer;
	}

}
