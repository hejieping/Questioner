package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Question;
import com.sitp.questioner.repository.QuestionRepository;
import com.sitp.questioner.service.abs.QuestionService;
import com.sitp.questioner.util.PageableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Question> getAllQuestionByPage(int pageSize, int currentPage){
        Pageable pageable = new PageableBuilder().setCurrentPage(currentPage)
                .setPageSize(pageSize).setSortParam("id")
                .setDirection(Sort.Direction.DESC).buildPage();
        return questionRepository.getAllQuestionByPage(pageable);
    }
}
