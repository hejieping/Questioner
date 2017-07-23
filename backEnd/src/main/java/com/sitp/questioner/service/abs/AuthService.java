package com.sitp.questioner.service.abs;

/**
 * Created by qi on 2017/7/23.
 */
public interface AuthService {
    String login(String username, String password);
    String refresh(String oldToken);
}
