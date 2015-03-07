package edu.umbc.is.ta.service.impl;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import edu.umbc.is.ta.service.StreamingConfigurationService;

public class StreamingConfigurationServiceImpl extends SimpleConfigurationServiceImpl implements StreamingConfigurationService {
	public static final String NUM_THREADS_KEY = "edu.umbc.is.ta.numThreads";
	
	private List<String> query;
	
	public StreamingConfigurationServiceImpl(List<String> query) {
		Validate.notNull(query, "query must not be null");
		Validate.isTrue(!query.isEmpty(), "must have at least 1 query term");
		this.query = query;
	}

	@Override
	public int getNumThreads() {
		final int numThreads = NumberUtils.toInt(System.getProperty(
			NUM_THREADS_KEY));
		
		Validate.isTrue(numThreads > 0, "numThreads must be greater than 0");
		
		return numThreads;
	}

	@Override
	public List<String> getQuery() {
		return query;
	}

}
