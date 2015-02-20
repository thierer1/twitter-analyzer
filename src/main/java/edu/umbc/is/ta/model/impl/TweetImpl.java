package edu.umbc.is.ta.model.impl;

import twitter4j.Status;
import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;

public class TweetImpl implements Tweet {
	
	private final String text;
	private final User user;
	
	public TweetImpl(String text, User user) {
		this.text = text;
		this.user = user;
	}
	
	public TweetImpl(Status tweet) {
		this(tweet.getText(), new UserImpl(tweet.getUser()));
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public User getUser() {
		return user;
	}

}
