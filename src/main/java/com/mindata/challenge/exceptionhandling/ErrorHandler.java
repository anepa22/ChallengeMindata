package com.mindata.challenge.exceptionhandling;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;

@ControllerAdvice
public class ErrorHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ExceptionHandler(value = { EntityNotFoundException.class })
	public @ResponseBody ExceptionResponse handEntityNotFoundException(final Exception e,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		logger.error(HttpStatus.NO_CONTENT.getReasonPhrase(),e);

		return error;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			ConstraintViolationException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
	public @ResponseBody ExceptionResponse handMethodNotAllowedException(final Exception e,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		logger.error(HttpStatus.BAD_REQUEST.getReasonPhrase(),e);

		return error;
	}
	
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	public @ResponseBody ExceptionResponse handMethodArgumentNotValidException(final Exception e,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		logger.error(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),e);

		return error;
	}
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = { AuthJWTException.class, JWTDecodeException.class, SignatureVerificationException.class, MissingRequestHeaderException.class })
	public @ResponseBody ExceptionResponse handUserNotValidException(final Exception e,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		logger.error(HttpStatus.UNAUTHORIZED.getReasonPhrase(),e);

		return error;
	}
	

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody ExceptionResponse handleException(final Exception e, final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		logger.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),e);

		return error;
	}
}
