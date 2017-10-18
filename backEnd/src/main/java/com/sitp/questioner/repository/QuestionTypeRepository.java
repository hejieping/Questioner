package com.sitp.questioner.repository;

import com.sitp.questioner.entity.QuestionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by qi on 2017/10/12.
 */
public interface QuestionTypeRepository extends CrudRepository<QuestionType,Long> {
    @Query(value = "SELECT COUNT(1) FROM account_follow_question_type WHERE account_id = ?1 AND question_type_id = ?2 limit 1"
            ,nativeQuery = true)
    int hasFollowQuestionType(Long userId, Long questionTypeId);
}
