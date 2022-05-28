package com.odat.fastrans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	private boolean favourite;
	private boolean fromAddress;
	@ManyToOne
	private Supplier supplier;
	public Address(City city, Town town, Village village, String mobile,boolean favourite,boolean fromAddress) {
		super();
		this.city = city;
		this.town = town;
		this.village = village;
		this.mobile = mobile;
		this.favourite = favourite;
		this.fromAddress=fromAddress;
	}

}
