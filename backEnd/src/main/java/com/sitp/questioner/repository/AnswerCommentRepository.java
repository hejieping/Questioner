package com.sitp.questioner.repository;

import com.sitp.questioner.entity.AnswerComment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by qi on 2017/10/16.
 */
public interface AnswerCommentRepository extends CrudRepository<AnswerComment, Long>{
    Long countByAnswerId(Long answerId);

    List<AnswerComment> findByAnswerId(Long answerId);
}
