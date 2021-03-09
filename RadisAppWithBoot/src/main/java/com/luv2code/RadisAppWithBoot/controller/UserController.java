package com.luv2code.RadisAppWithBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.RadisAppWithBoot.Logger.LoggerClass;
import com.luv2code.RadisAppWithBoot.entity.User;
import com.luv2code.RadisAppWithBoot.repository.UserRepository;

import java.util.List;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	LoggerClass loggerClass = new LoggerClass();

	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("getAllUser")
	@Cacheable(value = "users" , key = "1")
	public List<User> getAllUser() {
		loggerClass.getLogger().log(Level.INFO , "Get all users");
		return userRepository.findAll();
	}
	
	@GetMapping("getUser/{id}")
	@Cacheable(value = "user", key = "#id", unless = "#result.followers < 1500")
	public User getUser(@PathVariable("id") int id) {
		loggerClass.getLogger().log(Level.INFO, "Getting user with ID " + id);
		return userRepository.getOne(id);
	}
	
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CachePut(value = "user", key = "#user.id")
	public User updatePersonByID(@RequestBody User user) {
		loggerClass.getLogger().log(Level.INFO, "User Update Successfully for User ID " + user.getId());
	    // LOG.info("User Update Successfully for User Id {}" , user.getId());	
	    userRepository.save(user);
	    return user;
	}
	
	@CacheEvict(value = "users")
	@DeleteMapping("deleteUser/{id}")
	public User deleteUser(@PathVariable int id) {
		List<User> userList = userRepository.findAll();
		
		for(User user : userList) {
			if(user.getId() == id) {
				userRepository.deleteById(id);
				loggerClass.getLogger().log(Level.INFO, "Successfully delete from Redis and DB");
				// LOG.info("Successfully delete from Redis and DB");
				return user;
			}
		}
		
		// LOG.info("No user found for id {}" , id);
		loggerClass.getLogger().log(Level.INFO, "No user found for ID " + id);
		
		return new User();
	}
	
}
