package com.upedge.ums.modules.manager.request;


import com.upedge.ums.modules.manager.entity.ManagerTarget;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
@Data
@NoArgsConstructor
public class ManagerTargetAddRequest{
    String date;

    List<ManagerTargetRequestData> managerTargets;


    @Data
    @NoArgsConstructor
    public static class ManagerTargetRequestData{

        private String managerCode;
        /**
         * 目标销售额
         */
        @NotNull
        private BigDecimal targetSalesAmount;
        /**
         * 目标客户下单数
         */
        @NotNull
        private Integer targetCustomerPlaceOrder;


        public ManagerTarget toManagerTarget(){
            ManagerTarget managerTarget=new ManagerTarget();
            managerTarget.setManagerCode(managerCode);
            managerTarget.setTargetSalesAmount(targetSalesAmount);
            managerTarget.setTargetCustomerPlaceOrder(targetCustomerPlaceOrder);
            managerTarget.setActualSalesAmount(BigDecimal.ZERO);
            managerTarget.setActualCustomerPlaceOrder(0);
            return managerTarget;
        }
    }

    /**
    * 
    */





}
