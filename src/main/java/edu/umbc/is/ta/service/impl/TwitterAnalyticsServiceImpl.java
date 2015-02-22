package edu.umbc.is.ta.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;
import edu.umbc.is.ta.service.TwitterAnalyticsService;

public class TwitterAnalyticsServiceImpl implements TwitterAnalyticsService {
	private static final Logger LOGGER = LogManager.getLogger(TwitterAnalyticsServiceImpl.class.getName());
	
	private Map<User, List<Tweet>> twitterMap;
	
	public TwitterAnalyticsServiceImpl() {
		twitterMap = new HashMap<User, List<Tweet>>();
	}

	@Override
	public void addTweet(Tweet tweet) {
		Validate.notNull(tweet, "tweet must not be null");
		final User user = tweet.getUser();
		
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Adding tweet for userId={}, tweetId={}", user.getId(),
				tweet.getId());
		}
		
		getTweets(user).add(tweet);
	}
	
	@Override
	public void addTweets(Collection<Tweet> tweets) {
		Validate.notNull(tweets, "tweet collection must not be null");
		for(Tweet tweet : tweets) {
			addTweet(tweet);
		}
	}
	
	@Override
	public Collection<User> getUsers() {
		return twitterMap.keySet();
	}
	
	@Override
	public Collection<Tweet> getTweets(User forUser) {
		Validate.notNull(forUser, "forUser must not be null");
		
		if(!twitterMap.containsKey(forUser)) {
			if(LOGGER.isTraceEnabled()) {
				LOGGER.trace("Creating new list for userId={}", forUser.getId());
			}
			
			twitterMap.put(forUser, new ArrayList<Tweet>());
		}
		
		return twitterMap.get(forUser);
	}
	
	@Override
	public int getNumUsers() {
		return twitterMap.size();
	}
	
	@Override
	public int getNumTweets(User forUser) {
		return getTweets(forUser).size();
	}
	
	@Override
	public List<User> getRankedUsers() {
		final List<User> rankedUsers = new ArrayList<User>();
		
		rankedUsers.addAll(twitterMap.keySet());
		Collections.sort(rankedUsers, new TweetListComparator());
		
		return rankedUsers;
	}
	
	@Override
	public List<User> getTopRankedUsers(int topN) {
		return getRankedUsers().subList(0, topN);
	}
	
	private class TweetListComparator implements Comparator<User> {

		@Override
		public int compare(User user1, User user2) {
			final Integer user1Tweets = getTweets(user1).size();
			final Integer user2Tweets = getTweets(user2).size();
			
			if(LOGGER.isTraceEnabled()) {
				LOGGER.trace("Comparing userId={},{}; numTweets={},{}",
					user1.getId(), user2.getId(), user1Tweets, user2Tweets);
			}
			
			return user2Tweets.compareTo(user1Tweets);
		}
	}

}
