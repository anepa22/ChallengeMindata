package com.mindata.challenge.exceptionhandling;

import lombok.Data;

@Data
public class ExceptionResponse {
	private String errorMessage;
	private String requestedURI;
}
