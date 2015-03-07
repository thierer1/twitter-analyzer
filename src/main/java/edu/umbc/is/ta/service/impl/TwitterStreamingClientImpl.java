package edu.umbc.is.ta.service.impl;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.twitter.hbc.twitter4j.Twitter4jStatusClient;

import edu.umbc.is.ta.service.TwitterStreamingClient;

public class TwitterStreamingClientImpl implements TwitterStreamingClient {

	private static final Logger LOGGER = LogManager.getLogger(TwitterStreamingClientImpl.class.getName());
	
	private Twitter4jStatusClient client;
	
	public TwitterStreamingClientImpl(Twitter4jStatusClient client) {
		Validate.notNull(client, "client must not be null");
		this.client = client;
	}

	@Override
	public void stop() {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Stopping the streaming client...");
		}
		
		client.stop();
	}
}
