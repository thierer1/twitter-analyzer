package edu.umbc.is.ta.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import edu.umbc.is.ta.model.serializer.Serializer;
import edu.umbc.is.ta.model.serializer.impl.JsonSerializer;
import edu.umbc.is.ta.model.serializer.impl.TweetJsonSerializer;
import edu.umbc.is.ta.model.serializer.impl.UserJsonSerializer;

public class JsonSerializerServiceImpl extends AbstractSerializerService<String> {
	
	@SuppressWarnings("rawtypes")
	public JsonSerializerServiceImpl() {
		super();
		this.addSerializer(new TweetJsonSerializer());
		this.addSerializer(new UserJsonSerializer());
		this.addSerializer(new JsonSerializer<Collection>(Collection.class));
	}

	@Override
	public <T> T deserialize(String object, Class<T> toType) {
		Validate.notBlank(object, "object must not be blank");
		Validate.notNull(toType, "toType must not be null");
		
		if(!hasSerializer(toType)) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		final Serializer<String, T> serializer = 
			(Serializer<String, T>) getSerializer(toType);
		return serializer.deserialize(object);
	}

	@Override
	public <T> List<T> deserializeArray(String array, Class<T> elementType) {
		Validate.notBlank(array, "array must not be blank");
		Validate.notNull(elementType, "elementType must not be null");
		
		if(!hasSerializer(elementType)) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		final Serializer<String, T> serializer = 
			(Serializer<String, T>) getSerializer(elementType);
		final List<T> elements = new ArrayList<T>();
		final JsonParser parser = new JsonParser();
		final JsonArray jsonArr = parser.parse(array).getAsJsonArray();
		final Iterator<JsonElement> jsonIterator = jsonArr.iterator();
		
		while(jsonIterator.hasNext()) {
			final JsonElement jsonElement = jsonIterator.next();
			elements.add(serializer.deserialize(jsonElement.toString()));
		}
		
		return elements;
	}
	

}
