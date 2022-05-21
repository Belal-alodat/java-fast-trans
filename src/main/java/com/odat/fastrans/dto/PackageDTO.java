package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Package;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageDTO {
	public PackageDTO(Package pakage) {
		
		this.productName=new ProductDTO(pakage.getProduct());
		this.dimension=new DimensionDTO(pakage.getDimension());
		this.price=pakage.getPrice();
		this.weight=pakage.getWeight();
		this.pieces=pakage.getPieces();
	}
	private ProductDTO productName;
	private DimensionDTO dimension;
	private double price;
	private double weight;
	private int pieces;
}
