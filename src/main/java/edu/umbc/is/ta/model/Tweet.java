package edu.umbc.is.ta.model;

import java.util.Date;
import java.util.List;

public interface Tweet {

	long getId();
	String getText();
	User getUser();
	Date getCreatedAt();
	int getNumFavorites();
	int getNumRetweets();
	List<String> getHashTags();
	Tweet getRetweetOf();
	boolean isRetweet();
}
