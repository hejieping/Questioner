package com.sitp.questioner.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

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

    @JoinColumn(name = "question_id")
    @ManyToOne
    private Question question;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;

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
