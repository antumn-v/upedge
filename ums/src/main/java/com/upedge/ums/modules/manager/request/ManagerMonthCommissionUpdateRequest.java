package com.upedge.ums.modules.manager.request;

import com.upedge.ums.modules.manager.entity.ManagerMonthCommission;
import java.util.Date;
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
    private Date dateMonth;
    /**
     * 
     */
    private BigDecimal totalCommission;

    public ManagerMonthCommission toManagerMonthCommission(Long id){
        ManagerMonthCommission managerMonthCommission=new ManagerMonthCommission();
        managerMonthCommission.setId(id);
        managerMonthCommission.setManagerId(managerId);
        managerMonthCommission.setDateMonth(dateMonth);
        managerMonthCommission.setTotalCommission(totalCommission);
        return managerMonthCommission;
    }

}
