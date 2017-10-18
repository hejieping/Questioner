package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.QuestionType;

/**
 * Created by qi on 2017/10/12.
 */
public interface QuestionTypeService {
    Iterable<QuestionType> getAllQuestionType();

    boolean hasFollowQuestionType(Long questionTypeId, Long userId);

    boolean followQuestionType(Long questionTypeId, Long userId);

    boolean unFollowQuestionType(Long questionTypeId, Long userId);
}
