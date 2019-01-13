package ru.gromov.resvote.web;

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
	private ResponseEntity<Object> handleNotFoundException(Exception ex) {
		return new ResponseEntity<>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({IllegalArgumentException.class})
	private ResponseEntity<Object> handleIlligalArgException(Exception ex) {
		return new ResponseEntity<>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler({DeadLineException.class})
	private ResponseEntity<Object> handleDeadlineException(Exception ex) {
		return new ResponseEntity<>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
}
