package com.sitp.questioner.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by qi on 2017/10/13.
 */
public class PageableBuilder {
    private Sort.Direction direction;
    private String sortParam ;
    private int pageSize;
    private int currentPage;

    public PageableBuilder setDirection(Sort.Direction direction) {
        this.direction = direction;
        return this;
    }

    public PageableBuilder setSortParam(String sortParam) {
        this.sortParam = sortParam;
        return this;
    }

    public PageableBuilder setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageableBuilder setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public  Pageable buildPage(){
        Sort sort = new Sort(direction, sortParam);
        return new PageRequest(currentPage, pageSize, sort);
    }
}
