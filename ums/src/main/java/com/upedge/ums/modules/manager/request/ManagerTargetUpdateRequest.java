package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerTarget;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class ManagerTargetUpdateRequest{

    /**
     * 
     */
    private String managerCode;
    /**
     * 目标销售额
     */
    private BigDecimal targetSalesAmount;
    /**
     * 目标客户下单数
     */
    private Integer targetCustomerPlaceOrder;
    /**
     * 实际销售额
     */
    private BigDecimal actualSalesAmount;
    /**
     * 实际下单客户
     */
    private Integer actualCustomerPlaceOrder;
    /**
     * 日期月份
     */
    private String dateMonth;

    public ManagerTarget toManagerTarget(Integer id){
        ManagerTarget managerTarget=new ManagerTarget();
        managerTarget.setId(id);
        managerTarget.setManagerCode(managerCode);
        managerTarget.setTargetSalesAmount(targetSalesAmount);
        managerTarget.setTargetCustomerPlaceOrder(targetCustomerPlaceOrder);
        managerTarget.setActualSalesAmount(actualSalesAmount);
        managerTarget.setActualCustomerPlaceOrder(actualCustomerPlaceOrder);
        managerTarget.setDateMonth(dateMonth);
        return managerTarget;
    }

}
