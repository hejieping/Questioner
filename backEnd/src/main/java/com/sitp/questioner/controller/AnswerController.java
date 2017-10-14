package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.service.abs.AnswerService;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by qi on 2017/10/14.
 */
@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = "/getAnswerNum/{questionId}", method = RequestMethod.GET)
    public ResJsonTemplate getAnswerNum(@PathVariable("questionId") Long questionId){
        return new ResJsonTemplate<>("200", answerService.getAnswerNumOfQuestion(questionId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResJsonTemplate postAnswer(@RequestBody Answer answer){
        if(answerService.saveAnswer(answer)){
            return new ResJsonTemplate<>("201", "书写答案成功！");
        }
        else {
            return new ResJsonTemplate<>("400", "书写答案失败！");
        }
    }

    @RequestMapping(value = "/getLimitAnswers",method = RequestMethod.GET)
    public ResJsonTemplate getLimitAnswers(@RequestParam("questionId") Long questionId,
                                           @RequestParam("startIndex") int startIndex,
                                           @RequestParam("limitNum") int limitNum){
        List<Answer> answers = answerService.getLimitAnswers(questionId,startIndex,limitNum);
        return new ResJsonTemplate<>("200", answers);
    }

}
