package com.sitp.questioner.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sitp.questioner.constants.CreditEnum;
import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.entity.CreditRecord;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.jwt.JwtUser;
import com.sitp.questioner.service.abs.AccountService;
import com.sitp.questioner.service.abs.AnswerCommentService;
import com.sitp.questioner.service.abs.AnswerService;
import com.sitp.questioner.service.abs.CreditRecordService;
import com.sitp.questioner.service.abs.QuestionNoticeService;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.util.ResJsonTemplate;
import com.sitp.questioner.viewmodel.AnswerOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qi on 2017/10/14.
 */
@RestController
@RequestMapping("/answer")
public class AnswerController {
    /**
     * 线程池服务
     */
    @Autowired
    private  ExecutorService executorService ;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerCommentService answerCommentService;
    @Autowired
    private CreditRecordService creditRecordService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private QuestionNoticeService questionNoticeService;


    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{questionId}", method = RequestMethod.POST)
    public ResJsonTemplate postAnswer(@RequestBody Answer answer,
                                      @PathVariable("questionId") Long questionId){

        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        Question question = new Question();
        question.setId(questionId);
        answer.setQuestion(question);
        Account account = accountService.getUser(userId);
        account.setCreditPoint(account.getCreditPoint() + CreditEnum.ANSWER_QUESTION.getCreditValue());
        answer.setAccount(account);
        if(answerService.saveAnswer(answer)){
            executorService.submit(() -> questionNoticeService.createNoticeAfterAnswerQuestion(answer));
            //保存声望记录
            CreditRecord creditRecord = new CreditRecord();
            creditRecord.setType(CreditEnum.ANSWER_QUESTION.getType());
            creditRecord.setAnswer(answer);
            creditRecord.setDatetime(new Date());
            executorService.submit(() -> accountService.save(account));
            executorService.submit(()-> creditRecordService.save(creditRecord));
            return new ResJsonTemplate<>("201", "书写答案成功！");
        }
        else {
            return new ResJsonTemplate<>("400", "书写答案失败！");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/giveFeedback", method = RequestMethod.PUT)
    public ResJsonTemplate giveFeedback(@RequestParam("answerId") Long answerId,
                                        @RequestParam("isGood") boolean isGood){
        Long userId = ((JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        if(answerService.userHasFeedback(answerId,userId)){
            return new ResJsonTemplate<>("412", "已经对该问题作出反馈了！");//412  (Precondition Failed/先决条件错误)
        }
        else {
            answerService.giveAnswerFeedBack(answerId, userId, isGood);
            Answer answer = answerService.getAnswer(answerId);
            CreditRecord creditRecord = new CreditRecord();
            creditRecord.setAnswer(answer);
            creditRecord.setDatetime(new Date());
            //声望值加减
            if(isGood){
                answer.getAccount().setCreditPoint(answer.getAccount().getCreditPoint()+ CreditEnum.FEED_BACK_GOOD.getCreditValue());
                creditRecord.setType(CreditEnum.FEED_BACK_GOOD.getType());
            }
            else {
                answer.getAccount().setCreditPoint(answer.getAccount().getCreditPoint()+ CreditEnum.FEED_BACK_BAD.getCreditValue());
                creditRecord.setType(CreditEnum.FEED_BACK_BAD.getType());
            }
            //保存声望记录
            executorService.submit(()->creditRecordService.save(creditRecord));
            //保存账户信息
            executorService.submit(()-> accountService.save(answer.getAccount()));
            return new ResJsonTemplate<>("200", "对答案反馈成功！");
        }
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/acceptAnswer", method = RequestMethod.PUT)
    public ResJsonTemplate acceptAnswer(@RequestParam("answerId") Long answerId) {
        Long userId = ((JwtUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Long publisher_id = answerService.getQuestionPublisherByAnswer(answerId);
        if(publisher_id == null || !publisher_id.equals(userId)) {
            return new ResJsonTemplate<>("401", "你无权采纳该回答！");
        }
        Answer answer = answerService.getAnswer(answerId);
        //声望值记录
        Account account = answer.getAccount();
        CreditRecord creditRecord = new CreditRecord();
        account.setCreditPoint(account.getCreditPoint() + CreditEnum.ACCEPT_ANSWER.getCreditValue());
        creditRecord.setAnswer(answer);
        creditRecord.setType(CreditEnum.ACCEPT_ANSWER.getType());
        creditRecord.setDatetime(new Date());
        executorService.submit(() -> accountService.save(account));
        executorService.submit(() -> creditRecordService.save(creditRecord));
        return new ResJsonTemplate<>("200",answerService.acceptAnswer(answerId));
    }

    @RequestMapping(value = "/getAnswerNum/{questionId}", method = RequestMethod.GET)
    public ResJsonTemplate getAnswerNum(@PathVariable("questionId") Long questionId){
        return new ResJsonTemplate<>("200", answerService.getAnswerNumOfQuestion(questionId));
    }

    @RequestMapping(value = "/{questionId}/{answerId}", method = RequestMethod.GET)
    public ResJsonTemplate getQuestionSingleAnswer(@PathVariable("questionId") Long questionId,
                                                   @PathVariable("answerId") Long answerId) {
        Answer answer = answerService.getAnswer(answerId);
        if (answer == null || !answer.getQuestion().getId().equals(questionId)) {
            return new ResJsonTemplate<>("404", null);
        }
        else {
            return new ResJsonTemplate<>("200", answer);
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

    private static Map<String, Object> buildAnswerOverviewResult(Page<Answer> answerPage, QuestionService questionService) {
        List<AnswerOverview> answerOverviewList = new ArrayList<>();
        List<Answer> answers = answerPage.getContent();
        for(Answer answer: answers) {
            AnswerOverview answerOverview = new AnswerOverview();
            answerOverview.setAccepted(answer.getAccepted());
            answerOverview.setAnswerDateTime(answer.getAnswerDateTime());
            answerOverview.setId(answer.getId());
            answerOverview.setThumbsUpCount(answer.getThumbsUpCount());
            answerOverview.setThumbsDownCount(answer.getThumbsDownCount());
            answerOverview.setQuestionId(answer.getQuestion().getId());
            answerOverview.setQuestionTitle(answer.getQuestion().getQuestionTitle());
            answerOverviewList.add(answerOverview);
        }
        Long total = answerPage.getTotalElements();
        Map<String, Object> result = new HashMap<>();
        result.put("content", answerOverviewList);
        result.put("total", total);
        return result;
    }

    @RequestMapping(value = "/getUserAnswersByDateTime/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getUserAnswersByDateTime(@PathVariable("userId") Long userId,
                                                    @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Answer> answerPage = answerService.getUserAnswersByDateTime(userId, currentPage, pageSize);
        return new ResJsonTemplate<>("200", buildAnswerOverviewResult(answerPage, questionService));
    }

    @RequestMapping(value = "/getUserAnswersByThumbsUpCount/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getUserAnswersByThumbsUpCount(@PathVariable("userId") Long userId,
                                                         @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Answer> answerPage = answerService.getUserAnswerByThumbsUpCount(userId, currentPage, pageSize);
        return new ResJsonTemplate<>("200", buildAnswerOverviewResult(answerPage, questionService));
    }

    @RequestMapping(value = "/getUserAnswerCount/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getUserAnswerCount(@PathVariable("userId") Long userId) {
        return new ResJsonTemplate<>("200", answerService.getUserAnswerCount(userId));
    }

}
