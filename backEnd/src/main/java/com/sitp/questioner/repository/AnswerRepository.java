package com.sitp.questioner.repository;

import java.util.List;

import com.sitp.questioner.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by qi on 2017/10/14.
 */
public interface AnswerRepository extends CrudRepository<Answer,Long>{

    @Query("select count(a.id) from Answer a where a.question.id = :questionId")
    int getAnswerNumOfQuestion(@Param("questionId") Long questionId);



    @Query(value = "select answer.*  from answer WHERE answer.question_id = :questionId ORDER BY id DESC limit :start,:limit",
            nativeQuery = true)
    List<Answer> getLimitAnswersOrderById(@Param("questionId") Long questionId,
                                 @Param("start") int start,
                                 @Param("limit") int limit);

    @Query(value = "select answer.*  from answer WHERE answer.question_id = :questionId ORDER BY thumbs_up_count DESC, id DESC limit :start,:limit",
            nativeQuery = true)
    List<Answer> getLimitAnswersOrderByDefault(@Param("questionId") Long questionId,
                                          @Param("start") int start,
                                          @Param("limit") int limit);

    @Query(value = "select count(1) FROM answer_feedback_accounts WHERE answer_id =:answer_id AND feedback_account_id = :account_id LIMIT 1",
            nativeQuery = true)
    int hasFeedBack(@Param("answer_id") Long answerId, @Param("account_id") Long accountId);

    @Query(value = "SELECT question.account_id FROM answer, question WHERE answer.question_id = question.id AND answer.id = ?1",
            nativeQuery = true)
    Long getQuestionPublisherByAnswer(Long answerId);

    @Query("select a from Answer a where a.account.id = ?1")
    Page<Answer> getUserAnswers(Long userId, Pageable pageable);

    Long countByAccountId(Long accountId);

    Answer save(Answer answer);

    /*
    @Query(" select a.id, a.answerDateTime, a.thumbsUpCount, a.thumbsDownCount, a.accepted, a.question.id, a.question.questionTitle " +
            " from Answer  a where a.account.id = ?1")
    Page<AnswerOverview> getUserAnswersOverview(Long userId, Pageable pageable);
    */

}
