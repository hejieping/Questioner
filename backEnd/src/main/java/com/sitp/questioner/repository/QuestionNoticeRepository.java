package com.sitp.questioner.repository;

import com.sitp.questioner.entity.QuestionNotice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by qi on 2017/10/26.
 */

public interface QuestionNoticeRepository extends CrudRepository<QuestionNotice, Long> {
    List<QuestionNotice> getByAccount_IdAndHasRead(Long accountId, boolean hasRead);
}
