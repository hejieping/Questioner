package com.sitp.questioner.entity;

import javax.persistence.*;

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
