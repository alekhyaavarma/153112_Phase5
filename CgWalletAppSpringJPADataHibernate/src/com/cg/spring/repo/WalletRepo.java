package com.cg.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.spring.beans.Customer;
import com.cg.spring.exception.InvalidInputException;

public interface WalletRepo extends JpaRepository<Customer, String>{

	
}
