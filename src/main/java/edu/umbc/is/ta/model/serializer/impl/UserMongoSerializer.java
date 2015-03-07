package edu.umbc.is.ta.model.serializer.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import edu.umbc.is.ta.model.User;

public class UserMongoSerializer extends MongoSerializer<User> {

	public UserMongoSerializer() {
		super(User.class, new UserJsonSerializer());
	}

	@Override
	protected DBObject getSerialization(User fromObj) {
		if (fromObj == null) {
			return null;
		}
		
		final BasicDBObject dbObject = new BasicDBObject();
		
		dbObject.append("id", fromObj.getId());
		dbObject.append("screenName", fromObj.getScreenName());
		dbObject.append("createdAt", fromObj.getCreatedAt());
		dbObject.append("numFavorites", fromObj.getNumFavorites());
		dbObject.append("numFollowers", fromObj.getNumFollowers());
		dbObject.append("numFollowings", fromObj.getNumFollowings());
		dbObject.append("numTweets", fromObj.getNumTweets());
		
		return dbObject;
	}

}
