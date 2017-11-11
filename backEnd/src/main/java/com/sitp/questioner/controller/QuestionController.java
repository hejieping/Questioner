package com.sitp.questioner.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.jwt.JwtUser;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.service.abs.RecommendService;
import com.sitp.questioner.util.ResJsonTemplate;
import com.sitp.questioner.viewmodel.QuestionOverview;
import com.sitp.questioner.viewmodel.QuestionOverviewList;
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
 * Created by qi on 2017/10/11.
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RecommendService recommendService;
    private static ExecutorService executorService = Executors.newFixedThreadPool(20);
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResJsonTemplate raiseQuestion(@RequestBody Question question){
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Account account = new Account();
        account.setId(userId);
        question.setPublisher(account);
        if(questionService.saveQuestion(question)){
            return new ResJsonTemplate<>("201","成功发布问题！");
        } else {
            return new ResJsonTemplate<>("400", "发布问题失败！");
        }
    }

    private static QuestionOverviewList buildQuestionOverviewList(QuestionService questionService,Page<Question> questions){
        Iterable<Question> questionContent = questions.getContent();
        QuestionOverviewList questionOverviewList = new QuestionOverviewList();
        for (Question question : questionContent) {
            QuestionOverview questionOverview = new QuestionOverview();
            questionOverview.setAnswers(questionService.getAnswerNumber(question.getId()));
            questionOverview.setId(question.getId());
            questionOverview.setCourse(question.getQuestionType().getCourse());
            questionOverview.setSubject(question.getQuestionType().getSubject());
            questionOverview.setPublisherId(question.getPublisher().getId());
            questionOverview.setPublishDateTime(question.getPublishDateTime());
            questionOverview.setPublisherId(question.getPublisher().getId());
            questionOverview.setTitle(question.getQuestionTitle());
            questionOverview.setSolved(question.getSolved());
            questionOverview.setPublisherName(question.getPublisher().getUsername());
            questionOverview.setPublisherImgSrc(question.getPublisher().getAvatarURL());
            questionOverview.setViews(question.getViews());
            questionOverviewList.addQuestionOverview(questionOverview);
        }
        questionOverviewList.setCurrentPage(questions.getNumber());
        questionOverviewList.setHasNext(questions.hasNext());
        questionOverviewList.setHasPre(questions.hasPrevious());
        questionOverviewList.setTotalPage(questions.getTotalPages());
        questionOverviewList.setTotalNumber(questions.getTotalElements());
        return questionOverviewList;
    }
    private void replaceQuestionOverviewList(QuestionOverviewList questionOverviewList, List<Question> questionList){
        for(int i = 0; i < questionList.size(); i++){
            Question question = questionList.get(i);
            QuestionOverview questionOverview = questionOverviewList.getQuestionOverviewList().get(i);
            questionOverview.setAnswers(questionService.getAnswerNumber(question.getId()));
            questionOverview.setId(question.getId());
            questionOverview.setCourse(question.getQuestionType().getCourse());
            questionOverview.setSubject(question.getQuestionType().getSubject());
            questionOverview.setPublisherId(question.getPublisher().getId());
            questionOverview.setPublishDateTime(question.getPublishDateTime());
            questionOverview.setPublisherId(question.getPublisher().getId());
            questionOverview.setTitle(question.getQuestionTitle());
            questionOverview.setSolved(question.getSolved());
            questionOverview.setPublisherName(question.getPublisher().getUsername());
            questionOverview.setPublisherImgSrc(question.getPublisher().getAvatarURL());
            questionOverview.setViews(question.getViews());
        }
    }
    @RequestMapping(method = RequestMethod.GET)
    public ResJsonTemplate getAllQuestions(@RequestParam("pageSize") int pageSize,
                                           @RequestParam("currentPage") int currentPage,
                                           @RequestParam(value = "questionTitle",defaultValue = "") String questionTitle,
                                           @RequestParam(value = "sortParam", defaultValue = "id") String sortParam){
        Page<Question> questions;
        if(questionTitle.equals("")){
            questions = questionService.getAllQuestionByPage(pageSize, currentPage, sortParam);
        }
        else {
            questions = questionService.getQuestionTitleLike(questionTitle,pageSize,currentPage, sortParam);
        }
        QuestionOverviewList questionOverviewList = buildQuestionOverviewList(questionService, questions);
        return new ResJsonTemplate<>("200", questionOverviewList);



    }
    @RequestMapping(value = "/recommend",method = RequestMethod.GET)
    public ResJsonTemplate getRecommendQuestion(@RequestParam("questionSize") int questionSize){
        List<Question> recommendQuestions = Lists.newArrayList();
        try {
            //浏览记录
            Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
           recommendQuestions = recommendService.recommend(userId,questionSize);
           executorService.submit(() -> recommendService.refreshRecommendSystem());
        }catch (Exception e){
            //获取用户信息失败，表明是匿名登录
        }finally {
            return new ResJsonTemplate<>("200", recommendQuestions);
        }
    }
    @RequestMapping(value = "/getQuestionByType/{questionTypeId}", method = RequestMethod.GET)
    public ResJsonTemplate getQuestionsByType(@RequestParam("pageSize") int pageSize,
                                              @RequestParam("currentPage") int currentPage,
                                              @PathVariable("questionTypeId") Long typeId,
                                              @RequestParam(value = "questionTitle",defaultValue = "") String questionTitle,
                                              @RequestParam(value = "sortParam", defaultValue = "id") String sortParam){
        Page<Question> questions;
        if(questionTitle.equals("")){
            questions = questionService.getQuestionByPageAndType(typeId, pageSize, currentPage, sortParam);
        }
        else {
            questions = questionService.getQuestionTitleLikeByType(typeId,questionTitle,pageSize,currentPage, sortParam);
        }
        QuestionOverviewList questionOverviewList = buildQuestionOverviewList(questionService, questions);
        return new ResJsonTemplate<>("200", questionOverviewList);
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public ResJsonTemplate getQuestion(@PathVariable Long questionId){
        try {
            //浏览记录
            Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            recommendService.broweRecord(userId,questionId);

        }catch (Exception e){
            //获取用户信息失败，表明是匿名登录
        }finally {
            Question question = questionService.getQuestion(questionId);
            if(question == null)
                return new ResJsonTemplate<>("404","不存在的问题！");
            question.setViews(question.getViews() + 1);
            new Thread(() -> questionService.saveQuestion(question)).start();
            return new ResJsonTemplate<>("200", question);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/personal/hasFollow", method = RequestMethod.GET)
    public ResJsonTemplate hasFollow(@RequestParam("questionId") Long questionId){
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200",
                questionService.hasFollowQuestion(questionId, userId));
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/personal/followQuestion", method = RequestMethod.PUT)
    public ResJsonTemplate followQuestion(@RequestParam("questionId") Long questionId){
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200",questionService.userFollowQuestion(questionId,userId));
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/personal/unFollowQuestion", method = RequestMethod.PUT)
    public ResJsonTemplate unFollowQuestion(@RequestParam("questionId") Long questionId){
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return new ResJsonTemplate<>("200", questionService.userUnFollowQuestion(questionId, userId));
    }

    @RequestMapping(value = "/getFollow/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getFollowQuestion(@PathVariable("userId") Long userId,
                                             @RequestParam(value = "currentPage",defaultValue = "0") int currentPage,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ResJsonTemplate<>("200",questionService.getUserFollowQuestions(userId,currentPage,pageSize));
    }

    @RequestMapping(value = "/getUserQuestionByViews/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getUserQuestionByViews(@PathVariable("userId") Long userId,
                                                  @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ResJsonTemplate<>("200", questionService.getUserQuestionByViews(userId, currentPage, pageSize));
    }

    @RequestMapping(value = "/getUserQuestionByDateTime/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getUserQuestionByDateTIme(@PathVariable("userId") Long userId,
                                                     @RequestParam(value = "currentPage", defaultValue = "0") int currentPage,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return new ResJsonTemplate<>("200", questionService.getUserQuestionByDateTime(userId, currentPage, pageSize));
    }

    @RequestMapping(value = "/getUserQuestionCount/{userId}", method = RequestMethod.GET)
    public ResJsonTemplate getUserQuestionCount(@PathVariable("userId") Long userId) {
        return new ResJsonTemplate<>("200", questionService.getUserQuestionCount(userId));
    }
}
