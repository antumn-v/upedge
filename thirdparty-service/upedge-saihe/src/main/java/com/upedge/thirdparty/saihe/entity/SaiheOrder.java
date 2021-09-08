package com.upedge.thirdparty.saihe.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SaiheOrder {

    private Long customerId;
    /**
     *
     */
    private String managerCode;

    /**
     * 赛盒订单来源渠道ID
     */
    private Integer orderSourceID;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 客户订单号
     */
    private String clientOrderCode;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 交易id
     */
    private String transactionId;
    /**
     * 币种
     */
    private String currency="USD";
    /**
     * 订单总金额
     */
    private BigDecimal totalPrice;
    /**
     * 运费收入
     */
    private BigDecimal transportPay;
    /**
     * 买家留言
     */
    private String orderDescription;
    /**
     * 买家姓氏
     */
    private String firstName;
    /**
     * 买家名字
     */
    private String lastName;
    /**
     * 收货国家
     */
    private String country;
    /**
     * 收货省份
     */
    private String province;
    /**
     * 收货城市
     */
    private String city;
    /**
     * 邮编
     */
    private String postalCode;
    /**
     * 电话
     */
    private String telephone;
    /**
     * 收货地址一
     */
    private String address1;
    /**
     * 收货地址二
     */
    private String address2;
    /**
     * 订单产品列表
     */
    private List<SaiheOrderItem> orderItemList =new ArrayList<>();
    /**
     * 映射的赛盒运输方式
     */
    private Integer transportID;

    //==============================================================

    private Long shipMethodId;

    private String orderName;

    private Integer orderType;

    private BigDecimal vatAmount;

    private String discountAmount;

    private String shippingMethod;

    private String countryCode;

    private String addrName;

    private String provinceCode;

    private Integer payMethod;


}
