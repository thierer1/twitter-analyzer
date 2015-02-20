package edu.umbc.is.ta.app;

import java.util.List;

import org.apache.commons.lang3.Validate;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.service.ConfigurationService;
import edu.umbc.is.ta.service.TwitterService;
import edu.umbc.is.ta.service.impl.SimpleConfigurationServiceImpl;
import edu.umbc.is.ta.service.impl.TwitterServiceImpl;

public class TwitterAnalyzerApp {

	public static void main(String[] args) {
		final String queryStr = args.length > 0 ? args[0] : "";
		final ConfigurationService configService;
		final TwitterService service;
		final List<Tweet> results;
		
		Validate.notBlank(queryStr, "queryStr must not be blank");
		
		System.out.format("Searching Twitter for \"%s\"", queryStr);

		configService = new SimpleConfigurationServiceImpl();
		service = new TwitterServiceImpl(configService.getAppToken());
		results = service.search("@umbc", configService.getUserToken());
		
		System.out.format("Found %d tweets!\n", results.size());
		System.out.format("[0]=%s [@%s]\n", results.get(0).getText(), 
			results.get(0).getUser().getScreenName());
		
	}
}
