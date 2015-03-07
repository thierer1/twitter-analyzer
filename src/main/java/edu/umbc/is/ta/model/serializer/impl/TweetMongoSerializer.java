package edu.umbc.is.ta.model.serializer.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;

public class TweetMongoSerializer extends MongoSerializer<Tweet> {

	final MongoSerializer<User> userSerializer;
	
	public TweetMongoSerializer() {
		super(Tweet.class, new TweetJsonSerializer());
		userSerializer = new UserMongoSerializer();
	}

	@Override
	protected DBObject getSerialization(Tweet fromObj) {
		if (fromObj == null) {
			return null;
		}
		
		final BasicDBObject dbObject = new BasicDBObject();
		
		dbObject.append("id", fromObj.getId());
		dbObject.append("text", fromObj.getText());
		dbObject.append("createdAt", fromObj.getCreatedAt());
		dbObject.append("numFavorites", fromObj.getNumFavorites());
		dbObject.append("numRetweets", fromObj.getNumRetweets());
		dbObject.append("hashTags", fromObj.getHashTags());
		dbObject.append("isRetweet", fromObj.isRetweet());
		dbObject.append("retweetOf", getSerialization(fromObj.getRetweetOf()));
		dbObject.append("user", userSerializer.getSerialization(fromObj.getUser()));
		
		return dbObject;
	}

}
