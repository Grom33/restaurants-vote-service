package ru.gromov.resvote.service;

import ru.gromov.resvote.model.User;
import ru.gromov.resvote.to.UserTo;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */
public interface ProfileService {
	User updateLoggedUser(User user, UserTo loggedUser);

	User userRegistration(User user);
}
