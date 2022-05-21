package com.odat.fastrans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginCredentialsDTO {
	
	private String email;
    private String password;
    private String fullName;
	private String mobile; 
}
