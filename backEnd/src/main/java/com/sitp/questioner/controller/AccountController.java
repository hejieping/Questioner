package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.jwt.JwtAuthenticationRequest;
import com.sitp.questioner.jwt.JwtAuthenticationResponse;
import com.sitp.questioner.jwt.JwtUser;
import com.sitp.questioner.service.abs.AccountService;
import com.sitp.questioner.service.abs.AuthService;
import com.sitp.questioner.util.AvatarUtil;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/uploadAvatar", consumes = "multipart/form-data",method = RequestMethod.POST)
    public ResJsonTemplate uploadAvatar(@RequestParam("avatar") MultipartFile avatarFile){
        if(!avatarFile.isEmpty())
        {
            String relativeUrl = AvatarUtil.saveAvatar(avatarFile);
            Long userId = ((JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            Account account = accountService.getUser(userId);
            if (account != null) {
                String oldAvatarPath = account.getAvatarURL();
                AvatarUtil.deleteAvatar(oldAvatarPath, deploymentURL);
                String imgAbsoluteUrl = deploymentURL + relativeUrl;
                account.setAvatarURL(imgAbsoluteUrl);
                accountService.save(account);
                return new ResJsonTemplate<>("200", imgAbsoluteUrl);
            }
        }
        return new ResJsonTemplate<>("404","上传头像失败");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public ResJsonTemplate testAdmin()
    {
        return new ResJsonTemplate<>("400","you are admin");
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/userOwnInfo",method = RequestMethod.GET)
    public ResJsonTemplate getOwnInfo(){
        JwtUser jwtUser =(JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountService.getUser(jwtUser.getId());
        return new ResJsonTemplate<>("200",account);
    }


    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getUser(@PathVariable("userId") Long userId) {
        Account account = accountService.getUser(userId);
        if (account == null) {
            return new ResJsonTemplate<>("404", null);
        }
        else {
            return new ResJsonTemplate<>("200", account);
        }
    }

    @RequestMapping(value = "/user/followersInfo/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getFollowerInfo(@PathVariable("userId") Long userId) {
        Long followerCount = accountService.getUserFollowersCount(userId);
        Long followedCount = accountService.getUserFollowedCount(userId);
        Map<String, Long> followInfo = new HashMap<>();
        followInfo.put("followerCount", followerCount);
        followInfo.put("followedCount", followedCount);
        return new ResJsonTemplate<>("200", followInfo);
    }



    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/user/hasFollow/{followedId}", method = RequestMethod.GET)
    public ResJsonTemplate hasFollow(@PathVariable("followedId") Long followedId) {
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200", accountService.hasFollowUser(userId, followedId));
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/user/follow/{followedId}", method = RequestMethod.GET)
    public ResJsonTemplate followUser(@PathVariable("followedId") Long followedId) {
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200", accountService.followUser(userId, followedId));
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/user/unFollow/{followedId}", method = RequestMethod.GET)
    public ResJsonTemplate unFollowUser(@PathVariable("followedId") Long followedId) {
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200", accountService.unFollowUser(userId, followedId));
    }

    @RequestMapping(value = "/user/getFollowers/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getFollowers(@PathVariable("userId") Long userId,
                                        @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ResJsonTemplate<>("200", accountService.getUserFollowers(userId, currentPage, pageSize));
    }

    @RequestMapping(value = "/user/getFollowed/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getFollowed(@PathVariable("userId") Long userId,
                                       @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ResJsonTemplate<>("200", accountService.getUserFollowed(userId, currentPage,pageSize));
    }

}
