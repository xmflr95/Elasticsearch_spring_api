package com.log.logpilot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ElasticsearchConfiguration {
	private String host;
	private int port;
	private String scheme;
}
