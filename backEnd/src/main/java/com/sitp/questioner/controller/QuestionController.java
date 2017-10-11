package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Question;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.service.impl.QuestionServiceImpl;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qi on 2017/10/11.
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResJsonTemplate raiseQuestion(@RequestBody Question question){
        if(questionService.saveQuestion(question)){
            return new ResJsonTemplate<>("201","成功发布问题！");
        } else {
            return new ResJsonTemplate<>("400", "发布问题失败！");
        }
    }

}
