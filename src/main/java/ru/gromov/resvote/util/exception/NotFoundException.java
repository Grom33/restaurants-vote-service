package ru.gromov.resvote.util.exception;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}
