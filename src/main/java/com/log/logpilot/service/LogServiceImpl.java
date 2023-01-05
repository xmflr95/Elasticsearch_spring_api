package com.log.logpilot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.log.logpilot.model.ElasticModel;
import com.log.logpilot.model.SourceModel;

@Service
public class LogServiceImpl implements LogService {
	
	private final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
	
	private RestHighLevelClient elasticClient;	
	private ObjectMapper objectMapper;
	
	@Autowired
	public LogServiceImpl(RestHighLevelClient elasticClient, ObjectMapper objectMapper) {
		this.elasticClient = elasticClient;
		this.objectMapper = objectMapper;
	}

	@Override
	public boolean indexExist(String index) {
		boolean acknowledged = false;

		try {
			GetIndexRequest request = new GetIndexRequest(index);
			acknowledged = elasticClient.indices().exists(request, RequestOptions.DEFAULT);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		
		return acknowledged;
	}

	/**
	 * Find All data
	 */
	@Override
	public List<ElasticModel> find(String index) {
		List<ElasticModel> result = new ArrayList<>();
		
		if (!this.indexExist(index)) {
			log.info("Not Found Index.");
			return null;
		}
		
		SearchSourceBuilder searhSourceBuilder = new SearchSourceBuilder();
		searhSourceBuilder.from(0);
		searhSourceBuilder.size(500);
		searhSourceBuilder.query(QueryBuilders.matchAllQuery());
		
		SearchRequest request = new SearchRequest(index);
		request.source(searhSourceBuilder);
		
		SearchResponse response = null;
		try {
			response = elasticClient.search(request, RequestOptions.DEFAULT);
			
			SearchHits hits = response.getHits();
			SearchHit[] searchHits = hits.getHits();
			
			for (SearchHit hit : searchHits) {				
//				log.info("{}", hit);
//				log.info("SourceMap: {}", hit.getSourceAsMap());				
				ElasticModel elasticData = ElasticModel.builder()
											.index(hit.getIndex())
											.id(hit.getId())
											.score(hit.getScore())
											.source(objectMapper.convertValue(hit.getSourceAsMap(), SourceModel.class))
											.build();
				result.add(elasticData);
			}
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				log.error("Not Found Index.");
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		return result;
	}

	/**
	 * Find Single Data By id
	 * @throws Exception 
	 */
	@Override
	public SourceModel find(String index, String id) {
		if (!this.indexExist(index)) {
			log.error("Not Found Index.");
			return null;
		}
		
		GetRequest request = new GetRequest(index);
		request.id(id);
		
		GetResponse response = null;
		
		try {
			response = elasticClient.get(request, RequestOptions.DEFAULT);
			if (!response.isExists()) {
				log.error("Not Found Document.");
				return null;
			}
		} catch (ElasticsearchException e) {
			if (e.status() == RestStatus.NOT_FOUND) {
				log.error("Not Found Index.");
				return null;
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return null;
		}	
		
		SourceModel resSource = objectMapper.convertValue(response.getSourceAsMap(), SourceModel.class);
		
		log.info("res :: {}", resSource);
		
		return resSource;	
	}
	
	
}
