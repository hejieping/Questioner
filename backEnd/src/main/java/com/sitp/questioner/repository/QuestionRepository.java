package com.sitp.questioner.repository;

import com.sitp.questioner.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by qi on 2017/10/11.
 */
public interface QuestionRepository extends CrudRepository<Question,Long>{
    @Query("select q from Question as q")
    Page<Question>  getAllQuestionByPage(Pageable pageable);

    @Query("select count(a.id) from Answer a where a.question.id = :questionId")
    Long getTotalAnswerOfQuestion(@Param("questionId") Long questionId);

    @Query("select count(a.id) from Account a join a.followQuestion q where a.id = :userId and q.id = :questionId")
    int hasFollowQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId);
}
