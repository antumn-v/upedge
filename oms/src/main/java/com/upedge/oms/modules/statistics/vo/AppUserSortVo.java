package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cjq on 2019/7/11.
 */
@Data
public class AppUserSortVo  {

    /**
     * 排序类型
     */
    Integer sortType;


    /**
     * 客户id
     */
    Long customerId;

    /**
     * 客户名称
     */
    String CustomerName;

    /**
     * 客户账号
     */
    String email;

    /**
     * 注册日期
     */
    Date registerDate;

    /**
     * 客户经理
     */
    String managerCode;

    /**
     * 下单数
     */
    Integer orderPaidNum;

    /**
     * 下单金额
     */
    BigDecimal orderAmount;
}
