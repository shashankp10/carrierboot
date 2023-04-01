package com.AI_project.exception;

import lombok.Data;

@Data
public class ApiResponse {
	private String message;
	private boolean success;
	public ApiResponse(String message) {
		this.message = message;
	}
}
