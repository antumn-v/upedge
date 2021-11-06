package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.StockOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StockOrderUpdateRequest{

    /**
     * 仓库ID
     */
    private Long warehouseId;
    /**
     * 用户ID
     */
    private Long customerId;
    /**
     * 支付方式,balance = 0,paypal=1
     */
    private Integer payMethod;
    /**
     * 交易ID
     */
    private Long paymentId;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * paypal手续费
     */
    private BigDecimal paypalFee;
    /**
     * 支付状态,待支付=0，已支付=1，取消订单=-1
     */
    private Integer payState;
    /**
     * 退款状态:0=未退款，1=申请中，2=驳回，3=部分退款，4=全部退款
     */
    private Integer refundState;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 后台状态 0:未导入赛盒 1:已导入赛盒
     */
    private Integer adminState;
    /**
     * 处理人
     */
    private String managerCode;
    /**
     * 赛盒备库订单号
     */
    private String saiheCode;

    public StockOrder toStockOrder(Long id){
        StockOrder stockOrder=new StockOrder();
        stockOrder.setId(id);
        stockOrder.setWarehouseId(warehouseId);
        stockOrder.setCustomerId(customerId);
        stockOrder.setPayMethod(payMethod);
        stockOrder.setPaymentId(paymentId);
        stockOrder.setAmount(amount);
        stockOrder.setPaypalFee(paypalFee);
        stockOrder.setPayState(payState);
        stockOrder.setRefundState(refundState);
        stockOrder.setPayTime(payTime);
        stockOrder.setCreateTime(createTime);
        stockOrder.setUpdateTime(updateTime);
        stockOrder.setAdminState(adminState);
        stockOrder.setManagerCode(managerCode);
        stockOrder.setSaiheCode(saiheCode);
        return stockOrder;
    }

}
