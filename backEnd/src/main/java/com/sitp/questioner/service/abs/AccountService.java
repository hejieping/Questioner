package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.Account;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by jieping on 2017-07-08.
 */
public interface AccountService {

    Account save(Account account);

    Account register(Account userToAdd);

    Account getUser(Long userId);

    Long getUserFollowersCount(Long userId);

    Long getUserFollowedCount(Long userId);

    Page<Account> getUserFollowers(Long userId, int currentPage, int pageSize);

    Page<Account> getUserFollowed(Long userId, int currentPage, int pageSize);

    boolean followUser(Long userId, Long followedUserId);

    boolean unFollowUser(Long userId, Long followedUserId);

    boolean hasFollowUser(Long userId, Long followedUserId);

    boolean validateLoginUsername(String loginUsername);
}
