package com.sitp.questioner.repository;

import com.sitp.questioner.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by qi on 2017/10/11.
 */
public interface QuestionRepository extends CrudRepository<Question,Long>{
    @Query("select q from Question as q")
    Page<Question>  getAllQuestionByPage(Pageable pageable);
}
