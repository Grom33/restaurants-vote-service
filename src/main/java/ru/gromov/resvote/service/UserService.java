package ru.gromov.resvote.service;

import ru.gromov.resvote.model.User;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public interface UserService {

	User getUserByEmail(String email);

}
