package edu.umbc.is.ta.service.impl.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class BasicStatusListener implements StatusListener {
	
	private static final Logger LOGGER = LogManager.getLogger(BasicStatusListener.class.getName());

	@Override
	public void onException(Exception ex) {
		LOGGER.error(ex);
		ex.printStackTrace();
	}

	@Override
	public void onStatus(Status status) {
		LOGGER.info("Found status: {}", status);
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		LOGGER.info("Status was deleted: {}", statusDeletionNotice.getStatusId());
		
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		LOGGER.info("Track limitation: {}", numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		LOGGER.info("Scrubbed geo info: {} to {}", userId, upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		LOGGER.warn("Stall warning: {} ({}%)", warning.getMessage(),
			warning.getPercentFull());
	}

}
