package com.sitp.questioner.service.abs;

import java.util.List;

import com.sitp.questioner.entity.Answer;
import org.springframework.data.domain.Page;

/**
 * Created by qi on 2017/10/14.
 */
public interface AnswerService {

    int getAnswerNumOfQuestion(Long questionId);

    List<Answer> getLimitAnswers(Long questionId, int startIndex, int limitNum, String sortParam);

    boolean saveAnswer(Answer answer);

    boolean userHasFeedback(Long answerId, Long userId);

    boolean giveAnswerFeedBack(Long answerId, Long userId, boolean isGood);

    Long getQuestionPublisherByAnswer(Long answerId);

    boolean acceptAnswer(Long answerId);

    Page<Answer> getUserAnswersByDateTime(Long userId, int currentPage, int pageSize);

    Page<Answer> getUserAnswerByThumbsUpCount(Long userId, int currentPage, int pageSize);

    Answer getAnswer(Long answerId);

    Long getUserAnswerCount(Long userId);

    Answer save(Answer answer);
}
