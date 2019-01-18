package ru.gromov.resvote.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.to.UserTo;
import ru.gromov.resvote.util.UserUtil;

import static java.util.Objects.requireNonNull;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

	private final UserTo userTo;

	public AuthorizedUser(User user) {
		super(user.getEmail(), user.getPassword(), user.isEnabled(),
				true, true, true, user.getRoles());
		this.userTo = UserUtil.getTo(user);
	}

	private static AuthorizedUser safeGet() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info("GEt USerDetails logged user auth: {}", auth);
		if (auth == null) {
			return null;
		}
		Object principal = auth.getPrincipal();
		return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
	}

	public static AuthorizedUser get() {
		AuthorizedUser user = safeGet();
		log.info("GEt  user auth: {}", user);
		requireNonNull(user, "No authorized user found");
		return user;
	}

	public static long id() {
		return get().userTo.getId();
	}

	public UserTo getUserTo() {
		return userTo;
	}
}
