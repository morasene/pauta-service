package br.com.pauta.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -7115458404179999536L;

	public BusinessException() {
		super();
	}
	
	public BusinessException(String message) {
		super(message);
	}

}
