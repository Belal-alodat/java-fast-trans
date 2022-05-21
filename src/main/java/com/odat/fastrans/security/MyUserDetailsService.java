package com.odat.fastrans.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.odat.fastrans.entity.Account;
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
        return new org.springframework.security.core.userdetails.User(
                email,
                account.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
