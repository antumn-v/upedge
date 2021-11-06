package com.upedge.ums.modules.account.entity;

/**
 * @author 海桐
 */
public class PayMethod extends PaymethodDetail {

    private Integer id;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}