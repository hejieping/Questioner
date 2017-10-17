package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.AnswerComment;
import com.sitp.questioner.repository.AnswerCommentRepository;
import com.sitp.questioner.service.abs.AnswerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by qi on 2017/10/16.
 */
@Service
public class AnswerCommentServiceImpl implements AnswerCommentService{
    @Autowired
    private AnswerCommentRepository answerCommentRepository;

    @Override
    public boolean saveAnswerComment(AnswerComment answerComment) {
        answerCommentRepository.save(answerComment);
        return true;
    }

    @Override
    public Long getCommentCountOfAnswer(Long answerId) {
        return answerCommentRepository.countByAnswerId(answerId);
    }

    @Override
    public List<AnswerComment> getAllCommentsOfAnswer(Long answerId) {
        return answerCommentRepository.findByAnswerId(answerId);
    }
}
