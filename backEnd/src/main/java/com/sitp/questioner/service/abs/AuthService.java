package com.sitp.questioner.service.abs;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by qi on 2017/7/23.
 */
public interface AuthService {
    String login(String username, String password);
    String refresh(String oldToken);
    boolean verify(String username, String password, JSONObject result);
}
