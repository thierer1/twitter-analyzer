package edu.umbc.is.ta.service.impl;

import java.util.Collection;

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

}
