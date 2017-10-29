package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Role;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.repository.RoleRepository;
import com.sitp.questioner.service.abs.AccountService;
import com.sitp.questioner.service.abs.RoleService;
import com.sitp.questioner.util.PageableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by jieping on 2017-07-08.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleService roleService;

    @Value("${default.avatarUrl}")
    private String defaultAvatarUrl;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account register(Account account) {
        final String username = account.getUsername();
        if (accountRepository.findByLoginUsername(username) != null) {
            return null;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = account.getPassword();
        account.setPassword(encoder.encode(rawPassword));
        // set default the user role
        Role userRole = roleService.getUserRole();
        account.getRoles().add(userRole);
        account.setAvatarURL(defaultAvatarUrl);
        return accountRepository.save(account);
    }

    @Override
    public Account getUser(Long userId) {
        return accountRepository.findOne(userId);
    }

    @Override
    public Long getUserFollowersCount(Long userId) {
        Long count = accountRepository.getUserFollowersCount(userId);
        return count != null ? count : 0L;
    }

    @Override
    public Long getUserFollowedCount(Long userId) {
        Long count = accountRepository.getUserFollowedCount(userId);
        return count != null ? count : 0L;
    }

    @Override
    public Page<Account> getUserFollowers(Long userId, int currentPage, int pageSize) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setDirection(Sort.Direction.ASC)
                .setSortParam("id").buildPage();
        return accountRepository.getUserFollowers(userId, pageable);
    }

    @Override
    public Page<Account> getUserFollowed(Long userId, int currentPage, int pageSize) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setDirection(Sort.Direction.ASC)
                .setSortParam("id").buildPage();
        return accountRepository.getUserFollowed(userId, pageable);
    }

    @Override
    public boolean followUser(Long userId, Long followedUserId) {
        if (userId.equals(followedUserId)) {
            return false;
        }
        Account account = accountRepository.findOne(userId);
        if (account != null) {
            Account followed_account = accountRepository.findOne(followedUserId);
            if (followed_account != null) {
                account.getFollowed().removeIf(user -> user.getId().equals(userId));
                account.getFollowed().add(followed_account);
                accountRepository.save(account);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unFollowUser(Long userId, Long followedUserId) {
        if (userId.equals(followedUserId)) {
            return false;
        }
        Account account = accountRepository.findOne(userId);
        if (account != null) {
            account.getFollowed().removeIf((user) -> user.getId().equals(followedUserId));
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean hasFollowUser(Long userId, Long followedUserId) {
        return accountRepository.hasFollow(userId, followedUserId) > 0;
    }

    @Override
    public boolean validateLoginUsername(String loginUsername) {
        return accountRepository.findByLoginUsername(loginUsername) == null;
    }
}
