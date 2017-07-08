package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.service.AccountService;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jieping on 2017-07-08.
 */
@RestController
public class AccountController {
    @Autowired
    AccountService accountService;
    @RequestMapping(value = "/index")
    @ResponseBody
    public ResJsonTemplate hello() {
        return new ResJsonTemplate("200","welcome");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResJsonTemplate register(@RequestBody Account addedUser)  {
        if (accountService.register(addedUser) != null) {

            return new ResJsonTemplate<java.lang.String>("201", "注册成功");
        } else {
            return new ResJsonTemplate<java.lang.String>("400", "注册失败");
        }
    }

}
