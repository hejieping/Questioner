package com.sitp.questioner.repository;

import java.util.List;

import com.sitp.questioner.entity.CreditRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/10/23 上午10:38
 */
public interface CreditRecordRepository extends CrudRepository<CreditRecord, Long> {
    @Query("select c from CreditRecord c join c.answer.account u where u.id =?1")
    Page<CreditRecord> getUserCreditRecordsPageable(Long userId, Pageable pageable);
}
