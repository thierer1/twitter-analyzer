package edu.umbc.is.ta.model.impl;

import edu.umbc.is.ta.model.UserToken;

public class UserTokenImpl extends AbstractToken implements UserToken {

	public UserTokenImpl(String key, String secret) {
		super(key, secret);
	}
}
