package ru.gromov.resvote.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.gromov.resvote.model.User;
import ru.gromov.resvote.repository.UserRepository;
import ru.gromov.resvote.util.exception.UserNotFoundException;

import java.util.List;

import static ru.gromov.resvote.util.UserUtil.*;

/*
 *   Created by Gromov Vitaly, 2019   e-mail: mr.gromov.vitaly@gmail.com
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(
						String.format("User with name %s, not found!", email)
				));
		return getUserDetailsFromUser(user);
	}

	@Secured("ROLE_ADMIN")
	@Transactional(readOnly = true)
	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public User create(final User user) {
		Assert.notNull(user, "user must not be null");
		if (user.getPassword() != null)	user.setPassword(
				bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	@Secured("ROLE_ADMIN")
	@Transactional(readOnly = true)
	@Override
	public User getById(final long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(
						String.format("User with id %s not found", id)));
	}

	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void update(final User user) {
		Assert.notNull(user, "user must not be null");
		User oldUser = getById(user.getId());
		oldUser.setName(user.getName());
		oldUser.setEmail(user.getEmail());
		oldUser.setPassword(user.getPassword());
		userRepository.save(oldUser);
	}

	@Secured("ROLE_ADMIN")
	@Transactional
	@Override
	public void delete(final long id) {
		userRepository.deleteById(id);
	}


}
