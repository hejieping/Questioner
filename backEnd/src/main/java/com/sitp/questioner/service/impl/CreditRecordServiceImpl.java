package com.sitp.questioner.service.impl;

import java.util.List;

import com.sitp.questioner.entity.CreditRecord;
import com.sitp.questioner.repository.CreditRecordRepository;
import com.sitp.questioner.service.abs.CreditRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/10/23 上午10:49
 */
@Service
public class CreditRecordServiceImpl implements CreditRecordService {
    @Autowired
    private  CreditRecordRepository creditRecordRepository;
    @Override
    public CreditRecord save(CreditRecord creditRecord) {
        return creditRecordRepository.save(creditRecord);
    }

    @Override
    public List<CreditRecord> getCreditRecords(Long answerId) {
        return creditRecordRepository.findByAnswerid(answerId);
    }
}
