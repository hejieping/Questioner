package com.sitp.questioner.controller;

import com.sitp.questioner.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * Created by qi on 2017/10/11.
 */
@WebServlet(name = "UEditorServlet", urlPatterns = "/ueditor/exec")
public class UEditorController extends HttpServlet{
    @Autowired
    private final ResourceLoader resourceLoader;
    @Autowired
    public UEditorController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding( "utf-8" );
        resp.setHeader("Content-Type" , "text/html");
        String rootPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        rootPath = URLDecoder.decode(rootPath, "utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ActionEnter( req, rootPath ).exec());
    }
}
