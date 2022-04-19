package com.upedge.sms.modules.center.vo;

import com.upedge.sms.modules.center.entity.ServiceOrder;
import lombok.Data;

@Data
public class ServiceOrderVo extends ServiceOrder {

    Object orderDetail;
}
