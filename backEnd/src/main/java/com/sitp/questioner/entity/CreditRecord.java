package com.sitp.questioner.entity;

import java.util.Date;

import javax.persistence.*;

import com.alibaba.fastjson.JSON;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/10/23 上午10:33
 */
@Entity
public class CreditRecord {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 声望变动的类型
     */
    @Column(nullable = false)
    private String type;
    /**
     * 答案id
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    /**
     * 日期
     */
    @Column(nullable = false)
    private Date datetime;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString(){
        return  JSON.toJSONString(this);

    }
}
