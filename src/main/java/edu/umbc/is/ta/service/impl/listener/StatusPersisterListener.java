package edu.umbc.is.ta.service.impl.listener;

import org.apache.commons.lang3.Validate;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.impl.TweetImpl;
import edu.umbc.is.ta.service.MongoDataService;
import twitter4j.Status;

public class StatusPersisterListener extends BasicStatusListener {

	private final MongoDataService<Tweet> dataService;
	
	public StatusPersisterListener(MongoDataService<Tweet> dataService) {
		Validate.notNull(dataService, "dataService must not be null");
		this.dataService = dataService;
	}
	
	@Override
	public void onStatus(Status status) {
		super.onStatus(status);
		getDataService().insert(new TweetImpl(status));
	}
	
	private MongoDataService<Tweet> getDataService() {
		return dataService;
	}

}
