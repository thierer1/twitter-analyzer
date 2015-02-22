package edu.umbc.is.ta.model;

import java.util.Date;

public interface User {

	long getId();
	String getScreenName();
	Date getCreatedAt();
	int getNumFavorites();
	int getNumFollowers();
	int getNumFollowings();
	int getNumTweets();
}
