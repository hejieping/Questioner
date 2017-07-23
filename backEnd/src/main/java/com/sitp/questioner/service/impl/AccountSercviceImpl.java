package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Role;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.repository.RoleRepository;
import com.sitp.questioner.service.abs.AccountService;
import com.sitp.questioner.service.abs.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by jieping on 2017-07-08.
 */
@Service
public class AccountSercviceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleService roleService;

    @Override
    public Account register(Account account) {
        final String username = account.getUsername();
        if (accountRepository.findByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = account.getPassword();
        account.setPassword(encoder.encode(rawPassword));
        // set default the user role
        Role userRole = roleService.getUserRole();
        account.setRoles(Collections.singleton(userRole));
        return accountRepository.save(account);
    }
}
