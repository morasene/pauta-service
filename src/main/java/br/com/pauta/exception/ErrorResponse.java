package br.com.pauta.exception;

import java.util.Collection;

public class ErrorResponse {

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String message, Collection<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	private String message;
	private Collection<String> details;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<String> getDetails() {
		return details;
	}

	public void setDetails(Collection<String> details) {
		this.details = details;
	}

}