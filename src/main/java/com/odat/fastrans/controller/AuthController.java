package com.odat.fastrans.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odat.fastrans.dto.LoginCredentialsDTO;
import com.odat.fastrans.dto.SupplierDTO;
import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Supplier;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.SupplierRepo;
import com.odat.fastrans.security.JWTUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired private AccountRepo accountRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private SupplierRepo supplierRepo;

    @PostMapping("/register")
    public Map<String, Object> registerHandler(@RequestBody SupplierDTO supplierDTO){
    	LoginCredentialsDTO credintial = supplierDTO.getCredentials();
        String encodedPass = passwordEncoder.encode(credintial.getPassword());
        Account account = new Account();
        account.setPassword(encodedPass);
        account.setActive(1);
        account.setEmail(credintial.getEmail());
        account.setFullName(credintial.getFullName());
        account.setMobile(credintial.getMobile());
        account = accountRepo.save(account);
        supplierRepo.save(new Supplier(account));
        String token = jwtUtil.generateTokenX(account.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }

    @PostMapping("/login")
    
    public ResponseEntity<Map<String, Object>> loginHandler(@RequestBody LoginCredentialsDTO body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateTokenX(body.getEmail());

            return ResponseEntity.ok(Collections.singletonMap("jwt-token", token));
        }catch (AuthenticationException authExc){
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    public ResponseEntity<String> xxx(@RequestBody LoginCredentialsDTO body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateTokenX(body.getEmail());

            return ResponseEntity.ok(token);
        }catch (AuthenticationException authExc){
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
