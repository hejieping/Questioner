package com.sitp.questioner.repository;

import java.util.List;

import com.sitp.questioner.entity.CreditRecord;
import org.springframework.data.repository.CrudRepository;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/10/23 上午10:38
 */
public interface CreditRecordRepository extends CrudRepository<CreditRecord, Long> {
    CreditRecord save(CreditRecord creditRecord);
    List<CreditRecord> findByAnswerid(Long answerid);
}
