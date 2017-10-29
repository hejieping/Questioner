package com.sitp.questioner.viewmodel;

import java.util.Date;

/**
 * Created by qi on 2017/10/28.
 */
public class ReputationRecord {
    private String reputationType;
    private String questionTitle;
    private Long questionId;
    private Long answerId;
    private Date feedbackDateTime;

    public String getReputationType() {
        return reputationType;
    }

    public ReputationRecord setReputationType(String reputationType) {
        this.reputationType = reputationType;
        return this;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public ReputationRecord setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
        return this;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public ReputationRecord setQuestionId(Long questionId) {
        this.questionId = questionId;
        return this;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public ReputationRecord setAnswerId(Long answerId) {
        this.answerId = answerId;
        return this;
    }

    public Date getFeedbackDateTime() {
        return feedbackDateTime;
    }

    public ReputationRecord setFeedbackDateTime(Date feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
        return this;
    }
}
