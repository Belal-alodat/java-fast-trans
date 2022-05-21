package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Town;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TownDTO {
	public TownDTO(Town town) {
		this.id =town.getId();
		this.name=town.getName();
	}
	private long id;
	private String name;
}
