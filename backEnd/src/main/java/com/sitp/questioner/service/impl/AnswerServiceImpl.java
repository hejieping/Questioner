package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Answer;
import com.sitp.questioner.repository.AnswerRepository;
import com.sitp.questioner.service.abs.AnswerService;
import com.sitp.questioner.util.PageableBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qi on 2017/10/14.
 */
@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public int getAnswerNumOfQuestion(Long questionId) {
        return answerRepository.getAnswerNumOfQuestion(questionId);
    }

    @Override
    public List<Answer> getLimitAnswers(Long questionId, int startIndex, int limitNum) {
    /*    Pageable pageable = new PageableBuilder().setCurrentPage(startIndex / 10)
                .setPageSize(limitNum).setDirection(Sort.Direction.DESC)
                .setSortParam("id").buildPage();
        return answerRepository.getLimitAnswers(questionId,pageable).getContent();
        */
        int end = startIndex + limitNum;
        return answerRepository.getLimitAnswers(questionId, startIndex, end);
    }

    @Override
    public boolean saveAnswer(Answer answer) {
        return answerRepository.save(answer) != null;
    }
}
