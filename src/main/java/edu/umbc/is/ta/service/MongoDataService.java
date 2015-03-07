package edu.umbc.is.ta.service;

import java.util.Collection;

public interface MongoDataService<T> {
	
	boolean insert(T document);
	Collection<T> findAll();
}
