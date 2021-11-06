package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class StoreOrderVo {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String platOrderId;
    /**
     *
     */
    private String platOrderName;
    /**
     *
     */
    private Long storeId;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private Date importTime;

    private List<StoreOrderItemVo> items=new ArrayList<>();

}
