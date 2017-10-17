package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.entity.AnswerComment;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.service.abs.AnswerCommentService;
import com.sitp.questioner.service.abs.AnswerService;
import com.sitp.questioner.util.ResJsonTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Created by qi on 2017/10/14.
 */
@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerCommentService answerCommentService;

    @RequestMapping(value = "/getAnswerNum/{questionId}", method = RequestMethod.GET)
    public ResJsonTemplate getAnswerNum(@PathVariable("questionId") Long questionId){
        return new ResJsonTemplate<>("200", answerService.getAnswerNumOfQuestion(questionId));
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.POST)
    public ResJsonTemplate postAnswer(@RequestBody Answer answer,
                                      @PathVariable("questionId") Long questionId){
        Question question = new Question();
        question.setId(questionId);
        answer.setQuestion(question);
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
                                           @RequestParam("limitNum") int limitNum,
                                           @RequestParam(value = "sortParam", defaultValue = "thumbs_up_couniot") String sortParam){
        List<Answer> answers = answerService.getLimitAnswers(questionId,startIndex,limitNum, sortParam);
        for(Answer answer: answers){
            answer.setCommentCount(answerCommentService.getCommentCountOfAnswer(answer.getId()));
        }
        return new ResJsonTemplate<>("200", answers);
    }

    @RequestMapping(value = "/giveFeedback", method = RequestMethod.PUT)
    public ResJsonTemplate giveFeedback(@RequestParam("answerId") Long answerId,
                                        @RequestParam("userId") Long userId,
                                        @RequestParam("isGood") boolean isGood){
        if(answerService.userHasFeedback(answerId,userId)){
            return new ResJsonTemplate<>("412", "已经对该问题作出反馈了！");//412  (Precondition Failed/先决条件错误)
        }
        else {
            answerService.giveAnswerFeedBack(answerId, userId, isGood);
            return new ResJsonTemplate<>("200", "对答案反馈成功！");
        }
    }

    @RequestMapping(value = "/acceptAnswer", method = RequestMethod.PUT)
    public ResJsonTemplate acceptAnswer(@RequestParam("answerId") Long answerId,
                                        @RequestParam("userId") Long userId) {
        Long publisher_id = answerService.getQuestionPublisherByAnswer(answerId);
        if(publisher_id == null || !publisher_id.equals(userId)) {
            return new ResJsonTemplate<>("401", "你无权采纳该回答！");
        }
        return new ResJsonTemplate<>("200",answerService.acceptAnswer(answerId));
    }

}
