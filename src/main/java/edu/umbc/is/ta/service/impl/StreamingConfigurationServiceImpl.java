package edu.umbc.is.ta.service.impl;

import org.apache.commons.lang3.Validate;

import edu.umbc.is.ta.service.StreamingConfigurationService;

public class StreamingConfigurationServiceImpl extends SimpleConfigurationServiceImpl implements StreamingConfigurationService {

	private final int numThreads;
	
	public StreamingConfigurationServiceImpl(String query, int numThreads) {
		super(query);
		Validate.isTrue(numThreads > 0, "numThreads must be greater than 0");
		this.numThreads = numThreads;
	}

	@Override
	public int getNumThreads() {
		return numThreads;
	}

}
