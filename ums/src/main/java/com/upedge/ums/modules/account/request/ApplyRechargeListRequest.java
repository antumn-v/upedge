package com.upedge.ums.modules.account.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 海桐
 */
@Data
public class ApplyRechargeListRequest extends Page<RechargeRequestLog> {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date endTime;

    /**
     * 客户姓名
     */
    String name;



}
