package com.upedge.oms.modules.statistics.request;

import com.upedge.oms.modules.statistics.entity.OrderDailyPayCount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderDailyPayCountUpdateRequest{

    /**
     * 客户ID
     */
    private Long customerId;
    /**
     * 客户经理ID
     */
    private String managerCode;
    /**
     * 订单类型  备库 = 1，普通 = 2，批发 = 3
     */
    private Integer orderType;
    /**
     * 运费
     */
    private BigDecimal shipPrice;
    /**
     * 产品费
     */
    private BigDecimal productAmount;
    /**
     * 库存抵扣金额
     */
    private BigDecimal productDischargeAmount;
    /**
     * paypal手续费
     */
    private BigDecimal fixFee;
    /**
     * 
     */
    private BigDecimal vatAmount;
    /**
     * 支付金额：product_amount + ship_price + vat + fix_fee - discharge
     */
    private BigDecimal payAmount;
    /**
     * 入账金额：product_amount + ship_price + vat
     */
    private BigDecimal creditAmount;
    /**
     * 支付时间，每天统计一次
     */
    private Date payTime;

    public OrderDailyPayCount toOrderDailyPayCount(Long id){
        OrderDailyPayCount orderDailyPayCount=new OrderDailyPayCount();
        orderDailyPayCount.setId(id);
        orderDailyPayCount.setCustomerId(customerId);
        orderDailyPayCount.setManagerCode(managerCode);
        orderDailyPayCount.setOrderType(orderType);
        orderDailyPayCount.setShipPrice(shipPrice);
        orderDailyPayCount.setProductAmount(productAmount);
        orderDailyPayCount.setProductDischargeAmount(productDischargeAmount);
        orderDailyPayCount.setFixFee(fixFee);
        orderDailyPayCount.setVatAmount(vatAmount);
        orderDailyPayCount.setPayAmount(payAmount);
        orderDailyPayCount.setCreditAmount(creditAmount);
        orderDailyPayCount.setPayTime(payTime);
        return orderDailyPayCount;
    }

}
