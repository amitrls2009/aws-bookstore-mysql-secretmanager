package com.bookstore.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse {
	private Object body;	
	private Map<String, String> headers;
}
