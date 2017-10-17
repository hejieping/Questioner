package com.sitp.questioner.viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2017/10/13.
 */
public class QuestionOverviewList {
    private Long totalNumber;
    private int currentPage ;
    private int totalPage ;
    private boolean hasPre ;
    private boolean hasNext;
    private List<QuestionOverview> questionOverviewList = new ArrayList<>();

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }

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


    public List<QuestionOverview> getQuestionOverviewList() {
        return questionOverviewList;
    }

    public void setQuestionOverviewList(List<QuestionOverview> questionOverviewList) {
        this.questionOverviewList = questionOverviewList;
    }

    public void addQuestionOverview(QuestionOverview questionOverview) {
        this.questionOverviewList.add(questionOverview);
    }

    @Override
    public String toString() {
        return "QuestionOverviewList{" +
                "currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", hasPre=" + hasPre +
                ", hasNext=" + hasNext +
                ", totalNumber=" + totalNumber +
                ", questionOverviewList=" + questionOverviewList +
                '}';
    }
}
