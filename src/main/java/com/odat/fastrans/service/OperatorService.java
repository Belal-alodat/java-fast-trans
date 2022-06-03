package com.odat.fastrans.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odat.fastrans.dto.AddressDTO;
import com.odat.fastrans.dto.CityDTO;
import com.odat.fastrans.dto.DimensionDTO;
import com.odat.fastrans.dto.PackageDTO;
import com.odat.fastrans.dto.ProductDTO;
import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.dto.TownDTO;
import com.odat.fastrans.dto.VillageDTO;
import com.odat.fastrans.entity.Driver;
import com.odat.fastrans.entity.DriverShipment;
import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.entity.ShipmentStatus;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.AddressRepo;
import com.odat.fastrans.repo.CityRepo;
import com.odat.fastrans.repo.DimensionRepo;
import com.odat.fastrans.repo.DriverShipmentRepo;
import com.odat.fastrans.repo.DriverRepo;
import com.odat.fastrans.repo.PackageRepo;
import com.odat.fastrans.repo.ProductRepo;
import com.odat.fastrans.repo.ShipmentRepo;
import com.odat.fastrans.repo.ShipmentStatusRepo;
import com.odat.fastrans.repo.SupplierRepo;
import com.odat.fastrans.repo.TownRepo;
import com.odat.fastrans.repo.VillageRepo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OperatorService {

	final private AccountRepo accountRepo;
	final private SupplierRepo supplierRepo;
	final private ProductRepo productRepo;
	final private DimensionRepo dimensionRepo;
	final private PackageRepo packageRepo;
	final private CityRepo cityRepo;
	final private TownRepo townRepo;
	final private VillageRepo villageRepo;
	final private ShipmentRepo shipmentRepo;
	final private AddressRepo addressRepo;
	final private PasswordEncoder passwordEncoder;
	final private ShipmentStatusRepo shipmentStatusRepo;
	final private DriverRepo driverRepo ;
	final private DriverShipmentRepo driverShipmentRepo; 
	 
 
	public  List<Driver> getDrivers() {		 
		return  driverRepo.findAll();	   
	}


	public void assignShipmentToDriver(long driverId, long shipmentId,long shipmentStatusId) {
	
		
		Optional<Driver> optionalDriver = driverRepo.findById(driverId);
		
		if (optionalDriver.isEmpty()) {
			throw new NoSuchElementException("Driver not found");
		}
		
		Driver driver = optionalDriver.get();
		
		Optional<ShipmentStatus> optionalShipmentStatus = shipmentStatusRepo.findById(shipmentStatusId);
		
		if (optionalShipmentStatus.isEmpty()) {
			throw new NoSuchElementException("ShipmentStatus not found");
		}
		
		ShipmentStatus shipmentStatus = optionalShipmentStatus.get();
		
		
		Optional<Shipment> optionalShipment = shipmentRepo.findById(shipmentId);

		if (optionalShipment.isEmpty()) {
			throw new NoSuchElementException("Shipment not found");
		}
		
		Shipment shipment = optionalShipment.get();
		shipment.setShipmentStatus(shipmentStatus);
		
		DriverShipment driverShipment = new DriverShipment(shipment , driver, shipmentStatus) ;
		driverShipmentRepo.saveAndFlush(driverShipment);
		 
	} 
	
	
	
	
}