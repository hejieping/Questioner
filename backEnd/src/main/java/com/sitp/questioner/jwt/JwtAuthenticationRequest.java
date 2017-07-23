package com.sitp.questioner.jwt;

import java.io.Serializable;

/**
 * Created by qi on 2017/7/23.
 */
public class JwtAuthenticationRequest implements Serializable{
    private static final long serialVersionUID = -8445943548965154778L;

    private String username;
    private String password;

    public JwtAuthenticationRequest()
    {
        super();
    }
    public JwtAuthenticationRequest(String username,String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
