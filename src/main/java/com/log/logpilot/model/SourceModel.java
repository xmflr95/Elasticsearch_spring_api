package com.log.logpilot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SourceModel {
	private String name;
	private int age;
	private String message;
}
