package com.sitp.questioner.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2017/10/12.
 */

@Entity
public class QuestionType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String course;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "QuestionType{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
