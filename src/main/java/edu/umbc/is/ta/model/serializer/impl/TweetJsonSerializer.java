package edu.umbc.is.ta.model.serializer.impl;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;
import edu.umbc.is.ta.model.impl.TweetImpl;

public class TweetJsonSerializer extends JsonSerializer<Tweet> {

	private final JsonSerializer<User> userSerializer;
	
	public TweetJsonSerializer() {
		super(Tweet.class);
		userSerializer = new UserJsonSerializer();
	}
	
	@Override
	protected Gson getParser() {
		final GsonBuilder gsonBuilder = registerTypes(new GsonBuilder());
		return gsonBuilder.create();
	}
	
	@Override
	public GsonBuilder registerTypes(GsonBuilder forBuilder) {
		forBuilder = super.registerTypes(forBuilder);
		forBuilder.registerTypeAdapter(Tweet.class, new TweetDeserializer());
		return userSerializer.registerTypes(forBuilder);
	}
	
	private static class TweetDeserializer implements JsonDeserializer<Tweet> {

		@Override
		public Tweet deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			final JsonObject jsonObj = json.getAsJsonObject();
			final JsonArray hashtags = jsonObj.get("hashTags").getAsJsonArray();
			final TweetImpl tweet = new TweetImpl(
				jsonObj.get("id").getAsLong(), 
				jsonObj.get("text").getAsString(),
				(User) context.deserialize(jsonObj.get("user"), User.class),
				(Date) context.deserialize(jsonObj.get("createdAt"), Date.class),
				jsonObj.get("numFavorites").getAsInt(),
				jsonObj.get("numRetweets").getAsInt());
			
			for(JsonElement element : hashtags) {
				tweet.addHashTag(element.getAsString());
			}
			
			if(jsonObj.has("retweetOf")) {
				tweet.setRetweetOf((Tweet) context.deserialize(
					jsonObj.get("retweetOf"), Tweet.class));
			}
			
			return tweet;
		}
		
	}
}
