package com.odat.fastrans.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
public class Driver {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	
	
	@OneToOne
	private Account account;
	@OneToMany(
		    mappedBy = "driver"
		    
		)
	private List<DriverShipment> pickedPackages;

	public Driver( Account account, List<DriverShipment> pickedPackages ) {
		super();	
		this.account = account;
		this.pickedPackages = pickedPackages;

	}
	public Driver( Account account) {
		super();	
		this.account = account;
		 
	}
}
