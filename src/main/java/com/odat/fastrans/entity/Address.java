package com.odat.fastrans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.odat.fastrans.dto.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
@NoArgsConstructor
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	private City city;
	@OneToOne
	private Town town;
	@OneToOne
	private Village village;
	
	private String street;
	private int buildingNumber;
	private String mobile;
	private String fullName;
	
	private double latitude, longitude;
	//private boolean favourite;
	private boolean fromAddress;
	@ManyToOne
	private Customer customer;

	public Address(Customer customer,City city, Town town, Village village, String mobile/* ,boolean favourite */,boolean fromAddress) {
		super();
		this.city = city;
		this.town = town;
		this.village = village;
		this.mobile = mobile;
	//	this.favourite = favourite;
		this.customer = customer;
		this.fromAddress=fromAddress;
	}

	public Address(Customer customer,City city, Town town, Village village,AddressDTO addressDTO){
		this.customer = customer;
       this.setCity( city);
		this. town = town;
		this.village = village;
		this.setMobile (addressDTO.getMobile());
		this.setBuildingNumber (addressDTO.getBuildingNumber());
		this.setFullName(addressDTO.getFullName());
		this.setLatitude(addressDTO.getLatitude());
		this.setLongitude(addressDTO.getLongitude());
		this.setStreet(addressDTO.getStreet());
		this.setFromAddress(addressDTO.isFromAddress());
	}

}
