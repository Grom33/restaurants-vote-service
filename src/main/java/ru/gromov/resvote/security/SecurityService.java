package ru.gromov.resvote.security;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.util.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class SecurityService {

	@Autowired
	private final UserRepository userRepository;

	private UserDetails getLoggedUserDetails() {
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

	public User getLoggedUser() {
		String userName = getLoggedUserDetails().getUsername();
		return userRepository.findByEmail(userName)
				.orElseThrow(() -> new UserNotFoundException(
						String.format("User with email %s not found", userName)));
	}
}
