package com.odat.fastrans.service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.odat.fastrans.entity.*;
import com.odat.fastrans.repo.*;
import org.springframework.security.core.context.SecurityContextHolder;
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

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ShipmentService {

	final private AccountRepo accountRepo;
	final private SupplierRepo supplierRepo;
	final private DriverRepo driverRepo;
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


	public void updateShipmentsStatus(long shipmentId, long shipmentStatusId) {

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
		shipmentRepo.saveAndFlush(shipment);


	}

	public  List<Shipment> getShipments(long status) {

		Optional<ShipmentStatus> shipmentStatusOptional = shipmentStatusRepo.findById(status);
		if (shipmentStatusOptional.isEmpty()) {
			throw new NoSuchElementException("shipmentStatus not found");
		}
		ShipmentStatus shipmentStatus = shipmentStatusOptional.get();

		Optional<List<Shipment>> shipmentsOptional = shipmentRepo.findAllByShipmentStatus(shipmentStatus);
		List<Shipment> shipments= shipmentsOptional.get();

		return shipments;
	}


}