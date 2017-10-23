package com.sitp.questioner.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by qi on 2017/10/23.
 */

public class WebSocketUtil {
    private static final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    public static String getUsernameFormWebSocketUrl(String pathUrl) {
        String username = null;
        String[] args = pathUrl.split("\\?");
        if (args.length >=2 ) {
            String queryParameters = args[1];
            String token = queryParameters.split("=")[1];
            username = jwtTokenUtil.getUserNameFromToken(token);
        //    username = token
        }
        return username;
    }
}
