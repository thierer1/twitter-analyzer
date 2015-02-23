package edu.umbc.is.ta.model.serializer.impl;

import edu.umbc.is.ta.model.User;

public class UserJsonSerializer extends JsonSerializer<User> {

	public UserJsonSerializer() {
		super(User.class);
	}

}
