package com.luv2code.RadisAppWithBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.luv2code.RadisAppWithBoot.entity.Customer;
import com.luv2code.RadisAppWithBoot.entity.User;
import com.luv2code.RadisAppWithBoot.repository.UserRepository;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class RadisAppWithBootApplication {
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Bean
	RedisTemplate<String, Map<Integer, Customer>> redisTemplate() {
		RedisTemplate<String, Map<Integer, Customer>> redisTemplate = new RedisTemplate<String, Map<Integer,Customer>>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RadisAppWithBootApplication.class, args);
	}

}

 /* 
 @SpringBootApplication
 @EnableCaching
 public class RadisAppWithBootApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(RadisAppWithBootApplication.class, args);
	}
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private final UserRepository userRepository;
	
	@Autowired
	public RadisAppWithBootApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	

	@Override
	public void run(String... args) throws Exception {
		
		LOG.info("Saving users. Current user count is {}.", userRepository.count());
		User sazzad = new User("Sazzad", 1000);
		User pritam = new User("Pritam", 10);
		User joy = new User("Joy", 30000);
		
		userRepository.save(sazzad);
		userRepository.save(pritam);
		userRepository.save(joy);
		
		LOG.info("Done All Saving! {}", userRepository.findAll());
	} 

} */
