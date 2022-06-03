package com.odat.fastrans.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.DriverPikedShipment;

public interface DriverPikedPackageRepo extends  JpaRepository<DriverPikedShipment, Long>{

}
