package com.log.logpilot.service;

import java.util.List;

import com.log.logpilot.model.ElasticModel;
import com.log.logpilot.model.SourceModel;

public interface LogService {
	
	boolean indexExist(String index);
	
	List<ElasticModel> find(String index);
	SourceModel find(String index, String id);
}
