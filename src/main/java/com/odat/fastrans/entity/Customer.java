package com.odat.fastrans.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@OneToOne
	private Account account; 
	@OneToMany(
		    mappedBy = "customer",
		    cascade = CascadeType.ALL,
		    orphanRemoval = true
		)
	@JsonIgnore
	private List<Shipment> shipments;
	public Customer(Account account) {
		super();
		this.account = account;
		 
	}
}
