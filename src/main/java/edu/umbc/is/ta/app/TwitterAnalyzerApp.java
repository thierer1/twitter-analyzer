package edu.umbc.is.ta.app;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;
import edu.umbc.is.ta.service.ConfigurationService;
import edu.umbc.is.ta.service.SerializerService;
import edu.umbc.is.ta.service.TwitterAnalyticsService;
import edu.umbc.is.ta.service.TwitterService;
import edu.umbc.is.ta.service.impl.JsonSerializerServiceImpl;
import edu.umbc.is.ta.service.impl.SimpleConfigurationServiceImpl;
import edu.umbc.is.ta.service.impl.TwitterAnalyticsServiceImpl;
import edu.umbc.is.ta.service.impl.TwitterServiceImpl;
import edu.umbc.is.ta.service.impl.TwitterStreamingServiceImpl;

public class TwitterAnalyzerApp {
	
	public static String toJson(List<Tweet> tweets) {
		final SerializerService<String> service = new JsonSerializerServiceImpl();
		return service.serialize(tweets);
	}
	
	public static List<Tweet> fromJson(String tweetsJson) {
		final SerializerService<String> service = new JsonSerializerServiceImpl();
		return service.deserializeArray(tweetsJson, Tweet.class);
	}

	public static void main(String[] args) throws InterruptedException {	
		final String queryStr = args.length > 0 ? args[0] : "";
		final Integer queryLimit = args.length > 1 ? NumberUtils.toInt(args[1]) : null;
		final ConfigurationService configService;
		final TwitterService service;
		final List<Tweet> results;
		final TwitterAnalyticsService analyticsService = new TwitterAnalyticsServiceImpl();
		final List<User> popularUsers;
		
		Validate.notBlank(queryStr, "queryStr must not be blank");
		
		System.out.format(
			"Searching Twitter for \"%s\", returning %d results...\n", 
			queryStr, queryLimit);

		configService = new SimpleConfigurationServiceImpl();
		
		TwitterStreamingServiceImpl.test(configService.getAppToken(), configService.getUserToken());
		
		
//		service = new TwitterServiceImpl(configService.getAppToken());
//		
//		if (queryLimit != null) {
//			results = service.search(queryStr, configService.getUserToken(),
//				queryLimit);
//		} else {
//			results = service.search(queryStr, configService.getUserToken());
//		}
//		
//		System.out.format("Found %d tweets!\n", results.size());
//		
//		if (!results.isEmpty()) {
//			System.out.format("[0]=%s\n", results.get(0));
//			System.out.format("[%d]=%s\n", results.size() - 1, 
//				results.get(results.size() - 1));
//		}
//		
//		analyticsService.addTweets(results);
//		popularUsers = analyticsService.getTopRankedUsers(5);
//		
//		System.out.format("Found %d users!\n", analyticsService.getNumUsers());
//		
//		for (User popularUser : popularUsers) {
//			final int tweetsForUser = analyticsService.getNumTweets(popularUser);
//			System.out.format("%5d\t@%s\n", tweetsForUser,
//				popularUser.getScreenName());
//		}
	}
}
