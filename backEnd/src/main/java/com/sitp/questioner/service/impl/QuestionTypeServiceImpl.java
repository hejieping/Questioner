package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.QuestionType;
import com.sitp.questioner.repository.QuestionTypeRepository;
import com.sitp.questioner.service.abs.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Iterable<QuestionType> getAllQuestionType() {
        return questionTypeRepository.findAll();
    }

    @Override
    public void followQuestionType(Long questionTypeId, Long userId) {
        QuestionType questionType = questionTypeRepository.findOne(questionTypeId);
    }
}
