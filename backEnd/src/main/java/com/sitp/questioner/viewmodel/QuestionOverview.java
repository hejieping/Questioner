package com.sitp.questioner.viewmodel;


import java.util.Date;

/**
 * Created by qi on 2017/10/13.
 */
public class QuestionOverview {
    private Long id;
    private String title ;
    private String subject;
    private String course ;
    private Date publishDateTime ;
    private Long Answers ;
    private Boolean isSolved ;
    private String publisherName ;
    private Long publisherId;
    private String publisherImgSrc;
    private Long views ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(Date publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public Long getAnswers() {
        return Answers;
    }

    public void setAnswers(Long answers) {
        Answers = answers;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherImgSrc() {
        return publisherImgSrc;
    }

    public void setPublisherImgSrc(String publisherImgSrc) {
        this.publisherImgSrc = publisherImgSrc;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "QuestionOverview{" +
                "title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", course='" + course + '\'' +
                ", publishDateTime=" + publishDateTime +
                ", Answers=" + Answers +
                ", isSolved=" + isSolved +
                ", publisherName='" + publisherName + '\'' +
                ", publisherId=" + publisherId +
                ", publisherImgSrc='" + publisherImgSrc + '\'' +
                '}';
    }
}
