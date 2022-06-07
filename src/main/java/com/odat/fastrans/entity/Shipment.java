package com.odat.fastrans.entity;

import java.sql.Time;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Shipment {
	
	public Shipment(Customer customer, Address fromAddress, Address toAddress, Date pickupDate,
					Time pickupTime, Package pakage, ShipmentStatus shipmentStatus) {
		this.customer = customer;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.pickupDate = pickupDate;
		this.pickupTime = pickupTime;
		this.pakage = pakage;
		this.shipmentStatus = shipmentStatus; 

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	private Address fromAddress;
	@OneToOne
	private Address toAddress;
	@OneToOne
	private Package pakage;
	private Date pickupDate;
	private Time pickupTime;
	@OneToOne
	private ShipmentStatus shipmentStatus;
	@ManyToOne
	@JsonIgnore
	private Customer customer;

}
