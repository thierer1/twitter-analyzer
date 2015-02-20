package edu.umbc.is.ta.service;

import java.util.List;

import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.UserToken;

public interface TwitterService {

	List<Tweet> search(String queryStr, UserToken forUser);
}
