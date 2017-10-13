package com.sitp.questioner.controller;

import com.sitp.questioner.entity.QuestionType;
import com.sitp.questioner.service.abs.QuestionTypeService;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
