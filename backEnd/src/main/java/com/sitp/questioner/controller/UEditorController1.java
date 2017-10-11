package com.sitp.questioner.controller;

import com.sitp.questioner.ueditor.ActionEnter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * Created by qi on 2017/10/10.
 */

@RestController
@CrossOrigin
@RequestMapping("/ueditor1")
public class UEditorController1 {

    @RequestMapping(value = "/exec")
    @ResponseBody
    public String exec(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
        request.setCharacterEncoding("utf-8");
        String rootPath = request.getRealPath("/");
        return new ActionEnter(request, rootPath).exec();
    }
}
