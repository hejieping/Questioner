package com.sitp.questioner.jwt;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by qi on 2017/7/23.
 */
public final class JwtUserFactory {
    private JwtUserFactory()
    {

    }

    public static JwtUser create(Account account)
    {
        return new JwtUser(
                account.getId(),
                account.getLoginUsername(),
                account.getPassword(),
                mapToGrantedAuthorities(account.getRoles()));
    }
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities)
    {
        return authorities.stream()
                .map(role->new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }









}
