package com.luv2code.RadisAppWithBoot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.RadisAppWithBoot.entity.Customer;
import com.luv2code.RadisAppWithBoot.entity.CustomerIdComparator;
import com.luv2code.RadisAppWithBoot.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")

public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private RedisTemplate<String, Customer> redisTemplate;
	private HashOperations hashOperations;
	
	@PostConstruct
	public void init() {
		
		hashOperations = redisTemplate.opsForHash();
		
	}
	
	
	@GetMapping("/getAllCustomer")
	public List<Customer> getAllCustomer() {
		
		List<Customer> customerList = customerRepository.getAllCustomer();
		
		
		for(Customer customer : customerList) {
			if(customer.getAge() > 20) {
				hashOperations.put("CUSTOMER", customer.getId(), customer);
			}
		}
		
		Map<String , Customer> map = hashOperations.entries("CUSTOMER");
		for(Map.Entry<String, Customer> m : map.entrySet()) {
			System.out.println("Object: " + m.getValue().toString() );
			
		}
		
		return customerList;
		
	}
	
	@PostMapping("/updateCustomer")
	public Customer updateCustomer(@RequestBody Customer customer) {
		
		Customer tempCustomer = customerRepository.updateCustomer(customer);
		if(tempCustomer.getAge() > 20) {
			hashOperations.put("CUSTOMER", customer.getId(), tempCustomer);
			return tempCustomer;
		}
//		else {
//			Map<String , Customer> map = hashOperations.entries("CUSTOMER");
//			for(Map.Entry<String, Customer> m : map.entrySet()) {
//				if (m.getValue().getId() == tempCustomer.getId()) {
//					// System.out.println("Inside Update Method");
//					hashOperations.delete("CUSTOMER" , tempCustomer.getId());
//				}
//				
//			}
//		}
		
		hashOperations.delete("CUSTOMER" , tempCustomer.getId());
		return tempCustomer;
		
	}
	
	@GetMapping("/getCustomer/{id}")
	public Customer getCustomer(@PathVariable int id) {
		
		Customer customer = customerRepository.getCustomer(id);
		if(customer != null) {
			hashOperations.delete("CUSTOMER" , customer.getId());
			return customer;
		}
		
		return null;
		
	}
	
	@GetMapping("/deleteCustomer/{id}")
	public Customer deleteCustomer(@PathVariable int id) {
		
		Customer customer = customerRepository.deleteCustomer(id);
		
		if(customer != null) {
			System.out.println("Inside delete method");
			hashOperations.delete("CUSTOMER" , customer.getId());
			return customer;
		}
		
		return null;
		
	}
	
	@GetMapping("/getAllCustomerFromRedis")
	public List<Customer> getAllCustomerFromRedis() {
		
		List<Customer> customerList = new ArrayList<Customer>();
		Map<String , Customer> map = hashOperations.entries("CUSTOMER");
		for(Map.Entry<String, Customer> m : map.entrySet()) {
			// System.out.println("Name: " + m.getKey() + "value: " + m.getValue().toString() );
			customerList.add(m.getValue());
			
		}
		
		Collections.sort(customerList, new CustomerIdComparator());
		return customerList;
		
	}
}
