package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	public ProductDTO(Product product) {
		this.id = product.getId();
		this.name= product.getName();
		this.description = product.getDescription();
		 
	}
	private long id;
	private String description;
	private String name; 
}
