package ru.gromov.resvote.util;

import ru.gromov.resvote.model.HasId;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class ValidationUtil {
	private ValidationUtil() {
	}

	public static void assureIdConsistent(HasId bean, long id) {
		//      http://stackoverflow.com/a/32728226/548473
		if (bean.isNew()) {
			bean.setId(id);
		} else if (bean.getId() != id) {
			throw new IllegalArgumentException(bean + " must be with id=" + id);
		}
	}

	//    http://stackoverflow.com/a/28565320/548473
	public static Throwable getRootCause(Throwable t) {
		Throwable result = t;
		Throwable cause;

		while (null != (cause = result.getCause()) && (result != cause)) {
			result = cause;
		}
		return result;
	}

}
