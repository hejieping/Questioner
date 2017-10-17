package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.entity.AnswerComment;
import com.sitp.questioner.service.abs.AnswerCommentService;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by qi on 2017/10/16.
 */
@RestController
@RequestMapping("/answerComment")
public class AnswerCommentController {
    @Autowired
    private AnswerCommentService answerCommentService;

    @RequestMapping(value = "/{answerId}", method = RequestMethod.POST)
    public ResJsonTemplate sendAnswerComment(@RequestBody AnswerComment answerComment,
                                             @PathVariable("answerId") Long answerId){
        Answer answer = new Answer();
        answer.setId(answerId);
        answerComment.setAnswer(answer);
        return new ResJsonTemplate<>("201", answerCommentService.saveAnswerComment(answerComment));
    }

    @RequestMapping(value = "/{answerId}", method = RequestMethod.GET)
    public ResJsonTemplate getCommentsOfAnswer(@PathVariable("answerId") Long answerId){
        return new ResJsonTemplate<>("200", answerCommentService.getAllCommentsOfAnswer(answerId));
    }

    @RequestMapping(value = "/getCommentCount/{answerId}", method = RequestMethod.GET)
    public ResJsonTemplate getCommentCountOfAnswer(@PathVariable("answerId") Long answerId){
        return new ResJsonTemplate<>("201", answerCommentService.getCommentCountOfAnswer(answerId));
    }


}
