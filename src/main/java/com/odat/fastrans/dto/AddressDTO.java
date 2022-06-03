package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	
	public AddressDTO(Address address) {
		
		this.id =address.getId();
		 this.city = new CityDTO(address.getCity());
		  this.town = new TownDTO(address.getTown());
		  this.village = new VillageDTO(address.getVillage());;
		  this.mobile =address.getMobile();
		  this.street=address.getStreet();
		  this.buildingNumber=address.getBuildingNumber();
		  this.fullName=address.getFullName();
		  this.latitude=address.getLatitude();
		  this.longitude=address.getLongitude();
		 // this.favourite=address.isFavourite();
		  this.fromAddress=address.isFromAddress();
	}
	private long id =-1;
	private CityDTO city;
	private TownDTO town;
	private VillageDTO village;
	private String mobile;
	
	
	private String street;
	private int buildingNumber;
	private String fullName;
	private double latitude, longitude;
	//private boolean favourite;
	private boolean fromAddress;
 
}
