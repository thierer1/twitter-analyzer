package edu.umbc.is.ta.model.impl;

import edu.umbc.is.ta.model.User;

public class UserImpl implements User {
	
	private final String screenName;
	
	public UserImpl(String screenName) {
		this.screenName = screenName;
	}
	
	public UserImpl(twitter4j.User user) {
		this(user.getScreenName());
	}

	@Override
	public String getScreenName() {
		return screenName;
	}

}
