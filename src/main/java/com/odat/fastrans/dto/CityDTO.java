package com.odat.fastrans.dto;

import com.odat.fastrans.entity.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {
	public CityDTO(City city) {
		this.id=city.getId();
		this.name =city.getName();
	}
	private long id;
	private String name;
}
