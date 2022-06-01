package com.example.testcontainers.api;

import com.example.testcontainers.persistence.model.User;
import com.example.testcontainers.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	private final UserService users;

	public UserController(UserService users) {
		this.users = users;
	}

	@PostMapping
	public ResponseEntity<User> add(@RequestBody User user) {
		LOG.debug("Add User={}", user);
		User created = users.add(user);
		return ResponseEntity.created(URI.create(String.format("/users/%s", created.getId()))).body(created);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		return ResponseEntity.ok(users.getAll());
	}

	@GetMapping(path = "{id}")
	public ResponseEntity<User> get(@PathVariable Integer id) {
		return ResponseEntity.of(users.get(id));
	}
}
