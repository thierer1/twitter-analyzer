package edu.umbc.is.ta.service;

import java.util.List;

public interface StreamingConfigurationService extends ConfigurationService {

	int getNumThreads();
	List<String> getQuery();
	
}
