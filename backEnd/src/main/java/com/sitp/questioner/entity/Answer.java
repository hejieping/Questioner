package com.sitp.questioner.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by qi on 2017/10/14.
 */
@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String answerContent;

    @Column
    private Date answerDateTime = new Date();

    @Column(name = "thumbs_up_count")
    private Long thumbsUpCount = 0L;

    @Column(name = "thumbs_down_count")
    private Long thumbsDownCount = 0L;

    @Column
    private Boolean isAccepted = false;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {
            @JoinColumn(name = "answer_id", referencedColumnName = "id")
    },inverseJoinColumns = {
            @JoinColumn(name="feedback_account_id", referencedColumnName = "id" )
    })
    @JsonIgnore
    private List<Account> feedbackAccounts = new ArrayList<>();

    @JoinColumn(name = "question_id")
    @ManyToOne
    @JsonIgnore
    private Question question;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;

    @OneToMany
    @JsonIgnore
    private List<AnswerComment> answerComments = new ArrayList<>();

    /*
    @OneToMany
    @JsonIgnore
    private List<QuestionNotice> questionNotices = new ArrayList<>();
*/

    @Transient
    private Long commentCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Date getAnswerDateTime() {
        return answerDateTime;
    }

    public void setAnswerDateTime(Date answerDateTime) {
        this.answerDateTime = answerDateTime;
    }

    public Long getThumbsUpCount() {
        return thumbsUpCount;
    }

    public void setThumbsUpCount(Long thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    public Long getThumbsDownCount() {
        return thumbsDownCount;
    }

    public void setThumbsDownCount(Long thumbsDownCount) {
        this.thumbsDownCount = thumbsDownCount;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<AnswerComment> getAnswerComments() {
        return answerComments;
    }

    public void setAnswerComments(List<AnswerComment> answerComments) {
        this.answerComments = answerComments;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public List<Account> getFeedbackAccounts() {
        return feedbackAccounts;
    }

    public void setFeedbackAccounts(List<Account> feedbackAccounts) {
        this.feedbackAccounts = feedbackAccounts;
    }

/*    public List<QuestionNotice> getQuestionNotices() {
        return questionNotices;
    }

    public void setQuestionNotices(List<QuestionNotice> questionNotices) {
        this.questionNotices = questionNotices;
    }
*/
    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", answerConten='" + answerContent + '\'' +
                ", answerDateTime=" + answerDateTime +
                ", question=" + question +
                '}';
    }
}
