package com.sitp.questioner.controller;

import com.sitp.questioner.entity.QuestionNotice;
import com.sitp.questioner.jwt.JwtUser;
import com.sitp.questioner.service.abs.QuestionNoticeService;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by qi on 2017/10/26.
 */
@RestController
@RequestMapping(value = "/notice")
public class QuestionNoticeController {

    @Autowired
    private QuestionNoticeService questionNoticeService;

    @RequestMapping(value = "/readNotice/{noticeId}", method = RequestMethod.PUT)
    public ResJsonTemplate readNotice(@PathVariable("noticeId") Long noticeId) {
        questionNoticeService.markAsHasRead(noticeId);
        return new ResJsonTemplate<>("200", true);
    }


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/getUnreadNotice", method = RequestMethod.GET)
    public ResJsonTemplate getUnreadNotice() {
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<QuestionNotice> questionNoticeList = questionNoticeService.getUserNotReadNotice(userId);

        return new ResJsonTemplate<>("200", questionNoticeList);
    }
}
