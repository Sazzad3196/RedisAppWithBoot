package com.luv2code.RadisAppWithBoot.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.RadisAppWithBoot.entity.Customer;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public List<Customer> getAllCustomer() {
		
		Query query = entityManager.createQuery("from Customer", Customer.class);
		List<Customer> CustomerList = query.getResultList();
		return CustomerList;
		
	}

	@Override
	@Transactional
	public Customer updateCustomer(Customer tempCustomer) {
		
		entityManager.merge(tempCustomer);
		Customer customer = entityManager.find(Customer.class, tempCustomer.getId());
		return customer;
		
	}

	@Override
	@Transactional
	public Customer getCustomer(int id) {
		
		Customer customer = entityManager.find(Customer.class, id);
		return customer;
		
	}

	@Override
	@Transactional
	public Customer deleteCustomer(int id) {
		Customer customer = entityManager.find(Customer.class, id);
		if(customer != null) {
			entityManager.remove(customer);
			return customer;
		}
		
		return null;
	}

}
