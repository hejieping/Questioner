package com.sitp.questioner.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.service.abs.AccountService;
import com.sitp.questioner.service.abs.AuthService;
import com.sitp.questioner.util.JwtTokenUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by qi on 2017/7/23.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private Map<String, String> cookies4m3 = new HashMap<>();
    private Map<String, String> postData = new HashMap<>();
    private Map<String, String> cookiesIds = new HashMap<>();

    private final String step2 = "http://4m3.tongji.edu.cn/eams/login.action";
    private final String step1 = "http://4m3.tongji.edu.cn/";
    private final String step3 = "http://4m3.tongji.edu.cn/eams/samlCheck";

    @Override
    public boolean verify(String loginUsername, String password, JSONObject result) {

        try {
            Connection connection = Jsoup.connect(step1);
            Connection.Response response = connection.execute();
            for (Map.Entry<String, String> entry : response.cookies().entrySet()) {
                cookies4m3.put(entry.getKey(), entry.getValue());
            }

            connection = Jsoup.connect(step2);
            connection.cookie("SERVERNAME", cookies4m3.get("SERVERNAME"));

            response = connection.execute();
            for (Map.Entry<String, String> entry : response.cookies().entrySet()) {
                cookies4m3.put(entry.getKey(), entry.getValue());
            }

            connection = Jsoup.connect(step3);
            for (Map.Entry<String, String> entry : cookies4m3.entrySet()) {
                connection.cookie(entry.getKey(), entry.getValue());
            }

            response = connection.execute();
            Document doc = response.parse();
            for (Map.Entry<String, String> entry : response.cookies().entrySet()) {
                cookies4m3.put(entry.getKey(), entry.getValue());
            }

            String step2 = doc.select("meta").attr("content").substring(6);

            connection = Jsoup
                .connect(step2);

            response = connection.execute();
            doc = response.parse();
            for (Map.Entry<String, String> entry : response.cookies().entrySet()) {
                cookiesIds.put(entry.getKey(), entry.getValue());
            }

            String step3 = "https://ids.tongji.edu.cn:8443" + doc.select("form").attr("action");

            connection = Jsoup
                .connect(step3)
                .method(Connection.Method.POST);
            for (Map.Entry<String, String> entry : cookiesIds.entrySet()) {
                connection.cookie(entry.getKey(), entry.getValue());
            }

            response = connection.execute();
            doc = response.parse();
            for (Map.Entry<String, String> entry : response.cookies().entrySet()) {
                cookiesIds.put(entry.getKey(), entry.getValue());
            }

            String step4 = doc.select("form").attr("action");

            postData.put("option", "credential");
            postData.put("Ecom_User_ID", loginUsername);
            postData.put("Ecom_Password", password);
            postData.put("submit", "登录");

            connection = Jsoup
                .connect(step4)
                .method(Connection.Method.POST)
                .data(postData);
            for (Map.Entry<String, String> entry : cookiesIds.entrySet()) {
                connection.cookie(entry.getKey(), entry.getValue());
            }

            response = connection.execute();
            String webPage = response.parse().html();
            Pattern pattern = Pattern.compile("top.location.href=\'(.*?)\'");
            Matcher matcher = pattern.matcher(webPage);
            for (Map.Entry<String, String> entry : response.cookies().entrySet()) {
                cookiesIds.put(entry.getKey(), entry.getValue());
            }

            if (matcher.find()) {
                String step5 = matcher.group(1);

                connection = Jsoup.connect(step5);
                for (Map.Entry<String, String> entry : cookiesIds.entrySet()) {
                    connection.cookie(entry.getKey(), entry.getValue());
                }
                response = connection.execute();
                doc = response.parse();

                for (Map.Entry<String, String> entry : response.cookies().entrySet()) {
                    cookiesIds.put(entry.getKey(), entry.getValue());
                }

                String step6 = doc.select("form").attr("action");

                String SAMLResponse = doc.select("form").select("input").get(0).attr("value");
                String RelayState = doc.select("form").select("input").get(1).attr("value");

                postData.clear();
                postData.put("SAMLResponse", SAMLResponse);
                postData.put("RelayState", RelayState);
                cookies4m3.put("oiosaml-fragment", "");

                connection = Jsoup
                    .connect(step6)
                    .data(postData)
                    .method(Connection.Method.POST);
                for (Map.Entry<String, String> entry : cookies4m3.entrySet()) {
                    connection.cookie(entry.getKey(), entry.getValue());
                }
                // 一定要执行最后一步
                // 以使服务器确认完成认证
                doc = connection.execute().parse();
                String nameText = doc.body().select("form a").text();
                result.put("nameWithId", nameText);
                String name = nameText.split("\\(")[0];
                result.put("name", name);
                return true;
            }
            return false;

        } catch (Exception e) {
            result.put("error", e.getMessage());
            return false;
        }
    }

    @Override
    public String login(String loginUsername, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(loginUsername,password);
            // Perform the security
            final Authentication authentication =  authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-security so we can generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUsername);
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {

            //authenticate in 4m3
            JSONObject jsonRet = new JSONObject();
            Boolean verifyResult = verify(loginUsername, password, jsonRet);
            if(verifyResult){
                Account account = new Account();
                account.setLoginUsername(loginUsername);
                account.setUsername(jsonRet.getString("name"));
                account.setPassword(password);
                accountService.register(account);
                UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(loginUsername,password);
                final Authentication authentication =  authenticationManager.authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Reload password post-security so we can generate token
                final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUsername);
                return jwtTokenUtil.generateToken(userDetails);
            }
            throw e;
        }
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
 //       String username = jwtTokenUtil.getUserNameFromToken(token);
//        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, LocalDateTime.MIN)){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }
}
