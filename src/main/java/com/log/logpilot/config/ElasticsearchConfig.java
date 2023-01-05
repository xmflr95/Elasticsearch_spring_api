package com.log.logpilot.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.log.logpilot.model.ElasticsearchConfiguration;

@Component
public class ElasticsearchConfig {
	
	private Environment env;
	
	private String host;
	private int port;
	private String scheme;
	
	private ElasticsearchConfiguration elasticsearchConfiguration;
	
	public ElasticsearchConfig(Environment env) {
		this.env = env;
		host = env.getProperty("es.host");
		port = Integer.parseInt(env.getProperty("es.port"));
		scheme = env.getProperty("es.scheme");
		
		this.elasticsearchConfiguration = ElasticsearchConfiguration.builder()
											.host(host)
											.port(port)
											.scheme(scheme)
											.build();
	}
	
	@Bean
	RestHighLevelClient elasticClient() {
		HttpHost elasticHost = new HttpHost(
				this.elasticsearchConfiguration.getHost(),
				this.elasticsearchConfiguration.getPort(),
				this.elasticsearchConfiguration.getScheme()
			);
		
		return new RestHighLevelClient(RestClient.builder(elasticHost));		
	}
}
