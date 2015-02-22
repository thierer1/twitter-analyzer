package edu.umbc.is.ta.model.impl;

import java.util.Date;

import edu.umbc.is.ta.model.User;

public class UserImpl implements User {
	
	private final long id;
	private final String screenName;
	private final Date createdAt;
	private final int numFavorites;
	private final int numFollowers;
	private final int numFollowings;
	private final int numTweets;
	
	public UserImpl(long id, String screenName, Date createdAt, 
			int numFavorites, int numFollowers, int numFollowings, 
			int numTweets) {
		this.id = id;
		this.screenName = screenName;
		this.createdAt = createdAt;
		this.numFavorites = numFavorites;
		this.numFollowers = numFollowers;
		this.numFollowings = numFollowings;
		this.numTweets = numTweets;
	}

	public UserImpl(twitter4j.User user) {
		this(user.getId(), user.getScreenName(), user.getCreatedAt(), 
			user.getFavouritesCount(), user.getFollowersCount(), 
			user.getFriendsCount(), user.getStatusesCount());
	}
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getScreenName() {
		return screenName;
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
	public int getNumFollowers() {
		return numFollowers;
	}
	
	@Override
	public int getNumFollowings() {
		return numFollowings;
	}
	
	@Override
	public int getNumTweets() {
		return numTweets;
	}
	
	@Override 
	public String toString() {
		return String.format("UserImpl{id=%d, screenName=\"%s\", " + 
			"createdAt=%s, numFavorites=%d, numFollowers=%d, " + 
			"numFollowings=%d, numTweets=%d}", id, screenName, createdAt,
			numFavorites, numFollowers, numFollowings, numTweets);
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
		UserImpl other = (UserImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
