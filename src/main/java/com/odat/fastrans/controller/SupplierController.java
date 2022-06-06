package com.odat.fastrans.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.odat.fastrans.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.odat.fastrans.dto.AddressDTO;
import com.odat.fastrans.dto.PackageDTO;
import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.entity.Address;
import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.service.SupplierService;
import com.odat.fastrans.entity.Package;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/suppliers")
@AllArgsConstructor
@CrossOrigin
public class SupplierController {
	
	final private SupplierService supplierService;
	final private ShipmentService shipmentService;
	
	

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
	public ResponseEntity<List<ShipmentDTO>> getShipments(@RequestParam(name="status",required = false) String status) {

		List<Shipment>  shipments= supplierService.getShipments(status);

		List<ShipmentDTO> shipmentsDto = shipments.stream().map(
				s -> new ShipmentDTO(s)
		).collect(Collectors.toList());

		return  new ResponseEntity<List<ShipmentDTO>>(shipmentsDto,HttpStatus.OK);
	}


	public ResponseEntity<List<ShipmentDTO>> XgetShipments(@RequestParam(name="status",required = false) long status) {

		List<Shipment>  shipments= supplierService.getShipments(status);

		List<ShipmentDTO> shipmentsDto = shipments.stream().map(
				s -> new ShipmentDTO(s)
		).collect(Collectors.toList());

		return  new ResponseEntity<List<ShipmentDTO>>(shipmentsDto,HttpStatus.OK);
	}

	@PatchMapping ("/shipments/{shipmentId}/status/{shipmentStatusId}")
	public ResponseEntity<Void> updateShipmentsStatus(@PathVariable(name ="shipmentId" ) long shipmentId,@PathVariable(name ="shipmentStatusId" ) long shipmentStatusId) {
		shipmentService.updateShipmentsStatus(shipmentId,shipmentStatusId);
		return  new ResponseEntity<Void>(HttpStatus.OK);
	}
}
