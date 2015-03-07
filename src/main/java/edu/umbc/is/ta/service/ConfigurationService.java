package edu.umbc.is.ta.service;

import edu.umbc.is.ta.model.ApplicationToken;
import edu.umbc.is.ta.model.UserToken;

public interface ConfigurationService {

	UserToken getUserToken();
	ApplicationToken getAppToken();
}
