package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by jieping on 2017-07-08.
 */
@Service
public class AccountSercviceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public Account register(Account account) {
        final String username = account.getUsername();
        if (accountRepository.findByUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = account.getPassword();
        account.setPassword(encoder.encode(rawPassword));
        return accountRepository.save(account);
    }
}
