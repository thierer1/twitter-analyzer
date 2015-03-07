package edu.umbc.is.ta.service.impl;

import edu.umbc.is.ta.service.QueryConfigurationService;

public class QueryConfigurationServiceImpl 
extends SimpleConfigurationServiceImpl implements QueryConfigurationService {

	private final Integer numResults;
	
	public QueryConfigurationServiceImpl(String query, Integer numResults) {
		super(query);
		this.numResults = numResults;
	}

	@Override
	public boolean hasLimit() {
		return numResults != null;
	}

	@Override
	public int getNumResults() {
		return numResults != null ? numResults : 0;
	}

}
