package ru.gromov.resvote.web;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gromov.resvote.util.exception.DeadLineException;
import ru.gromov.resvote.util.exception.NotFoundException;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<Object> handleNotFoundException(final Exception ex) {
		return new ResponseEntity<>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<Object> handleIllegalArgException(final Exception ex) {
		return new ResponseEntity<>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler({DeadLineException.class})
	public ResponseEntity<Object> handleDeadlineException(final Exception ex) {
		return new ResponseEntity<>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<Object> handleSQLException(final Exception ex) {
		return new ResponseEntity<>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}


}
