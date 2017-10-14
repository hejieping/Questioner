package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.Answer;

import java.util.List;

/**
 * Created by qi on 2017/10/14.
 */
public interface AnswerService {

    int getAnswerNumOfQuestion(Long questionId);

    List<Answer> getLimitAnswers(Long questionId, int startIndex, int limitNum);

    boolean saveAnswer(Answer answer);
}
