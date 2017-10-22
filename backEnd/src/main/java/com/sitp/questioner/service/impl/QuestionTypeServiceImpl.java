package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Account;
import com.sitp.questioner.entity.QuestionType;
import com.sitp.questioner.repository.AccountRepository;
import com.sitp.questioner.repository.QuestionTypeRepository;
import com.sitp.questioner.service.abs.QuestionTypeService;
import com.sitp.questioner.util.PageableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qi on 2017/10/12.
 */
@Service
public class QuestionTypeServiceImpl implements QuestionTypeService{

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Iterable<QuestionType> getAllQuestionType() {
        return questionTypeRepository.findAll();
    }

    @Override
    public boolean hasFollowQuestionType(Long questionTypeId, Long userId) {
        return questionTypeRepository.hasFollowQuestionType(userId, questionTypeId) > 0;
    }

    @Override
    public boolean followQuestionType(Long questionTypeId, Long userId) {
        Account account = accountRepository.findOne(userId);
        if (account != null) {
            QuestionType questionType = questionTypeRepository.findOne(questionTypeId);
            if (questionType != null) {
                account.getFollowQuestionType().add(questionType);
                accountRepository.save(account);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean unFollowQuestionType(Long questionTypeId, Long userId) {
        Account account = accountRepository.findOne(userId);
        if (account != null) {
            List<QuestionType> questionTypes = account.getFollowQuestionType();
           questionTypes.removeIf(questionType -> questionType.getId().equals(questionTypeId));
           accountRepository.save(account);
           return true;
        }
        return false;
    }

    @Override
    public Page<QuestionType> getUserFollowQuestionType(Long userId, int currentPage, int pageSize) {
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setDirection(Sort.Direction.DESC).setSortParam("id").buildPage();
        return questionTypeRepository.getUserFollowQuestionType(userId, pageable);
    }
}
