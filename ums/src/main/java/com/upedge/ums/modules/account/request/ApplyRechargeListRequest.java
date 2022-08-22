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



    /**
     * 客户姓名
     */
    String name;



}
