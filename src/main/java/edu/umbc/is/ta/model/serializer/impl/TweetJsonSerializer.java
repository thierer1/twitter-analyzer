package edu.umbc.is.ta.model.serializer.impl;

import edu.umbc.is.ta.model.Tweet;

public class TweetJsonSerializer extends JsonSerializer<Tweet> {

	public TweetJsonSerializer() {
		super(Tweet.class);
	}

}
