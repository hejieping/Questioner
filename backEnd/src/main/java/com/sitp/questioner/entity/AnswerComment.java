package com.sitp.questioner.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by qi on 2017/10/16.
 */
@Entity
public class AnswerComment {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String commentContent;

    @Column
    private Date commentDateTime = new Date();

    @Column(name = "thumbs_up_count")
    private Long thumbsUpCount = 0L;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account commenter;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    @JsonIgnore
    private Answer answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(Date commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public Long getThumbsUpCount() {
        return thumbsUpCount;
    }

    public void setThumbsUpCount(Long thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    public Account getCommenter() {
        return commenter;
    }

    public void setCommenter(Account commenter) {
        this.commenter = commenter;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
