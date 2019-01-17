package ru.gromov.resvote.util.exception;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class AlreadyExistException extends RuntimeException {
	public AlreadyExistException(String message) {
		super(message);
	}
}
