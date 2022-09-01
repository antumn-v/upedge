package com.upedge.ums.modules.user.vo;

import com.upedge.common.model.store.StoreVo;
import com.upedge.ums.modules.user.entity.CustomerSetting;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    BigDecimal affiliateRebate;

    BigDecimal vipRebate;

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

    private List<StoreVo> stores = new ArrayList<>();

    Integer status;

    String remark;

    Integer vipLevel;

    private List<CustomerSetting> customerSettings = new ArrayList<>();
}
