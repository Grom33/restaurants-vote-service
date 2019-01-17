package ru.gromov.resvote.security;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.util.exception.UserNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {

	@Autowired
	private final UserRepository userRepository;

	private UserDetails getLoggedUserDetails() {
		log.info("GEt USerDetails logged user");
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

	@Cacheable("user")
	public User getLoggedUser() {
		log.info("GEt logged user  by email");
		String userName = getLoggedUserDetails().getUsername();
		return userRepository.findByEmail(userName)
				.orElseThrow(() -> new UserNotFoundException(
						String.format("User with email %s not found", userName)));
	}
}
