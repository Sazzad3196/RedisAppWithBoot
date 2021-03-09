package com.luv2code.RadisAppWithBoot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.RadisAppWithBoot.entity.Customer;
import com.luv2code.RadisAppWithBoot.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")

public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RedisTemplate<String, Map<Integer, Customer>> redisTemplate = new RedisTemplate<String, Map<Integer,Customer>>();
	private HashOperations hashOperations = redisTemplate.opsForHash();
	
	@GetMapping("/getAllCustomer")
	public List<Customer> getAllCustomer() {
		Map<Integer, Customer> customerMap = new HashMap();
		List<Customer> customerList = customerRepository.getAllCustomer();
		
		
		for(Customer customer : customerList) {
			if(customer.getAge() > 20) {
				customerMap.put(customer.getId(), customer);
			}
		}
		
		hashOperations.putAll("customers", customerMap);
		
		/*
		for(Map.Entry<Integer, Customer> m: customerMap.entrySet()) {
			System.out.println(m.getValue().getId());
		} 
		*/
		return customerList;
	}
	
	@GetMapping("/showAllCustomer")
	public Map<String, Map<Integer, Customer>> showAllUser() {
		Map<Integer, Customer> customerMap = hashOperations.entries("customers");
		for(Map.Entry<Integer, Customer> m: customerMap.entrySet()) {
			System.out.println(m.getValue().getId());
		} 
		return hashOperations.entries("customers");
	}
}
