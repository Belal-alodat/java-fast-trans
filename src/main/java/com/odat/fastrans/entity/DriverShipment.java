package com.odat.fastrans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
@Table( name ="driver_shipment " , uniqueConstraints={
	       @UniqueConstraint(name="const_shipment_id", columnNames={"shipment_id","shipment_status_id"})
	   })
public class DriverShipment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn(name="shipment_id")
	private Shipment shipment;
	@OneToOne
	@JoinColumn(name="shipment_status_id")
	private ShipmentStatus shipmentStatus;
	@ManyToOne
	private Driver driver;
	
	 
	public DriverShipment(Shipment shipment, Driver driver, ShipmentStatus shipmentStatus) {
		super();
		this.shipment = shipment;
		this.driver = driver;
		this.shipmentStatus = shipmentStatus;
	}
		
}
