package com.sitp.questioner.repository;

import com.sitp.questioner.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by qi on 2017/10/14.
 */
public interface AnswerRepository extends CrudRepository<Answer,Long>{

    @Query("select count(a.id) from Answer a where a.question.id = :questionId")
    int getAnswerNumOfQuestion(@Param("questionId") Long questionId);

    @Query(value = "select id,answer_content,answer_date_time,question_id, account_id  from answer WHERE answer.question_id = :questionId limit :start,:endIndex",
            nativeQuery = true)
    List<Answer> getLimitAnswers(@Param("questionId") Long questionId,
                                 @Param("start") int start,
                                 @Param("endIndex") int end);

    @Query(value = "select a from Answer a where question_id = :questionId")
    Page<Answer> getLimitAnswers(@Param("questionId") Long questionId, Pageable pageable);
}
