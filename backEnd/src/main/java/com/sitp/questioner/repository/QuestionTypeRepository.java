package com.sitp.questioner.repository;

import java.util.List;

import com.sitp.questioner.entity.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by qi on 2017/10/12.
 */
public interface QuestionTypeRepository extends CrudRepository<QuestionType,Long> {
    @Query(value = "SELECT COUNT(1) FROM account_follow_question_type WHERE account_id = ?1 AND question_type_id = ?2 limit 1"
            ,nativeQuery = true)
    int hasFollowQuestionType(Long userId, Long questionTypeId);


    @Query("select q from QuestionType q join q.followers f where f.id = ?1")
    Page<QuestionType> getUserFollowQuestionType(Long userId, Pageable pageable);
    List<QuestionType> findByIdIn(List<Long> idList);
}
