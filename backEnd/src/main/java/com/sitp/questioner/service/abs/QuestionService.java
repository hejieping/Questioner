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

    Page<Question> getAllQuestionByPage(int pageSize, int currentPage);

    boolean userFollowQuestion(Long questionId, Long userId);

    boolean userUnFollowQuestion(Long questionId, Long userId);

    boolean hasFollowQuestion(Long questionId, Long userId);
}
