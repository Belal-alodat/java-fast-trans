package com.odat.fastrans.repo;

import com.odat.fastrans.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Driver;

import java.util.Optional;

public interface DriverRepo  extends JpaRepository<Driver, Long>{
    public Optional<Driver> findByAccount(Account accouint);
}
