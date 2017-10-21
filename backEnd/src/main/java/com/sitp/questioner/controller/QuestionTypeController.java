package com.sitp.questioner.controller;

import com.sitp.questioner.entity.QuestionType;
import com.sitp.questioner.jwt.JwtUser;
import com.sitp.questioner.service.abs.QuestionTypeService;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by qi on 2017/10/12.
 */
@RestController
@RequestMapping("/questionType")
public class QuestionTypeController {

    @Autowired
    private QuestionTypeService questionTypeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResJsonTemplate getAllQuestionType(){
        Iterable<QuestionType> questionTypeList = questionTypeService.getAllQuestionType();
        return new ResJsonTemplate<>("200", questionTypeList);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/hasFollow", method = RequestMethod.GET)
    public ResJsonTemplate hasFollow(@RequestParam("questionTypeId") Long questionTypeId) {
        JwtUser jwtUser =(JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = jwtUser.getId();
        return new ResJsonTemplate<>("200", questionTypeService.hasFollowQuestionType(questionTypeId,userId));
    }

    @RequestMapping(value = "/follow", method = RequestMethod.PUT)
    public ResJsonTemplate followQuestionType(@RequestParam("questionTypeId") Long questionTypeId) {
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200", questionTypeService.followQuestionType(questionTypeId, userId));
    }

    @RequestMapping(value = "/unFollow", method = RequestMethod.PUT)
    public ResJsonTemplate unFollowQuestionType(@RequestParam("questionTypeId") Long questionTypeId) {
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200", questionTypeService.unFollowQuestionType(questionTypeId,userId));
    }

    @RequestMapping(value = "/getFollow/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getFollow(@PathVariable("userId") Long userId,
                                     @RequestParam(value = "currentPage",defaultValue = "0") int currentPage,
                                     @RequestParam(value = "pageSize", defaultValue = "0") int pageSize) {
        return new ResJsonTemplate<>("200", questionTypeService.getUserFollowQuestionType(userId, currentPage, pageSize));
    }
}
