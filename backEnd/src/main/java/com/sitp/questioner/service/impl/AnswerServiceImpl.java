package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.repository.AnswerRepository;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.service.abs.AnswerService;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.util.PageableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public List<Answer> getLimitAnswers(Long questionId, int startIndex, int limitNum) {
        return answerRepository.getLimitAnswers(questionId, startIndex, limitNum);
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
                if(!question.getSolved()) {
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
}
