package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.Question;
import org.springframework.data.domain.Page;

/**
 * Created by qi on 2017/10/11.
 */
public interface QuestionService {
    boolean saveQuestion(Question question);

    Question getQuestion(Long questionId);

    Page<Question> getAllQuestionByPage(int pageSize, int currentPage);

}
