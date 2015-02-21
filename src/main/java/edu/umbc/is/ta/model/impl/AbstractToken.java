package edu.umbc.is.ta.model.impl;

import org.apache.commons.lang3.Validate;

import edu.umbc.is.ta.model.Token;

public abstract class AbstractToken implements Token {
	
	private final String key;
	private final String secret;
	
	public AbstractToken(String key, String secret) {
		Validate.notBlank(key, "key must not be blank");
		Validate.notBlank(secret, "secret must not be blank");
		this.key = key;
		this.secret = secret;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getSecret() {
		return secret;
	}
	
	@Override
	public String toString() {
		return String.format("AbstractToken{key=\"%s\", token=\"***\"}", key);
	}

}
