package br.com.pauta.exception;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String BAD_REQUEST = "BAD_REQUEST";
	private static final String NOT_FOUND = "NOT_FOUND";

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return new ResponseEntity<>(
				new ErrorResponse(BAD_REQUEST,
						ex.getBindingResult().getFieldErrors().stream()
								.map(field -> String.format("%s - %s", field.getField(), field.getDefaultMessage())).collect(Collectors.toList())),
				status);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, Collections.singletonList(ex.getLocalizedMessage()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public final ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse(NOT_FOUND, Collections.singletonList(ex.getLocalizedMessage()));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, Collections.singletonList(ex.getMostSpecificCause().getMessage()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<ErrorResponse> handleRecordNotFoundException(BusinessException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse(NOT_FOUND, Collections.singletonList(ex.getLocalizedMessage()));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, Collections.singletonList(ex.getMostSpecificCause().getMessage()));
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
