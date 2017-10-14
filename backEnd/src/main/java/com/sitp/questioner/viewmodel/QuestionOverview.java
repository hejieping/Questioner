package com.sitp.questioner.viewmodel;


import java.time.LocalDateTime;

/**
 * Created by qi on 2017/10/13.
 */
public class QuestionOverview {
    private String title ;
    private String subject;
    private String course ;
    private LocalDateTime publishDateTime ;
    private Integer Answers ;
    private Boolean isSolved ;
    private String publisherName ;
    private Long publisherId;
    private String publisherImgSrc;

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

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(LocalDateTime publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public Integer getAnswers() {
        return Answers;
    }

    public void setAnswers(Integer answers) {
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
