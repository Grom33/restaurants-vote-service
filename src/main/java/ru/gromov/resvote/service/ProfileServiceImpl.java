package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.security.SecurityService;
import ru.gromov.resvote.util.exception.UserNotFoundException;

import java.util.List;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final SecurityService securityService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Override
	public User getLoggedUser() {
		log.info("Get logged user");
		return securityService.getLoggedUser();
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Override
	public void updateLoggedUser(User user) {
		log.info("Update logged user");
		User loggedUser = securityService.getLoggedUser();
		if (loggedUser.getId().equals(user.getId())) {
			loggedUser.setName(user.getName());
			loggedUser.setPassword(user.getPassword());
			userRepository.save(loggedUser);
		}
	}

	@Secured("ROLE_ADMIN")
	@Transactional(readOnly = true)
	@Override
	public List<User> getAll() {
		log.info("Get list of users");
		return userRepository.findAll();
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public User create(final User user) {
		log.info("Create user: {}", user);
		Assert.notNull(user, "user must not be null");
		if (user.getPassword() != null) user.setPassword(
				bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Secured("ROLE_ADMIN")
	@Transactional(readOnly = true)
	@Override
	public User getById(final long id) {
		log.info("Get user by id: {}", id);
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(
						String.format("User with id %s not found", id)));
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void update(final User user) {
		log.info("Update user: {}", user);
		Assert.notNull(user, "user must not be null");
		User oldUser = getById(user.getId());
		oldUser.setName(user.getName());
		oldUser.setEmail(user.getEmail());
		oldUser.setPassword(user.getPassword());
		userRepository.save(oldUser);
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void delete(final long id) {
		log.info("Delete user by id: {}", id);
		userRepository.deleteById(id);
	}

}
