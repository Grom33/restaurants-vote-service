package ru.gromov.resvote.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.gromov.resvote.util.exception.*;

import javax.servlet.http.HttpServletRequest;

import static ru.gromov.resvote.util.exception.ErrorType.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
@ResponseBody
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

	//  http://stackoverflow.com/a/22358422/548473
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({NotFoundException.class})
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ErrorInfo handleNotFoundException(HttpServletRequest req, Exception ex) {
		log.error("422 Status Code, Data not found: {}", ex.getMessage());
		return getErrorInfo(req, ex, DATA_NOT_FOUND);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler({
			MethodArgumentTypeMismatchException.class,
			HttpMessageNotReadableException.class})
	public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception ex) {
		log.error("422 Status Code, Data validation error {}", ex.getMessage());
		return getErrorInfo(req, ex, DATA_ERROR);
	}


	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler({IllegalArgumentException.class})
	public ErrorInfo handleIllegalArgException(HttpServletRequest req, Exception ex) {
		log.error("409 Status Code {}", ex.getMessage());
		return getErrorInfo(req, ex, VALIDATION_ERROR);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler({DeadLineException.class})
	public ErrorInfo handleDeadlineException(HttpServletRequest req, Exception ex) {
		log.debug("409 Status Code {}", ex.getMessage());
		return getErrorInfo(req, ex, DEADLINE_ERROR);
	}

	@ResponseStatus(value = HttpStatus.CONFLICT)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler({AlreadyExistException.class})
	public ErrorInfo handleAlreadyExist(HttpServletRequest req, RuntimeException ex) {
		log.error("409 Status Code: {} ", ex.getMessage());
		return getErrorInfo(req, ex, VALIDATION_ERROR);
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ExceptionHandler({AccessDeniedException.class})
	public void handleAccessDenied() {
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@Order
	@ExceptionHandler({Exception.class})
	public ErrorInfo handleAppError(HttpServletRequest req, RuntimeException ex) {
		log.error("500 Status Code: {} ", ex.getMessage());
		return getErrorInfo(req, ex, APP_ERROR);
	}

	@Order(Ordered.HIGHEST_PRECEDENCE)
	@ResponseStatus(value = HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ErrorInfo conflict(HttpServletRequest req, Exception ex) {
		log.error("409 Status Code: {} ", ex.getMessage());
		return getErrorInfo(req, ex, DATA_ERROR);
	}

	private ErrorInfo getErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType) {
		log.info("req {}, e {}, error:{}", req, e, errorType);
		return new ErrorInfo(req.getRequestURL(), errorType, e.getMessage());
	}

}
