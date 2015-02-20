package edu.umbc.is.ta.model.impl;

import edu.umbc.is.ta.model.ApplicationToken;

public class ApplicationTokenImpl extends AbstractToken implements ApplicationToken {

	public ApplicationTokenImpl(String key, String secret) {
		super(key, secret);
	}
}
