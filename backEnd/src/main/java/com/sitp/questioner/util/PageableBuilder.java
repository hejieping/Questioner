package com.sitp.questioner.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2017/10/13.
 */
public class PageableBuilder {
    private Sort.Direction direction = Sort.Direction.DESC;
    private String sortParam ;
    private int pageSize;
    private int currentPage;

    private List<Sort.Order> orders = new ArrayList<>();

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

    public PageableBuilder addSortParam(Sort.Direction direction, String sortParam) {
        orders.add(new Sort.Order(direction, sortParam));
        return this;
    }

    public  Pageable buildPage(){
        if (direction != null && sortParam != null)
            orders.add(new Sort.Order(direction, sortParam));
        return new PageRequest(currentPage, pageSize, new Sort(orders));
    }
}
