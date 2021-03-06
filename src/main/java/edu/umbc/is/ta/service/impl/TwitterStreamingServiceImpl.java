package edu.umbc.is.ta.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.StatusListener;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;

import edu.umbc.is.ta.model.ApplicationToken;
import edu.umbc.is.ta.model.UserToken;
import edu.umbc.is.ta.service.TwitterStreamingClient;
import edu.umbc.is.ta.service.TwitterStreamingService;

public class TwitterStreamingServiceImpl implements TwitterStreamingService {
	
	private static final Logger LOGGER = LogManager.getLogger(TwitterStreamingServiceImpl.class.getName());
	private static final int DEFAULT_NUM_THREADS = 1;
	
	private final ApplicationToken appToken;
	private final int numThreads;
	private final List<StatusListener> listeners;
	
	public TwitterStreamingServiceImpl(ApplicationToken appToken) {
		this(appToken, DEFAULT_NUM_THREADS);
	}
	
	public TwitterStreamingServiceImpl(ApplicationToken appToken, int numThreads) {
		Validate.notNull(appToken, "appToken must not be null");
		this.appToken = appToken;
		this.numThreads = numThreads;
		this.listeners = new ArrayList<StatusListener>();
	}
	
	public TwitterStreamingClient startCollecting(List<String> query, UserToken userToken) {
		Validate.notNull(query, "query most not be blank");
		Validate.isTrue(!query.isEmpty(), "query must not be empty");
		Validate.notNull(userToken, "userToken must not be null");
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Listening for: {}", query);
		}
		
		final List<StatusListener> listeners = getListeners();
		final Twitter4jStatusClient client = getStatuses(query, userToken,
			listeners, getNumThreads());
		
		return new TwitterStreamingClientImpl(client);
	}
	
	public int getNumThreads() {
		return numThreads;
	}
	
	@Override
	public void addListener(StatusListener statusListener) {
		listeners.add(statusListener);
	}
	
	private List<StatusListener> getListeners() {
		return listeners;
	}
	
	private Twitter4jStatusClient getStatuses(List<String> terms, 
			UserToken userToken, List<StatusListener> listeners, 
			int numThreads) {
		final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>(100000);
		final Twitter4jStatusClient statusClient = new Twitter4jStatusClient(
			buildClient(terms, userToken, messageQueue), messageQueue,
			listeners, Executors.newFixedThreadPool(numThreads));
		
		statusClient.connect();
		
		for (int i = 0; i < numThreads; i++) {
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Kicking off process #{}", (i + 1));
			}
			
			statusClient.process();
		}
		
		return statusClient;
	}
	
	private Client buildClient(List<String> terms, UserToken userToken, 
			BlockingQueue<String> msgQueue) {
		final Hosts hosts = new HttpHosts(Constants.STREAM_HOST);
		final StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		final Authentication auth = new OAuth1(
			appToken.getKey(), appToken.getSecret(), 
			userToken.getKey(), userToken.getSecret());
		
		endpoint.trackTerms(terms);
		
		return (new ClientBuilder()
			.hosts(hosts)
			.authentication(auth)
			.endpoint(endpoint)
			.processor(new StringDelimitedProcessor(msgQueue))).build();
	}
}
