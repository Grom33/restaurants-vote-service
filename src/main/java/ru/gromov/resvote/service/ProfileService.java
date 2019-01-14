package ru.gromov.resvote.service;

import ru.gromov.resvote.model.User;

import java.util.List;
import java.util.Optional;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface ProfileService {

	User getLoggedUser();

	void updateLoggedUser(User user);

	List<User> getAll();

	User create(User user);

	User getById(long id);

	void update(User user);

	void delete(long id);

}
