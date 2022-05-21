package com.odat.fastrans.dto;

import com.odat.fastrans.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	public   AccountDTO(Account account) {
		this.email=account.getEmail();
		this.fullName=account.getFullName();
		this.mobile=account.getMobile();
	}
	private String email;
	private String fullName;
	private String mobile; 
	
}
