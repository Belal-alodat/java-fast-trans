package com.odat.fastrans.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long>{
	public Optional<Supplier> findByAccount(Account accouint);

}
