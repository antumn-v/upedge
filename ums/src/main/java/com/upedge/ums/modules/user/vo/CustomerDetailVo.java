package com.upedge.ums.modules.user.vo;

import com.upedge.common.model.store.StoreVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class CustomerDetailVo {

    Long id;

    String loginName;

    String username;

    String email;

    long loginCount;

    Date lastLoginTime;

    BigDecimal balance;

    private String whatsapp;
    /**
     *
     */
    private String wechat;
    /**
     *
     */
    private String fbInfo;
    /**
     *
     */
    private String skype;

    List<StoreVo> stores;

    Integer status;
}
