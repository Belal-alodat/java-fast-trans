package com.odat.fastrans.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
 
@NoArgsConstructor
@Data
@Entity
public class Town {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	private String name;
	private String description;
	
	@OneToMany(
		    mappedBy = "town",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	
	private List<Village> villages;
	
	@ManyToOne
	@JsonIgnore
	private City city;
	
	
	
	 
	
	
	public Town(String name, String description, List<Village> villages, City city) {
		super();
		this.name = name;
		this.description = description;
		this.villages = villages;
		this.city = city;
	}
	public Town(  String name, String description, City city) {
		super();
		this.name = name;
		this.description = description;
		this.city = city;
	}
	 
	public static void assignCitieٌs(City city) {
		List<Town>  towns = city.getTowns();
		 for (Town town: towns) {
			 town.setCity(city);
			 Village.assignTowns(town);
		}		
	}
	
	
	

}
