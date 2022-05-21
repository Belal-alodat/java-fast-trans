package com.odat.fastrans.controller;

import java.sql.Date;
import java.sql.Time;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odat.fastrans.dto.AddressDTO;
import com.odat.fastrans.dto.CityDTO;
import com.odat.fastrans.dto.DimensionDTO;
import com.odat.fastrans.dto.LoginCredentialsDTO;
import com.odat.fastrans.dto.PackageDTO;
import com.odat.fastrans.dto.ProductDTO;
import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.dto.SupplierDTO;
import com.odat.fastrans.dto.TownDTO;
import com.odat.fastrans.dto.VillageDTO;
import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Supplier;
import com.odat.fastrans.service.SupplierService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	final private SupplierService supplierService;

	@GetMapping("/info")
	public Account getUserDetails() {
		return supplierService.getAccount();
	}

	@PostMapping("/register")
	public ResponseEntity<Void> registerHandler(@RequestBody SupplierDTO supplierDTO) {
		supplierService.registerHandler(supplierDTO);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	

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

	@GetMapping("/packageDTO")
	public PackageDTO getPackageDTO() {

		PackageDTO x = new PackageDTO();
		x.setProductName(new ProductDTO());
		x.setDimension(new DimensionDTO());
		return x;
	}

	 

	@GetMapping("/supplier")
	public Supplier getSupplier() {
		return supplierService.getSupplier();
	}

	@PostMapping("/shipments")
	public ResponseEntity<Void> saveShipments(@RequestBody ShipmentDTO shipmentDTO) {
		supplierService.saveShipments(shipmentDTO);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
//http://localhost:8080/user/shipments
	@GetMapping("/shipments")
	public  ShipmentDTO getShipment() {
		ShipmentDTO shipmentDTO = supplierService.getShipment();
		return shipmentDTO;
	} 
	
}
