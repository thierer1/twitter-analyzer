package edu.umbc.is.ta.service;

import edu.umbc.is.ta.model.UserToken;

public interface TwitterStreamingService {

	TwitterStreamingClient startCollecting(String queryStr, UserToken userToken);
}
