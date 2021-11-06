package com.upedge.thirdparty.trackingmore.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by cjq on 2019/5/8.
 */
@Data
public class PostTracking {

    //运单号
    String tracking_number;
    //运输商简码
    String carrier_code;
    //	目的国二字简码.查看国际国家二字简码
    String destination_code;// (optional)
    //	商品标题
    String title;// (optional)
    //物流渠道
    String logistics_channel;//(optional)
    //客户名称.
    String customer_name;//(optional)
    //客户邮箱.
    String customer_email;//(optional)
    //客户电话号码.
    String customer_phone ;//(optional)
    //订单号.
    String order_id;//(optional)
    //下单时间(eg:2017/8/27 16:51).
    String order_create_time;//(optional)
    //寄件日期，格式为20181001（年月日），部分运输商（如德国邮政）要求填写发件日期才可以查询物流信息，故查询此类运输商时需要传此参数.
    String tracking_ship_date;//(optional)
    //收件地邮编，部分运输商（如Mondial Relay法国快递）要求填写邮编才可以查询物流信息，故查询此类运输商时需要传该参数.
    String tracking_postal_code;//(optional)
    //账户号，部分快递要求填写账户号才可以查询物流，故查询此类快递时需要传该参数.
    String tracking_account_number;//(optional)
    //目的国参数，部分运输商要求填写目的国家才可以查询物流，故查询此类快递时需要传该参数.
    String tracking_destination_country;//(optional)
    //返回语言类型 (仅当快递官网支持时有用)
    String lang;//(optional)
    //如果将值设置为 1, 系统将不会根据单号自动矫正快递商.
    String auto_correct;//(optional)
    //备注
    String comment;// (optional)

    Date shipTime;

}
