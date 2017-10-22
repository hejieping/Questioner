package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.Question;
import org.springframework.data.domain.Page;

/**
 * Created by qi on 2017/10/11.
 */
public interface QuestionService {
    boolean saveQuestion(Question question);

    Long getAnswerNumber(Long questionId);

    Question getQuestion(Long questionId);

    Page<Question> getAllQuestionByPage(int pageSize, int currentPage,String sortParam);

    Page<Question> getQuestionTitleLike(String questionTitle, int pageSize, int currentPage, String sortParam);

    Page<Question> getQuestionByPageAndType(Long typeId, int pageSize, int currentPage, String sortParam);

    Page<Question> getQuestionTitleLikeByType(Long questionTypeId, String questionTitle,
                                              int pageSize, int currentPage, String sortParam);

    boolean userFollowQuestion(Long questionId, Long userId);

    boolean userUnFollowQuestion(Long questionId, Long userId);

    boolean hasFollowQuestion(Long questionId, Long userId);

    Page<Question> getUserFollowQuestions(Long userId, int currentPage, int pageSize);

    Page<Question> getUserQuestionByViews(Long userId, int currentPage, int pageSize);

    Page<Question> getUserQuestionByDateTime(Long userId, int currentPage, int pageSize);

    Question getQuestionByAnswerId(Long answerId);

    Long getUserQuestionCount(Long userId);
}
