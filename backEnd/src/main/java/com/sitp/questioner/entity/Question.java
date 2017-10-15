package com.sitp.questioner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by qi on 2017/10/11.
 */
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "questionTitle")
    private String questionTitle;

    @Column(name = "questionContent", columnDefinition="TEXT")
    private String questionContent;

    @Column(name = "questionContentTxt", columnDefinition = "TEXT")
    private String questionContentTxt; // plain text of the question remove the format

    @JoinColumn(name = "type_id")
    @ManyToOne
    private QuestionType questionType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany(mappedBy = "followQuestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Account> followers = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionContentTxt() {
        return questionContentTxt;
    }

    public void setQuestionContentTxt(String questionContentTxt) {
        this.questionContentTxt = questionContentTxt;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Account> followers) {
        this.followers = followers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", questionContentTxt='" + questionContentTxt + '\'' +
                ", questionType=" + questionType +
                '}';
    }
}
