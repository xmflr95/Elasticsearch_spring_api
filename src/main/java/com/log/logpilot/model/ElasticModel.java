package com.log.logpilot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ElasticModel {
	private String id;
	private String index;
//	private String type; // deprecated
	private Float score;
	private SourceModel source;
}
