package edu.umbc.is.ta.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import edu.umbc.is.ta.model.ApplicationToken;
import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.UserToken;
import edu.umbc.is.ta.model.impl.TweetImpl;
import edu.umbc.is.ta.service.TwitterService;

public class TwitterServiceImpl implements TwitterService {

	private final ApplicationToken appToken;
	
	public TwitterServiceImpl(ApplicationToken appToken) {
		Validate.notNull(appToken, "appToken must not be null");
		this.appToken = appToken; 
	}
	
	@Override
	public List<Tweet> search(String queryStr, UserToken forUser) {
		Validate.notBlank(queryStr, "queryStr cannot be blank");
		
		final Twitter endpoint = getEndpoint(forUser);
		final Query query = new Query(queryStr);
		final List<Tweet> tweets = new ArrayList<Tweet>();
		QueryResult result = null; 
		
		try {
			result = endpoint.search(query);
		} catch (TwitterException e) {
			// TODO
		}
		
		if (result != null) {
			for (Status status : result.getTweets()) {
				tweets.add(new TweetImpl(status));
			}
		}
		
		return tweets;
	}
	
	protected final Twitter getEndpoint(UserToken forUser) {
		Validate.notNull(forUser, "forUser must not be null");
		
		final Twitter twitter = TwitterFactory.getSingleton();
		final ApplicationToken appToken = getAppToken();
		
		twitter.setOAuthConsumer(appToken.getKey(), appToken.getSecret());
		twitter.setOAuthAccessToken(new AccessToken(forUser.getKey(), 
			forUser.getSecret()));
		
		return twitter;
	}
	
	private final ApplicationToken getAppToken() {
		return appToken;
	}
}
