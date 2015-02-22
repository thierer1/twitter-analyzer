package edu.umbc.is.ta.service;

import java.util.Collection;
import java.util.List;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;

public interface TwitterAnalyticsService {

	void addTweet(Tweet tweet);
	void addTweets(Collection<Tweet> tweets);
	Collection<User> getUsers();
	Collection<Tweet> getTweets(User forUser);
	int getNumUsers();
	int getNumTweets(User forUser);
	List<User> getRankedUsers();
	List<User> getTopRankedUsers(int topN);
}
