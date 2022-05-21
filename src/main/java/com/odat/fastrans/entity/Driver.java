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
	private String fullName;
	private String mobile;
	@OneToOne
	private Account account;
	@OneToMany(
		    mappedBy = "driver"
		    
		)
	private List<DriverPikedShipment> pickedPackages;
	@OneToMany(
		)
	private List<DriverDelveryShipment> delveryPackages;
	 
 
	public Driver(String fullName, String mobile, Account account, List<DriverPikedShipment> pickedPackages,
			List<DriverDelveryShipment> delveryPackages) {
		super();
		this.fullName = fullName;
		this.mobile = mobile;
		this.account = account;
		this.pickedPackages = pickedPackages;
		this.delveryPackages = delveryPackages;
	}
	
}
