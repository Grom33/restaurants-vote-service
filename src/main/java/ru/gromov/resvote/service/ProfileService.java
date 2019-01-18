package ru.gromov.resvote.service;

import ru.gromov.resvote.model.User;
import ru.gromov.resvote.to.UserTo;

import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface ProfileService {

	UserTo getLoggedUser();

	User updateLoggedUser(User user);

	List<User> getAll();

	User create(User user);

	User getById(long id);

	User update(User user);

	void delete(long id);

	User userRegistration(User user);
}
