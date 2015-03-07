package edu.umbc.is.ta.service;

public interface QueryConfigurationService extends ConfigurationService {

	boolean hasLimit();
	int getNumResults();
}
