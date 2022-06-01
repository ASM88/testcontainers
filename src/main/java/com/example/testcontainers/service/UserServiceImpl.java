package com.example.testcontainers.service;

import com.example.testcontainers.persistence.model.User;
import com.example.testcontainers.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository users;

	public UserServiceImpl(UserRepository users) {
		this.users = users;
	}

	@Override public User add(User user) {
		return users.save(user);
	}

	@Override public Optional<User> get(int id) {
		return users.findById(id);
	}

	@Override public Optional<User> get(String username) {
		return users.findByUsername(username);
	}

	@Override public List<User> getAll() {
		return users.findAll();
	}
}
