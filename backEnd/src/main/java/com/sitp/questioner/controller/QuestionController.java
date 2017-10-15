package com.sitp.questioner.controller;

import com.sitp.questioner.entity.Question;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.util.ResJsonTemplate;
import com.sitp.questioner.viewmodel.QuestionOverview;
import com.sitp.questioner.viewmodel.QuestionOverviewList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.GET)
    public ResJsonTemplate getAllQuestions(@RequestParam("pageSize") int pageSize,
                                           @RequestParam("currentPage") int currentPage){
        Page<Question> questions = questionService.getAllQuestionByPage(pageSize, currentPage);
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
            questionOverview.setSolved(false);
            questionOverview.setPublisherName(question.getPublisher().getUsername());
            questionOverview.setPublisherImgSrc(question.getPublisher().getAvatarURL());
            questionOverviewList.addQuestionOverview(questionOverview);
        }
        questionOverviewList.setCurrentPage(questions.getNumber());
        questionOverviewList.setHasNext(questions.hasNext());
        questionOverviewList.setHasPre(questions.hasPrevious());
        questionOverviewList.setTotalPage(questions.getTotalPages());
        questionOverviewList.setTotalNumber(questions.getTotalElements());
        return new ResJsonTemplate<>("200", questionOverviewList);
    }

    @RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
    public ResJsonTemplate getQuestion(@PathVariable Long questionId){
        Question question = questionService.getQuestion(questionId);
        if(question == null)
            return new ResJsonTemplate<>("404","不存在的问题！");
        return new ResJsonTemplate<>("200", question);
    }

    @RequestMapping(value = "/hasFollow", method = RequestMethod.GET)
    public ResJsonTemplate hasFollow(@RequestParam("questionId") Long questionId,
                                     @RequestParam("userId") Long userId){
        return new ResJsonTemplate<>("200",
                questionService.hasFollowQuestion(questionId, userId));
    }

    @RequestMapping(value = "/followQuestion", method = RequestMethod.PUT)
    public ResJsonTemplate followQuestion(@RequestParam("questionId") Long questionId,
                                          @RequestParam("userId") Long userId){
        return new ResJsonTemplate<>("200",questionService.userFollowQuestion(questionId,userId));
    }

    @RequestMapping(value = "/unFollowQuestion", method = RequestMethod.PUT)
    public ResJsonTemplate unFollowQuestion(@RequestParam("questionId") Long questionId,
                                            @RequestParam("userId") Long userId){
        return new ResJsonTemplate<>("200", questionService.userUnFollowQuestion(questionId, userId));
    }

    @RequestMapping(value = "/questionOverview", method = RequestMethod.GET)
    public ResJsonTemplate getAllQuestionOverview(){
        Page<Question> questionPageable = questionService.getAllQuestionByPage(10,0);
        return new ResJsonTemplate<>("200", questionPageable);
    }
}
