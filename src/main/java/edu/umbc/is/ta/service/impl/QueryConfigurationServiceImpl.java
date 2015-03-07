package edu.umbc.is.ta.service.impl;

import org.apache.commons.lang3.Validate;

import edu.umbc.is.ta.service.QueryConfigurationService;

public class QueryConfigurationServiceImpl 
extends SimpleConfigurationServiceImpl implements QueryConfigurationService {

	private final String query;
	private final Integer numResults;
	
	public QueryConfigurationServiceImpl(String query, Integer numResults) {
		Validate.notBlank(query, "query must not be blank");
		this.query = query;
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
	
	@Override 
	public String getQuery() {
		return query;
	}

}
