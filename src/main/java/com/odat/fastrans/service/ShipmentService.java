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
import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.entity.ShipmentStatus;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.AddressRepo;
import com.odat.fastrans.repo.CityRepo;
import com.odat.fastrans.repo.DimensionRepo;
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
public class ShipmentService {

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
	 
	
	public  ShipmentDTO getShipment() {
		ShipmentDTO shipmentDTO = new ShipmentDTO();
		shipmentDTO.setPickupDate(Date.valueOf("2022-12-12"));
		shipmentDTO.setPickupTime(Time.valueOf("12:12:12"));
		int buildingNumber=1;
		double longitude=1.1;
		double latitude=1.1;
		AddressDTO add =	new AddressDTO(1, new CityDTO(), new TownDTO(-1,""),
				new VillageDTO(-1,""), 
				"mobile","street",buildingNumber,
				"fullName", latitude, longitude, /* false, */ false);
		shipmentDTO.setFromAddress(add);
		shipmentDTO.setToAddress(add);
		PackageDTO packagedetails = new PackageDTO();
		packagedetails.setDimension(new DimensionDTO());
		packagedetails.setProduct(new ProductDTO());
		shipmentDTO.setPackageDetails(packagedetails );
		return shipmentDTO;
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