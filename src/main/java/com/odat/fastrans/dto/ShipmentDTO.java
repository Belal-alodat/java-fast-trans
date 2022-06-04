package com.odat.fastrans.dto;

import java.sql.Date;
import java.sql.Time;

import com.odat.fastrans.entity.Shipment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor 
public class ShipmentDTO {
	
	public ShipmentDTO(Shipment shipment) {
		this.fromAddress = new AddressDTO(shipment.getFromAddress());
		this.toAddress = new AddressDTO(shipment.getToAddress());
		this.packageDetails = new PackageDTO(shipment.getPakage());
		this.pickupDate = shipment.getPickupDate();
		this.pickupTime = shipment.getPickupTime();
		this.id = shipment.getId();
		this.status = shipment.getShipmentStatus().getId();
		
	}
	private long id;
	private AddressDTO fromAddress;
	private AddressDTO toAddress;
    private PackageDTO packageDetails;
    private Date pickupDate;
    private Time pickupTime;
	private long status;
    
}
