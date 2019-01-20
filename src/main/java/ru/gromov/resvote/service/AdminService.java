package ru.gromov.resvote.service;

import ru.gromov.resvote.model.User;

import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface AdminService {

	List<User> getAll();

	User create(User user);

	User getById(long id);

	User update(User user);

	void delete(long id);

}
