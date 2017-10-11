package com.sitp.questioner.repository;

import com.sitp.questioner.entity.Question;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by qi on 2017/10/11.
 */
public interface QuestionRepository extends CrudRepository<Question,Long>{

}
