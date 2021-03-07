package com.luv2code.RadisAppWithBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.RadisAppWithBoot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
