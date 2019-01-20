package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;


/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@Service
@CacheConfig(cacheNames = {"user"})
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;

	// Use cache for users to prevent invoke DB for each request, because we use stateless REST
	@Cacheable("user")
	public User getUserByEmail(final String email) {
		log.debug("load user from repository by email: {}", email);
		return userRepository.findByEmail(email.toLowerCase())
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("User with name %s, not found!", email)
				));
	}
}
