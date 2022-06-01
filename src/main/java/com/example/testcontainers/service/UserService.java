package com.example.testcontainers.service;

import com.example.testcontainers.persistence.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
	User add(User user);
	Optional<User> get(int id);
	Optional<User> get(String username);

	List<User> getAll();
}
