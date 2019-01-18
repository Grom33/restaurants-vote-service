package ru.gromov.resvote.util;

import ru.gromov.resvote.model.User;
import ru.gromov.resvote.to.UserTo;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public class UserUtil {
	private UserUtil() {
	}

	public static User getUserFromTo(UserTo userTo) {
		return new User(userTo.getId(), userTo.getName(),
				userTo.getEmail(), userTo.getPassword());
	}

	public static UserTo getTo(User user) {
		return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword());
	}

}
