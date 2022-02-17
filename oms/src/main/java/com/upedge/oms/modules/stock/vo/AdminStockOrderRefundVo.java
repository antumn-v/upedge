package com.upedge.oms.modules.stock.vo;

import com.upedge.oms.modules.stock.entity.StockOrderRefundItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AdminStockOrderRefundVo {


    /**
     *
     */
    private Long id;
    /**
     * 备库订单ID
     */
    private Long stockOrderId;
    /**
     * 退款总金额
     */
    private BigDecimal amount;
    /**
     * 退款状态，申请中=0，通过=1，驳回=2
     */
    private Integer state;
    /**
     * 退款原因
     */
    private String reason;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     * 0:app,1:admin
     */
    private Integer source;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private String managerCode;
    /**
     *
     */
    private Integer warehouseCode;

    private List<StockOrderRefundItem> items;
}
