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

    @Column(unique = true)
    private String loginUsername; // the username that user input just for login
    @Column(nullable = false,unique = true)
    private String username; // the username that showed to other users which means it's more friendly and readable
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String avatarURL;
    @Column(columnDefinition = "TEXT")
    private String profile;

    @Column
    private Long creditPoint = 0L;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="account_role",joinColumns = {
            @JoinColumn(name = "account_id",referencedColumnName = "id"),
    },inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private List<Role> roles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    /*
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<QuestionNotice> questionNotices = new ArrayList<>();
*/

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

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "follower_followed", joinColumns = @JoinColumn(name = "followed_id"),
    inverseJoinColumns = @JoinColumn(name = "follower_id"))
    @JsonIgnore
    private List <Account> followers = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "follower_followed",joinColumns = @JoinColumn(name = "follower_id"),
    inverseJoinColumns = @JoinColumn(name = "followed_id"))
    @JsonIgnore
    private List<Account> followed = new ArrayList<>();


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

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public Long getCreditPoint() {
        return creditPoint;
    }

    public void setCreditPoint(Long creditPoint) {
        this.creditPoint = creditPoint;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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


    public List<Account> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Account> followers) {
        this.followers = followers;
    }

    public List<Account> getFollowed() {
        return followed;
    }

    public void setFollowed(List<Account> followed) {
        this.followed = followed;
    }

/*
    public List<QuestionNotice> getQuestionNotices() {
        return questionNotices;
    }

    public void setQuestionNotices(List<QuestionNotice> questionNotices) {
        this.questionNotices = questionNotices;
    }
*/
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
