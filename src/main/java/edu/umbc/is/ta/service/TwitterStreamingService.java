package edu.umbc.is.ta.service;

import java.util.List;

import edu.umbc.is.ta.model.UserToken;

public interface TwitterStreamingService {

	TwitterStreamingClient startCollecting(List<String> query, UserToken userToken);
}
