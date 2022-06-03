package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
	
	private AccountDTO account;
	private long id;
	
	public DriverDTO(Driver driver) {
		this.account = new AccountDTO(driver.getAccount());
		this.id = driver.getId();
	}
	 
}
