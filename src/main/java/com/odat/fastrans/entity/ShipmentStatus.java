package com.odat.fastrans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
public class ShipmentStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	private String name;
	private String description;
	 
	public ShipmentStatus(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
}
