package com.odat.fastrans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
public class Package {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	private Product product;
	@OneToOne
	private Dimension dimension;
	private double price;
	private double weight;
	private int pieces;
	 
	public Package(Product product, Dimension dimension, double totalPrice) {
		super();
		this.product = product;
		this.dimension = dimension;
		this.price = totalPrice;
	}
	 

}
