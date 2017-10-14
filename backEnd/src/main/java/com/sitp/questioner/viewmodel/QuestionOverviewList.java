package com.sitp.questioner.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2017/10/13.
 */
public class QuestionOverviewList {
    private int currentPage ;
    private int totalPage ;
    private boolean hasPre ;
    private boolean hasNext;
    private int totalQuestions;
    private List<QuestionOverview> questionOverviewList = new ArrayList<>();

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Boolean getHasPre() {
        return hasPre;
    }

    public void setHasPre(Boolean hasPre) {
        this.hasPre = hasPre;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<QuestionOverview> getQuestionOverviewList() {
        return questionOverviewList;
    }

    public void setQuestionOverviewList(List<QuestionOverview> questionOverviewList) {
        this.questionOverviewList = questionOverviewList;
    }

    @Override
    public String toString() {
        return "QuestionOverviewList{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", hasPre=" + hasPre +
                ", hasNext=" + hasNext +
                ", totalQuestions=" + totalQuestions +
                ", questionOverviewList=" + questionOverviewList +
                '}';
    }
}
