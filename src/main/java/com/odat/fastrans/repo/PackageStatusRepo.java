package com.odat.fastrans.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.ShipmentStatus;

public interface PackageStatusRepo extends JpaRepository<ShipmentStatus, Long>{

}
