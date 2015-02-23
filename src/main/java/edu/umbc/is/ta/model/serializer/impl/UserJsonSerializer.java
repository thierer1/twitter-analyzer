package edu.umbc.is.ta.model.serializer.impl;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.umbc.is.ta.model.User;
import edu.umbc.is.ta.model.impl.UserImpl;

public class UserJsonSerializer extends JsonSerializer<User> {

	public UserJsonSerializer() {
		super(User.class);
	}
	
	@Override
	protected Gson getParser() {
		final GsonBuilder gsonBuilder = registerTypes(new GsonBuilder());
		return gsonBuilder.create();
	}
	
	@Override
	public GsonBuilder registerTypes(GsonBuilder forBuilder) {
		forBuilder = super.registerTypes(forBuilder);
		forBuilder.registerTypeAdapter(User.class, new UserDeserializer());
		return forBuilder;
	}
	
	private static class UserDeserializer implements JsonDeserializer<User> {

		@Override
		public User deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			final JsonObject jsonObj = json.getAsJsonObject();
			
			return new UserImpl(
				jsonObj.get("id").getAsLong(),
				jsonObj.get("screenName").getAsString(),
				(Date) context.deserialize(jsonObj.get("createdAt"), Date.class),
				jsonObj.get("numFavorites").getAsInt(),
				jsonObj.get("numFollowers").getAsInt(),
				jsonObj.get("numFollowings").getAsInt(),
				jsonObj.get("numTweets").getAsInt()
			);
		}
		
	}
}
