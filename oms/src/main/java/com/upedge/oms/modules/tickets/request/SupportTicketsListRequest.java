package com.upedge.oms.modules.tickets.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.tickets.entity.SupportTickets;
import lombok.Data;

/**
 * @author author
 */
@Data
public class SupportTicketsListRequest extends Page<SupportTickets> {

    /**
     * 读取状态   有未读 1   全部已读  0
     */
    private Integer readState;

}
