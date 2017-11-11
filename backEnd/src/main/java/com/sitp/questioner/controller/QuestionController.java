package com.sitp.questioner.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.jwt.JwtUser;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.service.abs.RecommendService;
import com.sitp.questioner.util.ResJsonTemplate;
import com.sitp.questioner.viewmodel.QuestionOverview;
import com.sitp.questioner.viewmodel.QuestionOverviewList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;
    @Autowired
    private RecommendService recommendService;

    @Autowired
    private ExecutorService executorService;

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
            questionOverviewList.addQuestionOverview(generateQuestionOverviewFromQuestion(questionService, question));
        }
        questionOverviewList.setCurrentPage(questions.getNumber());
        questionOverviewList.setHasNext(questions.hasNext());
        questionOverviewList.setHasPre(questions.hasPrevious());
        questionOverviewList.setTotalPage(questions.getTotalPages());
        questionOverviewList.setTotalNumber(questions.getTotalElements());
        return questionOverviewList;
    }

    private static QuestionOverview generateQuestionOverviewFromQuestion(QuestionService questionService,Question question) {
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
        return questionOverview;
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

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/recommend",method = RequestMethod.GET)
    public ResJsonTemplate getRecommendQuestion(@RequestParam(value = "questionSize",defaultValue = "3") int questionSize){
        List<QuestionOverview> recommendQuestions = Lists.newArrayList();
        //浏览记录
        Long userId = ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        try {
            List<Question> questions = recommendService.recommend(userId,questionSize);
            recommendQuestions = questions.stream().map((question -> generateQuestionOverviewFromQuestion(questionService, question)))
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            logger.error("Recommend Question Fail");
            logger.error(e.toString());
        }
        executorService.submit(() -> recommendService.refreshRecommendSystem());
        return new ResJsonTemplate<>("200", recommendQuestions);
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
           executorService.submit(()->recommendService.browseRecord(userId,questionId));
        }catch (Exception e){
            //获取用户信息失败，表明是匿名登录
        }
        Question question = questionService.getQuestion(questionId);
        if(question == null)
            return new ResJsonTemplate<>("404","不存在的问题！");
        question.setViews(question.getViews() + 1);
        executorService.submit(()->questionService.saveQuestion(question));
        return new ResJsonTemplate<>("200", question);
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
