package edu.umbc.is.ta.service.impl;

import org.apache.commons.lang3.Validate;

import edu.umbc.is.ta.model.ApplicationToken;
import edu.umbc.is.ta.model.UserToken;
import edu.umbc.is.ta.model.impl.ApplicationTokenImpl;
import edu.umbc.is.ta.model.impl.UserTokenImpl;
import edu.umbc.is.ta.service.ConfigurationService;

public class SimpleConfigurationServiceImpl implements ConfigurationService {	
	public static final String USER_TOKEN_KEY = "edu.umbc.is.ta.userKey";
	public static final String USER_TOKEN_SECRET = "edu.umbc.is.ta.userSecret";
	public static final String APP_TOKEN_KEY = "edu.umbc.is.ta.appKey";
	public static final String APP_TOKEN_SECRET = "edu.umbc.is.ta.appSecret";

	@Override
	public UserToken getUserToken() {
		final String key = System.getProperty(USER_TOKEN_KEY);
		final String secret = System.getProperty(USER_TOKEN_SECRET);
		
		Validate.notBlank(key, 
			"User[key] not found; expected system property \"%s\"",
			USER_TOKEN_KEY);
		Validate.notBlank(secret, 
			"User[secret] not found; expected system property \"%s\"",
			USER_TOKEN_SECRET );
		
		return new UserTokenImpl(key, secret);
	}

	@Override
	public ApplicationToken getAppToken() {
		final String key = System.getProperty(APP_TOKEN_KEY);
		final String secret = System.getProperty(APP_TOKEN_SECRET);
		
		Validate.notBlank(key,
			"Application[key] not found; expected system property \"%s\"",
			APP_TOKEN_KEY );
		Validate.notBlank(secret, 
			"Application[secret] not found; expected system property \"%s\"", 
			APP_TOKEN_SECRET);
		
		return new ApplicationTokenImpl(key, secret);
	}

}
