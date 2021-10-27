package com.mindata.challenge.exceptionhandling;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ErrorHandler {
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@ExceptionHandler(value = { EntityNotFoundException.class })
	public @ResponseBody ExceptionResponse handEntityNotFoundException(final Exception e,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		e.printStackTrace();

		return error;
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
			ConstraintViolationException.class, MethodArgumentTypeMismatchException.class })
	public @ResponseBody ExceptionResponse handMethodArgumentNotValidException(final Exception e,
			final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		e.printStackTrace();

		return error;
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody ExceptionResponse handleException(final Exception e, final HttpServletRequest request) {

		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(e.getMessage());
		error.setRequestedURI(request.getRequestURI());

		e.printStackTrace();

		return error;
	}
}
