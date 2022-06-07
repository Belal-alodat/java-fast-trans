package com.odat.fastrans.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Address;
import com.odat.fastrans.entity.Customer;

public interface AddressRepo extends  JpaRepository<Address, Long>{
	
	public List<Address> findAllByCustomerAndFromAddress(Customer customer, boolean fromAddress);
		
	
}
