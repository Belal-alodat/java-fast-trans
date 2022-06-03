package com.odat.fastrans.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.entity.ShipmentStatus;
import com.odat.fastrans.entity.Supplier;

public interface ShipmentRepo  extends JpaRepository<Shipment, Long>{
	
	public Optional<List<Shipment>> findAllBySupplier(Supplier supplier);
	public Optional<List<Shipment>> findAllByShipmentStatus(ShipmentStatus shipmentStatus);
	 
}
