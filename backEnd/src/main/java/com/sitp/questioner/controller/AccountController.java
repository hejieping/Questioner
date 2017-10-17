package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.jwt.JwtAuthenticationRequest;
import com.sitp.questioner.jwt.JwtAuthenticationResponse;
import com.sitp.questioner.jwt.JwtUser;
import com.sitp.questioner.service.abs.AccountService;
import com.sitp.questioner.service.abs.AuthService;
import com.sitp.questioner.util.FIleSaveUtil;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by jieping on 2017-07-08.
 */
@RestController
public class AccountController {
    @Autowired
    AuthService authService;
    @Autowired
    AccountService accountService;

    @Value("${deployment.url}")
    private String deploymentURL ;

    @RequestMapping(value = "/index")
    @ResponseBody
    public ResJsonTemplate hello() {
        return new ResJsonTemplate("200","welcome");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResJsonTemplate register(@RequestBody Account addedUser)  {
        if (accountService.register(addedUser) != null) {
            return new ResJsonTemplate<>("201", "注册成功");
        } else {
            return new ResJsonTemplate<>("400", "注册失败");
        }
    }

    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    public ResJsonTemplate auth(@RequestBody JwtAuthenticationRequest authenticationRequest)
        throws Exception
    {
        final String token = authService.login(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
        JwtAuthenticationResponse authenticationResponse = new JwtAuthenticationResponse(token);
        return new ResJsonTemplate<>("400", authenticationResponse);
    }


    @RequestMapping(value = "/uploadAvatar", consumes = "multipart/form-data",method = RequestMethod.POST)
    public ResJsonTemplate uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile){
        if(!avatarFile.isEmpty())
        {
            FIleSaveUtil.saveAvatar(avatarFile);
        }
        return new ResJsonTemplate<>("200","上传头像成功");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public ResJsonTemplate testAdmin()
    {
        return new ResJsonTemplate<>("400","you are admin");
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public ResJsonTemplate testUser(){
        JwtUser jwtUser =(JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(jwtUser.getId());
        return new ResJsonTemplate<>("400","you are user");
    }

}
