package com.odat.fastrans.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{
	public Optional<Customer> findByAccount(Account accouint);

}
