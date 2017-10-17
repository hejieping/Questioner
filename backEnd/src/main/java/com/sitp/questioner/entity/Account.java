package com.sitp.questioner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by jieping on 2017-07-08.
 */
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String avatarURL;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="account_role",joinColumns = {
            @JoinColumn(name = "account_id",referencedColumnName = "id"),
    },inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {
          @JoinColumn(name = "account_id",referencedColumnName = "id")
    },inverseJoinColumns = {
            @JoinColumn(name = "question_id", referencedColumnName = "id")
    })
    @JsonIgnore
    private List<Question> followQuestion = new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(joinColumns = {
            @JoinColumn(name = "account_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "question_type_id", referencedColumnName = "id")
    } )
    @JsonIgnore
    private List<QuestionType> followQuestionType = new ArrayList<>();



    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AnswerComment> answerComments = new ArrayList<>();

    @ManyToMany(mappedBy = "feedbackAccounts", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Answer> feedbackAnswers = new ArrayList<>();


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Question> getFollowQuestion() {
        return followQuestion;
    }

    public void setFollowQuestion(List<Question> followQuestion) {
        this.followQuestion = followQuestion;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<AnswerComment> getAnswerComments() {
        return answerComments;
    }

    public void setAnswerComments(List<AnswerComment> answerComments) {
        this.answerComments = answerComments;
    }

    public List<Answer> getFeedbackAnswers() {
        return feedbackAnswers;
    }

    public void setFeedbackAnswers(List<Answer> feedbackAnswers) {
        this.feedbackAnswers = feedbackAnswers;
    }


    public List<QuestionType> getFollowQuestionType() {
        return followQuestionType;
    }

    public void setFollowQuestionType(List<QuestionType> followQuestionType) {
        this.followQuestionType = followQuestionType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
