package com.odat.fastrans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
public class DriverPikedShipment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	private Shipment shipment;
	@OneToOne
	private ShipmentStatus shipmentStatus;
	@ManyToOne
	private Driver driver;
	
	 
	public DriverPikedShipment(Shipment shipment, Driver driver, ShipmentStatus shipmentStatus) {
		super();
		this.shipment = shipment;
		this.driver = driver;
		this.shipmentStatus = shipmentStatus;
	}
		
}
