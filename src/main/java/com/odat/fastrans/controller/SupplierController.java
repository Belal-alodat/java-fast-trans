package com.odat.fastrans.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.odat.fastrans.dto.AddressDTO;
import com.odat.fastrans.dto.CityDTO;
import com.odat.fastrans.dto.LoginCredentialsDTO;
import com.odat.fastrans.dto.PackageDTO;
import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.dto.SupplierDTO;
import com.odat.fastrans.dto.TownDTO;
import com.odat.fastrans.dto.VillageDTO;
import com.odat.fastrans.entity.Address;
import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.entity.Supplier;
import com.odat.fastrans.service.SupplierService;
import com.odat.fastrans.entity.Package;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/suppliers")
@AllArgsConstructor
@CrossOrigin
public class SupplierController {
	
	final private SupplierService supplierService;

	
	
	@GetMapping("/register")
	public SupplierDTO getSupplierDTO() {
		
		LoginCredentialsDTO loginCredentials = new LoginCredentialsDTO();
		SupplierDTO s = new SupplierDTO();
		s.setCredentials(loginCredentials);
		AddressDTO t = new AddressDTO();
		t.setCity(new CityDTO());
		t.setTown(new TownDTO());
		t.setVillage(new VillageDTO());
		t.setMobile("0569960717");

		return s;
	}
	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDTO>> getAddressBySupplierAndIsFavourite (@RequestParam(name="type",required = false) String type   ){
	  boolean isFromAddress = false;
		if("from".equalsIgnoreCase(type)) {
			isFromAddress = true;
		}
		List<Address> addresses = supplierService.getAddressBySupplierAndFromAddress (isFromAddress );
		
		List<AddressDTO> addressesDto = addresses.stream().map(
		        address -> new AddressDTO(address)
		).collect(Collectors.toList());
		
		return  new ResponseEntity<List<AddressDTO>>(addressesDto,HttpStatus.OK);

	}
	
	@GetMapping("/packages")
	public ResponseEntity<List<PackageDTO>> getPackagesBySupplier (  ){
	   
		List<Package> packages = supplierService.getPackagesBySupplier( );
		
		List<PackageDTO> packagesDto = packages.stream().map(
				(Package pakage) -> new PackageDTO(pakage)
		).collect(Collectors.toList());
		
		return  new ResponseEntity<List<PackageDTO>>(packagesDto,HttpStatus.OK);

	}
	
	
	@PostMapping("/addresses")
	public ResponseEntity<Void> saveAddresses(@RequestBody AddressDTO addressDTO) {
		supplierService.saveAddresses(addressDTO);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PostMapping("/packages")
	public ResponseEntity<Void> savePackage(@RequestBody PackageDTO packageDTO) {
		supplierService.savePackage(packageDTO);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PostMapping("/shipments")
	public ResponseEntity<Void> saveShipments(@RequestBody ShipmentDTO shipmentDTO) {
		supplierService.saveShipments(shipmentDTO);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	@GetMapping("/shipments")
	public ResponseEntity<List<ShipmentDTO>> getShipments() {
		
		List<Shipment> shipments= supplierService.getShipments();
		/*
		 * for(Shipment shipment:shipments) { ShipmentDTO shipmentDTO = new
		 * ShipmentDTO(shipment);
		 * 
		 * }
		 */
		List<ShipmentDTO> shipmentsDto = shipments.stream().map(
		        s -> new ShipmentDTO(s)
		).collect(Collectors.toList());
		
		return  new ResponseEntity<List<ShipmentDTO>>(shipmentsDto,HttpStatus.OK);
	}

}
