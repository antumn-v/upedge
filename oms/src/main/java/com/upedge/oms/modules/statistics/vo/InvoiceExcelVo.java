package com.upedge.oms.modules.statistics.vo;

import com.upedge.common.model.user.vo.AddressVo;
import lombok.Data;

@Data
public class InvoiceExcelVo {


    /**
     * 账单地址
     */
    private AddressVo addressVo;

    /**
     * 普通订单
     */
    private InvoiceDetailVo Normal;

    /**
     * 库存订单
     */
    private InvoiceDetailVo stockOrderInvoice;

    /**
     * 批发订单
     */
    private InvoiceDetailVo wholesaleOrderInvoice;
}
