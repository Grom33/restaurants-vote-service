package ru.gromov.resvote.util.exception;

import lombok.Data;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Data
public class ErrorInfo {
	private final String url;
	private final String type;
	private final String detail;

	public ErrorInfo(CharSequence url, ErrorType type, String ex) {
		this.url = url.toString();
		this.type = type.name();
		this.detail = ex;
	}
}
