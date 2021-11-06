package com.upedge.common.model.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 夕雾
 * 2021/07/03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeManagerVo {

    /**
     * 客户前任客户经理
     */
    private String oldManager;

    /**
     * 客户customerId
     */
    private Long customerId;

    /**
     * 客户现任客户经理
     */
    private String newManager;
}
