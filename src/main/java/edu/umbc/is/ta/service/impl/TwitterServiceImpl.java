package edu.umbc.is.ta.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private static final Logger LOGGER = LogManager.getLogger(TwitterServiceImpl.class.getName());
	private static final int TWITTER_SEARCH_MAX_PAGE_SIZE = 100;

	private final ApplicationToken appToken;
	
	public TwitterServiceImpl(ApplicationToken appToken) {
		Validate.notNull(appToken, "appToken must not be null");
		this.appToken = appToken; 
	}
	
	@Override
	public List<Tweet> search(String queryStr, UserToken forUser) {
		Validate.notBlank(queryStr, "queryStr cannot be blank");
		
		return search(getEndpoint(forUser), newQuery(queryStr), 
			new ArrayList<Tweet>());
	}
	
	@Override
	public List<Tweet> search(String queryStr, UserToken forUser, int limit) {
		Validate.notBlank(queryStr, "queryStr cannot be blank");
		Validate.isTrue(limit >= 1, "limit must be greater than 0");
		
		return search(getEndpoint(forUser), newQuery(queryStr, limit), 
			new ArrayList<Tweet>(), limit);
	}
	
	private Query newQuery(String queryStr) {
		return newQuery(queryStr, null);
	}
	
	private Query newQuery(String queryStr, Integer limit) {
		Validate.notBlank(queryStr);
		final Query query = new Query(queryStr);
		
		if (limit != null) {
			final int count = limit > TWITTER_SEARCH_MAX_PAGE_SIZE 
				? TWITTER_SEARCH_MAX_PAGE_SIZE : limit;
			query.setCount(count);
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Setting query count={}", count);
			}
		}
		
		return query;
	}
	
	private final List<Tweet> search(Twitter endpoint, Query query, 
			List<Tweet> found) {
		return search(endpoint, query, found, null);
	}
	
	private final List<Tweet> search(Twitter endpoint, Query query, 
			List<Tweet> found, Integer maxResults) {
		return search(endpoint, query, found, maxResults, null);
	}
	
	private final List<Tweet> search(Twitter endpoint, Query query, 
			List<Tweet> found, Integer maxResults, Long startAt) {
		Validate.notNull(endpoint);
		Validate.notNull(query);
		Validate.notNull(found);
		
		QueryResult result = null;
		Long lowestId = null;
		
		if (maxResults == null || found.size() < maxResults) {
			if (startAt != null) {
				query.setMaxId(startAt - 1);
			}
			
			try {
				result = endpoint.search(query);
			} catch (TwitterException e) {
				LOGGER.error(e);
				result = null;
			}
			
			if (result != null && result.getCount() > 0) {
				for (Status status : result.getTweets()) {
					final Tweet tweet = new TweetImpl(status); 
					found.add(tweet);
					
					if (lowestId == null || status.getId() < lowestId) {
						lowestId = status.getId();
					}
					
					if (LOGGER.isTraceEnabled()) {
						LOGGER.trace("Retrieved tweet: {}", tweet);
					}
				}
				
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Retrieved {} tweets; setting startAt={}", 
						result.getCount(), lowestId);
				}
				
				return search(endpoint, query, found, maxResults, lowestId);
			}
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Done retrieving; total={}", found.size());
		}
		
		return found;
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
