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
import ru.gromov.resvote.security.AuthorizedUser;
import ru.gromov.resvote.to.UserTo;
import ru.gromov.resvote.util.exception.AlreadyExistException;
import ru.gromov.resvote.util.exception.NotFoundException;

import javax.validation.constraints.Positive;
import java.util.*;

import static ru.gromov.resvote.util.ValidationUtil.checkNotFoundWithId;

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

	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Override
	public UserTo getLoggedUser() {
		log.info("Get logged user");
		return AuthorizedUser.get().getUserTo();
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	@Transactional
	@Override
	public void updateLoggedUser(final User user) {
		log.info("Update logged user");
		final UserTo loggedUser = AuthorizedUser.get().getUserTo();
		if (!((Long) loggedUser.getId()).equals(user.getId())) {
			log.warn("User {}, try edit another user profile: {}", loggedUser, user);
			throw new IllegalArgumentException("This action is prohibited!");
		}
		User updatedUser = getById(loggedUser.getId());
		updatedUser.setName(user.getName());
		updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(updatedUser);
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
		checkAlreadyExist(user);
		if (user.getPassword() != null) user.setPassword(
				bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Secured("ROLE_ADMIN")
	@Transactional(readOnly = true)
	@Override
	public User getById(@Positive final long id) {
		log.info("Get user by id: {}", id);
		return userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(
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
		oldUser.setPassword(user.getPassword());
		userRepository.save(oldUser);
	}

	@CacheEvict(value = "user", allEntries = true)
	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void delete(@Positive final long id) {
		log.info("Delete user by id: {}", id);
		checkNotFoundWithId(userRepository.delete(id) != 0, id);
	}

	@CacheEvict(value = "user", allEntries = true)
	@Transactional
	@Override
	public User userRegistration(final User user) {
		log.info("New user registration: {}", user);
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
