package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.Question;

/**
 * Created by qi on 2017/10/11.
 */
public interface QuestionService {
    boolean saveQuestion(Question question);

    Question getQuestion(Long questionId);
}
