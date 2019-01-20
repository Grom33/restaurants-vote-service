package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.Role;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.to.UserTo;
import ru.gromov.resvote.util.exception.AlreadyExistException;
import ru.gromov.resvote.util.exception.NotFoundException;

import java.util.HashSet;
import java.util.Set;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */


@Slf4j
@CacheConfig(cacheNames = {"user"})
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@CacheEvict(value = "user", allEntries = true)
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Transactional
	@Override
	public User updateLoggedUser(final User user, final UserTo loggedUser) {
		log.debug("Update logged user");
		if (!((Long) loggedUser.getId()).equals(user.getId())) {
			log.warn("User {}, try edit another user profile: {}", loggedUser, user);
			throw new IllegalArgumentException("This action is prohibited!");
		}
		User updatedUser = userRepository.findById(loggedUser.getId())
				.orElseThrow(() -> new NotFoundException(
						String.format("User with id %s, not found!", loggedUser.getId())
				));
		updatedUser.setName(user.getName());
		updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(updatedUser);
	}

	@CacheEvict(value = "user", allEntries = true)
	@Transactional
	@Override
	public User userRegistration(final User user) {
		log.debug("New user registration: {}", user);
		Assert.notNull(user, "user must not be null");
		checkAlreadyExist(user);
		Set<Role> roleList = new HashSet<>();
		roleList.add(Role.ROLE_USER);
		user.setRoles(roleList);
		return userRepository.save(user);
	}

	private void checkAlreadyExist(final User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent())
			throw new AlreadyExistException(
					String.format("User with email: %s already exist!", user.getEmail()));
	}
}
