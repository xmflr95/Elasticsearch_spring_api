package com.log.logpilot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.log.logpilot.model.ElasticModel;
import com.log.logpilot.model.SourceModel;
import com.log.logpilot.service.LogService;

@RestController
@RequestMapping("/api/v1")
public class LogController {
	
	private final Logger log = LoggerFactory.getLogger(LogController.class);
	
	private Environment env;
	private LogService logService;
	
	public LogController(Environment env, LogService logService) {
		this.env = env;
		this.logService = logService;
	}
	
	@GetMapping("/health")
	public String checkHealth() {
		return "Server running on PORT : " + env.getProperty("server.port");
	}
	
	@GetMapping("/doc/{index}")
	public String existCheckIndex(@PathVariable(name = "index", required = false) String index) {
		boolean result = logService.indexExist(index);
		
		if (!result) {
			return "Index is not Exist!";
		}
		
		return "Index is Exist!";
	}
	
	@GetMapping("/doc/search/{index}")
	public List<ElasticModel> search(@PathVariable(name = "index", required = false) String index) {
		return logService.find(index);
	}
	
	@GetMapping("/doc/search/{index}/{id}")
	public SourceModel getDocument(@PathVariable(name = "index", required = false) String index, 
								@PathVariable(name = "id", required = false) String id) {		
		return logService.find(index, id);
	}
}
