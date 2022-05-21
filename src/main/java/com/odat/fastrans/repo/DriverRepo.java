package com.odat.fastrans.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Driver;

public interface DriverRepo  extends JpaRepository<Driver, Long>{

}
