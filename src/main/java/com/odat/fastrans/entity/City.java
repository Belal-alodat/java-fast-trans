package com.odat.fastrans.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private String description;
	@OneToMany(
		    mappedBy = "city"
		)
	
	private List<Town> towns;
	
	
	 
	public City(String name, String description, List<Town> towns) {
		super();
		this.name = name;
		this.description = description;
		this.towns = towns;
	}
	public City(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
}
