package com.sitp.questioner.service.abs;

import java.util.List;

import com.sitp.questioner.entity.CreditRecord;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/10/23 上午10:49
 */
public interface CreditRecordService {
    CreditRecord save(CreditRecord creditRecord);
    List<CreditRecord> getCreditRecords(Long answerId);
}
