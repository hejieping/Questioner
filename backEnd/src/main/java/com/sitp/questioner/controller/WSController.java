package com.sitp.questioner.controller;

import groovy.util.logging.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by qi on 2017/10/22.
 */
@Controller
@Log4j
public class WSController {
    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/notice")
    public void notice(String message) {
        template.convertAndSend("/topic/notice", "Hello World");
    }
}
