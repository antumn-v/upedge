package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class SameAddressOrderVo {

    Long storeId;

    String address;

    List<Long> orderIds;

    List<AppOrderVo> appOrderVos;
}
