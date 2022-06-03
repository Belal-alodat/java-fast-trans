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
	@OneToMany(
			  mappedBy = "driver"
		)
	private List<DriverDelveryShipment> delveryPackages;
	 
 
	public Driver( Account account, List<DriverShipment> pickedPackages,
			List<DriverDelveryShipment> delveryPackages) {
		super();	
		this.account = account;
		this.pickedPackages = pickedPackages;
		this.delveryPackages = delveryPackages;
	}
	public Driver( Account account) {
		super();	
		this.account = account;
		 
	}
}
