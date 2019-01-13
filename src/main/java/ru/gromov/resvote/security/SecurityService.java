package ru.gromov.resvote.security;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
	public UserDetails getLoggedUserDetails() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails);
		}
		if (principal instanceof org.springframework.security.core.userdetails.User) {
			return ((org.springframework.security.core.userdetails.User) principal);
		}
		return null;
	}
}
