package com.luv2code.RadisAppWithBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.RadisAppWithBoot.entity.User;
import com.luv2code.RadisAppWithBoot.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Cacheable(value = "users")
	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") int id) {
		LOG.info("Getting user with ID {}", id);
		return userRepository.getOne(id);
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public User updatePersonByID(@RequestBody User user) {
	  userRepository.save(user);
	  return user;
	}
}
