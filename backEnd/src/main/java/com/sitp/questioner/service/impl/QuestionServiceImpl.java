package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.Question;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.util.PageableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qi on 2017/10/11.
 */
@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean saveQuestion(Question question) {
        return questionRepository.save(question) != null;
    }

    @Override
    public Long getAnswerNumber(Long questionId) {
        return questionRepository.getTotalAnswerOfQuestion(questionId);
    }

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepository.findOne(questionId);
    }

    @Override
    public Page<Question> getAllQuestionByPage(int pageSize, int currentPage){
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setSortParam("id")
                .setDirection(Sort.Direction.DESC).buildPage();
        return questionRepository.getAllQuestionByPage(pageable);
    }

    @Override
    public Page<Question> getQuestionTitleLike(String questionTitle, int pageSize, int currentPage) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setSortParam("id")
                .setDirection(Sort.Direction.DESC).buildPage();
        return questionRepository.getQuestionTitleLike(questionTitle,pageable);
    }

    @Override
    public Page<Question> getQuestionByPageAndType(Long typeId, int pageSize, int currentPage) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setSortParam("id")
                .setDirection(Sort.Direction.DESC).buildPage();
        return questionRepository.getQuestionByPageAndType(typeId, pageable);
    }

    @Override
    public Page<Question> getQuestionTitleLikeByType(Long questionTypeId, String questionTitle,
                                                     int pageSize, int currentPage) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setSortParam("id")
                .setDirection(Sort.Direction.DESC).buildPage();
        return questionRepository.getQuestionTitleLikeByType(questionTypeId, questionTitle, pageable);
    }

    @Override
    public boolean userFollowQuestion(Long questionId, Long userId) {
        Account account = accountRepository.findOne(userId);
        if(account !=null){
            Question question = questionRepository.findOne(questionId);
            if(question!=null)
            {
                account.getFollowQuestion().add(question);
                return accountRepository.save(account) != null;
            }
        }
        return false;
    }

    @Override
    public boolean userUnFollowQuestion(Long questionId, Long userId) {
        Account account = accountRepository.findOne(userId);
        List<Question> followQuestions = account.getFollowQuestion();
        Question removedQuestion = null;
        for (Question question: account.getFollowQuestion()){
            if (question.getId().equals(questionId)){
                removedQuestion = question;
            }
        }
        if(removedQuestion != null){
            followQuestions.remove(removedQuestion);
        }
        return accountRepository.save(account) != null;
    }

    @Override
    public boolean hasFollowQuestion(Long questionId, Long userId) {
        return questionRepository.hasFollowQuestion(userId,questionId) > 0;
    }
}
