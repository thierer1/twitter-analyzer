package edu.umbc.is.ta.model.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import edu.umbc.is.ta.model.Tweet;
import edu.umbc.is.ta.model.User;

public class TweetImpl implements Tweet {
	
	private final long id;
	private final String text;
	private final User user;
	private final Date createdAt;
	private final int numFavorites;
	private final int numRetweets;
	private final List<String> hashTags;
	private Tweet retweetOf;
	
	public TweetImpl(long id, String text, User user, Date createdAt, 
			int numFavorites, int numRetweets) {
		this(id, text, user, createdAt, numFavorites, numRetweets, 
			new ArrayList<String>(), null);
	}
	
	public TweetImpl(long id, String text, User user, Date createdAt, 
			int numFavorites, int numRetweets, List<String> hashTags, 
			Tweet retweetOf) {
		this.id = id;
		this.text = text;
		this.user = user;
		this.createdAt = createdAt;
		this.numFavorites = numFavorites;
		this.numRetweets = numRetweets;
		this.hashTags = hashTags;
		this.retweetOf = retweetOf;
	}
	
	public TweetImpl(Status tweet) {
		this(tweet.getId(), tweet.getText(), new UserImpl(tweet.getUser()),
			tweet.getCreatedAt(), tweet.getFavoriteCount(), 
			tweet.getRetweetCount());
		
		final HashtagEntity[] hashtags = tweet.getHashtagEntities();
		final Status retweetOf = tweet.getRetweetedStatus();
		
		if(hashtags != null && hashtags.length > 0) {
			for (int i = 0; i < hashtags.length; i++) {
				this.addHashTag(hashtags[i].getText());
			}
		}
		
		if(retweetOf != null) {
			this.setRetweetOf(new TweetImpl(retweetOf));
		}
	}
	
	private void addHashTag(String hashTag) {
		this.hashTags.add(hashTag);
	}
	
	private void setRetweetOf(Tweet retweetOf) {
		this.retweetOf = retweetOf;
	}
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public User getUser() {
		return user;
	}
	
	@Override
	public Date getCreatedAt() {
		return createdAt;
	}
	
	@Override
	public int getNumFavorites() {
		return numFavorites;
	}
	
	@Override
	public int getNumRetweets() {
		return numRetweets;
	}
	
	@Override
	public List<String> getHashTags() {
		return hashTags;
	}
	
	@Override
	public Tweet getRetweetOf() {
		return retweetOf;
	}
	
	@Override
	public boolean isRetweet() {
		return retweetOf != null;
	}
	
	@Override
	public String toString() {
		return String.format("TweetImpl{id=%d, text=\"%s\", user=%s, " + 
			"createdAt=%s, numFavorites=%d, numRetweets=%d, hashTags=[%s], " + 
			"isRetweet=%b}", id, text, user, createdAt, numFavorites, 
			numRetweets, StringUtils.join(hashTags, ','), this.isRetweet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TweetImpl other = (TweetImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
