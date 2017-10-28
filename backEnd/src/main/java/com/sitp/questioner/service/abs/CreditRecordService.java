package com.sitp.questioner.service.abs;


import com.sitp.questioner.entity.CreditRecord;
import org.springframework.data.domain.Page;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/10/23 上午10:49
 */
public interface CreditRecordService {
    CreditRecord save(CreditRecord creditRecord);

    Page<CreditRecord> getUserCreditRecordPageable(Long userId, int currentPage, int pageSize);
}
