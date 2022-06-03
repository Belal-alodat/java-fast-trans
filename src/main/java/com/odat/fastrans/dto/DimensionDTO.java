package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Dimension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DimensionDTO {
	public DimensionDTO(Dimension dimension) {
		this.id=dimension.getId();
		this.name =dimension.getName();
		this.description = dimension.getDescription();
	}
	private long id;
	private String name;
	private String description; 
	
}
