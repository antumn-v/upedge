package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import lombok.Data;

import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ManagerMonthCommissionUpdateRequest{

    /**
     * 
     */
    private Long managerId;
    /**
     * 
     */
    private String dateMonth;
    /**
     * 
     */
    private BigDecimal totalCommission;

    public ManagerMonthCommission toManagerMonthCommission(Long id){
        ManagerMonthCommission managerMonthCommission=new ManagerMonthCommission();
        managerMonthCommission.setManagerId(managerId);
        managerMonthCommission.setDateMonth(dateMonth);
        managerMonthCommission.setTotalCommission(totalCommission);
        return managerMonthCommission;
    }

}
