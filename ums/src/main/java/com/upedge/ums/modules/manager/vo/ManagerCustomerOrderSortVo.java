package com.upedge.ums.modules.manager.vo;

import com.upedge.common.model.statistics.vo.CustomerOrderStatisticsVo;
import com.upedge.common.model.user.vo.ManagerCustomerVo;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManagerCustomerOrderSortVo {

    private Long customerId;

    private String userName;

    private String email;

    private Integer orderCount;

    private BigDecimal orderAmount;


    public ManagerCustomerOrderSortVo(ManagerCustomerVo managerCustomerVo, CustomerOrderStatisticsVo customerOrderStatisticsVo) {
        this.customerId = managerCustomerVo.getCustomerId();
        this.email = managerCustomerVo.getEmail();
        this.userName = managerCustomerVo.getUsername();
        this.orderCount = customerOrderStatisticsVo.getOrderPayCount() - customerOrderStatisticsVo.getRefundCount();
        this.orderAmount = customerOrderStatisticsVo.getCreditAmount().subtract(customerOrderStatisticsVo.getRefundAmount());
    }

    public ManagerCustomerOrderSortVo() {
    }
}
