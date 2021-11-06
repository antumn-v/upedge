package com.upedge.common.model.old;


import lombok.Data;

@Data
public class RedisPage {

    String method;

    long count;

    int pageSize = 1000;

    int totalPage;

    int currentPage = 1;

    public RedisPage() {
    }

    public RedisPage(String method, Long count, Integer pageSize, Integer totalPage, Integer currentPage) {
        this.method = method;
        this.count = count;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public void addCurrentPage(){
        this.currentPage ++;
    }


}
