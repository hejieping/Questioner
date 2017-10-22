package com.sitp.questioner.service;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.jwt.JwtUserFactory;
import com.sitp.questioner.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by qi on 2017/7/23.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginUsername) throws UsernameNotFoundException {
        Account account = accountRepository.findByLoginUsername(loginUsername);
        if(account == null)
        {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'",
                    loginUsername));
        }
        else
        {
            return JwtUserFactory.create(account);
        }
    }
}
