package com.sitp.questioner.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2017/10/28.
 */
public class ReputationRecordPage {
    private List<ReputationRecord> reputationRecords = new ArrayList<>();
    private Long totalNumber;

    public List<ReputationRecord> getReputationRecords() {
        return reputationRecords;
    }

    public ReputationRecordPage setReputationRecords(List<ReputationRecord> reputationRecords) {
        this.reputationRecords = reputationRecords;
        return this;
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public ReputationRecordPage setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
        return this;
    }
}
