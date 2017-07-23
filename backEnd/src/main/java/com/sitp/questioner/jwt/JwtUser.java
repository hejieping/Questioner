package com.sitp.questioner.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by qi on 2017/7/23.
 */
public class JwtUser implements UserDetails{
    private final Long id;
    private final String username;
    private final String password;
 //   private final LocalDate lastPasswordResetDate;
    private final Collection<? extends GrantedAuthority> authorities;
    public JwtUser(Long id,String username,String password,
                   Collection<? extends GrantedAuthority> authorities)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
 //       this.lastPasswordResetDate = lastPasswordResetDate;
    }
    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
