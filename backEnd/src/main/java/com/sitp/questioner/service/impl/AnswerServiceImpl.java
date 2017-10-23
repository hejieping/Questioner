package com.sitp.questioner.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.repository.AnswerRepository;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.service.abs.AnswerService;
import com.sitp.questioner.util.PageableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by qi on 2017/10/14.
 */
@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public int getAnswerNumOfQuestion(Long questionId) {
        return answerRepository.getAnswerNumOfQuestion(questionId);
    }

    @Override
    public List<Answer> getLimitAnswers(Long questionId, int startIndex, int limitNum, String sortParam) {
        if (sortParam.equals("id")) {
            return answerRepository.getLimitAnswersOrderById(questionId, startIndex, limitNum);
        }
        else
            return answerRepository.getLimitAnswersOrderByDefault(questionId, startIndex, limitNum);
    }

    @Override
    public boolean saveAnswer(Answer answer) {
        return answerRepository.save(answer) != null;
    }

    @Override
    public boolean userHasFeedback(Long answerId, Long userId) {
        return answerRepository.hasFeedBack(answerId, userId) > 0;
    }

    @Override
    public boolean giveAnswerFeedBack(Long answerId, Long userId, boolean isGood) {
        Answer answer = answerRepository.findOne(answerId);
        if(isGood){
            answer.setThumbsUpCount(answer.getThumbsUpCount() + 1);
        }
        else {
            answer.setThumbsDownCount(answer.getThumbsDownCount() + 1);
        }
        Account account = accountRepository.findOne(userId);
        answer.getFeedbackAccounts().add(account);
        answerRepository.save(answer);
        return true;
    }

    @Override
    public Long getQuestionPublisherByAnswer(Long answerId) {
        return answerRepository.getQuestionPublisherByAnswer(answerId);
    }

    @Override
    @Transactional
    public  boolean acceptAnswer(Long answerId) {
        Answer answer = answerRepository.findOne(answerId);
        if(answer != null) {
            Long questionId = answer.getQuestion().getId();
            Question question = questionRepository.findOne(questionId);
            if(question != null) {
                if(question.getSolved() == null || !question.getSolved()) {
                    question.setSolved(true);
                    questionRepository.save(question);
                }
                answer.setAccepted(true);
                answerRepository.save(answer);
                return true;
            }
        }
        return false;
    }

    @Override
    public Page<Answer>getUserAnswersByDateTime(Long userId, int currentPage, int pageSize) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setDirection(Sort.Direction.DESC)
                .setSortParam("id").buildPage();
        return answerRepository.getUserAnswers(userId, pageable);
    }

    @Override
    public Page<Answer> getUserAnswerByThumbsUpCount(Long userId, int currentPage, int pageSize) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).addSortParam(Sort.Direction.DESC,"thumbsUpCount")
                .addSortParam(Sort.Direction.ASC, "thumbsDownCount")
                .buildPage();
        return answerRepository.getUserAnswers(userId, pageable);
    }

    @Override
    public Answer getAnswer(Long answerId) {
        return answerRepository.findOne(answerId);
    }

    @Override
    public Long getUserAnswerCount(Long userId) {
        return answerRepository.countByAccountId(userId);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
}
