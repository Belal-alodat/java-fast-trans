package com.odat.fastrans.controller;

import com.odat.fastrans.Constants;
import com.odat.fastrans.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odat.fastrans.security.TokenProvider;
import com.odat.fastrans.dto.LoginCredentialsDTO;
import com.odat.fastrans.dto.CustomerDTO;
import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Driver;
import com.odat.fastrans.entity.Customer;
import com.odat.fastrans.repo.AccountRepo;
import com.odat.fastrans.repo.DriverRepo;
import com.odat.fastrans.repo.CustomerRepo;
import com.odat.fastrans.security.JWTUtil;

@RestController
@RequestMapping(Constants.AUTH_CONTROLLER_BASE_PATH)
@CrossOrigin
public class AuthController {


	@Autowired private AccountRepo accountRepo;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private AuthenticationManager authManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CustomerRepo customerRepo;
    @Autowired private TokenProvider jwtTokenUtil;
	@Autowired private DriverRepo driverRepo;

    @PostMapping(Constants.AUTH_CONTROLLER_REGISTER_PATH)
    public ResponseEntity<Void> registerHandler(@RequestBody CustomerDTO customerDTO){
        Account account = new Account( passwordEncoder, customerDTO);
    	LoginCredentialsDTO credential = customerDTO.getCredentials();
        account = accountRepo.save(account);

        if(Constants.DRIVER_ROLE.equalsIgnoreCase(credential.getRoleName())) {
          driverRepo.save(new Driver(account));
        }else {
        	customerRepo.save(new Customer(account));
        }
        return  new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping(Constants.AUTH_CONTROLLER_LOGIN_PATH)
    
    public ResponseEntity<LoginResponse> loginHandler(@RequestBody LoginCredentialsDTO body){
        try {
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            final Authentication authentication =  authManager.authenticate(authInputToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final LoginResponse loginResponse = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(loginResponse);
        }catch (AuthenticationException authExc){
        	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    
}
