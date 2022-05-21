package com.odat.fastrans.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Village {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;	private String name;
	private String description;
	@ManyToOne
	@JsonIgnore
	private Town town;
	public Village(String name, String description, Town town) {
		super();
		this.name = name;
		this.description = description;
		this.town = town;
	}
	public static void assignTowns(Town town) {
		List<Village>  villages = town.getVillages();
		 for (Village v: villages) {
			 v.setTown(town);
		}		
	}
}
