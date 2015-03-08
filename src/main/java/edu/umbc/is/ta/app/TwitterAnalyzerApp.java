package edu.umbc.is.ta.app;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;
import edu.umbc.is.ta.model.serializer.impl.TweetMongoSerializer;
import edu.umbc.is.ta.service.QueryConfigurationService;
import edu.umbc.is.ta.service.SerializerService;
import edu.umbc.is.ta.service.StreamingConfigurationService;
import edu.umbc.is.ta.service.TwitterAnalyticsService;
import edu.umbc.is.ta.service.TwitterService;
import edu.umbc.is.ta.service.TwitterStreamingClient;
import edu.umbc.is.ta.service.TwitterStreamingService;
import edu.umbc.is.ta.service.impl.JsonSerializerServiceImpl;
import edu.umbc.is.ta.service.impl.MongoCollectionDataServiceImpl;
import edu.umbc.is.ta.service.impl.MongoDataServiceImpl;
import edu.umbc.is.ta.service.impl.StreamingConfigurationServiceImpl;
import edu.umbc.is.ta.service.impl.TwitterAnalyticsServiceImpl;
import edu.umbc.is.ta.service.impl.TwitterServiceImpl;
import edu.umbc.is.ta.service.impl.TwitterStreamingServiceImpl;
import edu.umbc.is.ta.service.impl.listener.StatusPersisterListener;

public class TwitterAnalyzerApp {
	
	private static final Logger LOGGER = LogManager.getLogger(TwitterAnalyzerApp.class.getName());
	
	public static String toJson(List<Tweet> tweets) {
		final SerializerService<String> service = new JsonSerializerServiceImpl();
		return service.serialize(tweets);
	}
	
	public static List<Tweet> fromJson(String tweetsJson) {
		final SerializerService<String> service = new JsonSerializerServiceImpl();
		return service.deserializeArray(tweetsJson, Tweet.class);
	}
	
	public static void stream(StreamingConfigurationService configService) throws Exception {
		Validate.notNull(configService, "configService cannot be null");
		final TwitterStreamingService service = new TwitterStreamingServiceImpl(
			configService.getAppToken(), configService.getNumThreads());
		final Thread mainThread = Thread.currentThread();
		final TwitterStreamingClient client;
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Setting up streaming...");
		}
	
		service.addListener(new StatusPersisterListener(
			new MongoCollectionDataServiceImpl<Tweet>(
				MongoDataServiceImpl.getDatabaseConnection("localhost", 27017, "twitterAnalyzer"), 
				new TweetMongoSerializer(), configService.getCollectionName())));
		
		client = service.startCollecting(configService.getQuery(),
			configService.getUserToken());
		
		// listen for the SIGTERM to shut down cleanly
		Runtime.getRuntime().addShutdownHook(new Thread () {
			public void run() {
				// stop the client 
				client.stop();
			}
		});
		
		// main thread should wait until notified, or SIGTERMed
		try {
			synchronized (mainThread) { mainThread.wait(); }
		} catch (InterruptedException e) {
			
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Main thread caught interuppted exception...");
			}
			
			// stop the client 
			client.stop();
			
			// log the error
			LOGGER.error(e);
		}
	}
	
	public static List<Tweet> query(QueryConfigurationService configService) {
		Validate.notNull(configService, "configService must not be null");
		final TwitterService service = new TwitterServiceImpl(configService.getAppToken());
		final List<Tweet> results;
		
		if(configService.hasLimit()) {
			results = service.search(configService.getQuery(), 
				configService.getUserToken(), configService.getNumResults());
		} else {
			results = service.search(configService.getQuery(),
				configService.getUserToken());
		}
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Found {} tweets", results.size());
		}
		
		return results;
	}
	
	public static void analyze(List<Tweet> tweets) {
		Validate.notNull(tweets, "tweets must not be null");
		final TwitterAnalyticsService analyticsService = new TwitterAnalyticsServiceImpl();
		final List<User> popularUsers;
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Analyzing {} tweets...", tweets.size());
		}
		
		analyticsService.addTweets(tweets);
		popularUsers = analyticsService.getTopRankedUsers(5);
		
		System.out.format("Found %d users\n", analyticsService.getNumUsers());
		
		for (User popularUser : popularUsers) {
			final int tweetsForUser = analyticsService.getNumTweets(popularUser);
			System.out.format("%5d\t@%s\n", tweetsForUser,
				popularUser.getScreenName());
		}
	}

	public static void main(String[] args) throws Exception {	
		final String queryStr = args.length > 0 ? args[0] : "";
		
		Validate.notBlank(queryStr, "queryStr must not be blank");
		
		// below is for RESTful API
//		final QueryConfigurationService configService = 
//			new QueryConfigurationServiceImpl(queryStr, 
//			(args.length > 1 ? NumberUtils.toInt(args[1]) : null));
//		
//		analyze(query(configService));
		
		// below is for streaming API
		final StreamingConfigurationService configService = 
			new StreamingConfigurationServiceImpl(Lists.newArrayList(args));

		stream(configService);
	}
}
