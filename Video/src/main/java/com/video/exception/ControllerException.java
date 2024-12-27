package com.video.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ControllerException extends RuntimeException {
	private String errorCode;
	private String errorMessage;

	public ControllerException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}

