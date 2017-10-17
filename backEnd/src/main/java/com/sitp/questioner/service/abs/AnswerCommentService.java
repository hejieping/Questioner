package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.AnswerComment;

import java.util.List;

/**
 * Created by qi on 2017/10/16.
 */
public interface AnswerCommentService {
    boolean saveAnswerComment(AnswerComment answerComment);

    Long getCommentCountOfAnswer(Long answerId);

    List<AnswerComment> getAllCommentsOfAnswer(Long answerId);
}
