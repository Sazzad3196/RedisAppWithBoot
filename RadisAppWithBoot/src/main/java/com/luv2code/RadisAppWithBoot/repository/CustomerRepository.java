package com.luv2code.RadisAppWithBoot.repository;

import java.util.List;

import com.luv2code.RadisAppWithBoot.entity.Customer;

public interface CustomerRepository {
	public List<Customer> getAllCustomer();
	public Customer updateCustomer(Customer customer);
	public Customer getCustomer(int id);
	public Customer deleteCustomer(int id);
}
