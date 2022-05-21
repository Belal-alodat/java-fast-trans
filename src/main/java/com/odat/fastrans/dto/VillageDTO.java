package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Village;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VillageDTO {
	public VillageDTO(Village village) {
		  this.id = village.getId();
		  this.name = village.getName();
	}
	private long id;
	private String name;
}
