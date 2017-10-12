package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Question;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.service.abs.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qi on 2017/10/11.
 */
@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public boolean saveQuestion(Question question) {
        return questionRepository.save(question) != null;
    }

    @Override
    public Question getQuestion(Long questionId) {
        return questionRepository.findOne(questionId);
    }
}
