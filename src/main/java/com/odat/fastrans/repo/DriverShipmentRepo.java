package com.odat.fastrans.repo;

import com.odat.fastrans.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverShipmentRepo extends  JpaRepository<DriverShipment, Long>{
    public Optional<DriverShipment> findByDriverAndShipmentAndShipmentStatus(Driver driver,
                                                                                 Shipment shipment,
                                                                                 ShipmentStatus shipmentStatus);
}
