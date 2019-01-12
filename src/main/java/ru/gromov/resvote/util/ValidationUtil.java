package ru.gromov.resvote.util;

import org.springframework.data.domain.Persistable;
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

}
