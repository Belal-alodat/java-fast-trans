package com.odat.fastrans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.odat.fastrans.dto.CustomerDTO;
import com.odat.fastrans.dto.LoginCredentialsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	
	public static Account getDefault() {
		 Account account = new Account("96656","a@a", "password","name", 1,"");
		 account.setId(-1);
		 return account;
	}

	public Account(PasswordEncoder passwordEncoder,CustomerDTO customerDTO){
		LoginCredentialsDTO credential = customerDTO.getCredentials();
		String encodedPass = passwordEncoder.encode(credential.getPassword());
		this.setPassword(encodedPass);
		this.setActive(1);
		this.setEmail(credential.getEmail());
		this.setFullName(credential.getFullName());
		this.setMobile(credential.getMobile());
		this.setRoleName(credential.getRoleName());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String email;
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private int active;
	private String fullName;
	private String mobile; 
	private String roleName;
	
	 
	 
	public Account(String mobile,String email, String password,String fullName, int active,String roleName ) {
		super();
		this.email = email;
		this.password = password;
		this.active = active;
		this.mobile = mobile;
		this.fullName = fullName;
		this.roleName=roleName;
	}



	
}
