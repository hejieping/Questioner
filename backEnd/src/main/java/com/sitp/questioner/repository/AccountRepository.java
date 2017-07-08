package com.sitp.questioner.repository;

import com.sitp.questioner.entity.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jieping on 2017-07-08.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    /**
     * @param account 要存储的account
     * @return 返回已经存储在数据库的account
     */
    Account save(Account account);
    /**
     * @param username
     * @return 返回符合username的account对象
     */
    Account findByUsername(String username);
}
