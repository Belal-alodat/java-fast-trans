package com.odat.fastrans.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.odat.fastrans.dto.PackageDTO;
import com.odat.fastrans.entity.Account;
import com.odat.fastrans.entity.Package;
import com.odat.fastrans.repo.AccountRepo;

@Component
public class MyUserDetailsService implements UserDetailsService {

      
   final private AccountRepo accountRepo;
   
   public MyUserDetailsService(AccountRepo accountRepo) {
	   this.accountRepo=accountRepo;
   }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepo.findByEmail(email);
        if(accountOptional.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with email = " + email);
        Account account = accountOptional.get();
        List<SimpleGrantedAuthority> simpleGrantedAuthoritys = new ArrayList<SimpleGrantedAuthority>(0);
        
        if (account.getRoleName() != null && account.getRoleName().length() > 0) {
        	  simpleGrantedAuthoritys = Arrays.asList(account.getRoleName().split(",")).stream().map((String role) -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
        }
        return new org.springframework.security.core.userdetails.User(
                email,
                account.getPassword(),
                simpleGrantedAuthoritys);
    }
    public static void main(String[] args) {
		String a []= "u1".split(",");
		System.out.println(a.length);
		//Collections.singleton(o)
		//List<SimpleGrantedAuthority> x = Arrays.asList(a).stream().map((String role) -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toList());
	}
}