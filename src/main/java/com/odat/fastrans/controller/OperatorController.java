package com.odat.fastrans.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.odat.fastrans.dto.DriverDTO;
import com.odat.fastrans.dto.ShipmentDTO;
import com.odat.fastrans.entity.Driver;
import com.odat.fastrans.entity.Shipment;
import com.odat.fastrans.service.OperatorService;
import com.odat.fastrans.service.ShipmentService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/operators")
@AllArgsConstructor
@CrossOrigin
public class OperatorController {
	
	final private ShipmentService shipmentService;
	final private OperatorService operatorService;
	
	@GetMapping("/shipments")
	public ResponseEntity<List<ShipmentDTO>> getShipmentxs(@RequestParam(name="status",required = true) long status) {
		 try {
			Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
		List<Shipment> shipments= shipmentService.getShipments(status);
		
		List<ShipmentDTO> shipmentsDto = shipments.stream().map(
		        s -> new ShipmentDTO(s)
		).collect(Collectors.toList());
		
		return  new ResponseEntity<List<ShipmentDTO>>(shipmentsDto,HttpStatus.OK);
	}
	@GetMapping("/drivers")
	public ResponseEntity<List<DriverDTO>> getDrivers() {
		 
		List<Driver> derivers= operatorService.getDrivers();
		 
		List<DriverDTO> deriversDto = derivers.stream().map(
		        d -> new DriverDTO(d)
		).collect(Collectors.toList());
		
		return  new ResponseEntity<List<DriverDTO>>(deriversDto,HttpStatus.OK);
	}
	
	@PostMapping("/drivers/{driverId}/shipment/{shipmentId}/status/{shipmentStatusId}")
	public ResponseEntity<Void> getDrivers(@PathVariable(name ="driverId" ) long driverId,@PathVariable(name ="shipmentId" ) long shipmentId,@PathVariable(name ="shipmentStatusId" ) long shipmentStatusId) {
		 
		  operatorService.assignShipmentToDriver(driverId,shipmentId,shipmentStatusId);
		 
		 
		return  new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
