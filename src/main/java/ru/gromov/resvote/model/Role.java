package ru.gromov.resvote.model;

import org.springframework.security.core.GrantedAuthority;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

public enum Role implements GrantedAuthority {
	ROLE_USER,
	ROLE_ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}
}
