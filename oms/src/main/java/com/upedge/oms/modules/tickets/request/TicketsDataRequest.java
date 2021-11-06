package com.upedge.oms.modules.tickets.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.tickets.vo.TicketsDataVo;
import lombok.Data;

@Data
public class TicketsDataRequest  extends Page<TicketsDataVo> {

    /**
     * 查询 开始 ---  结束 时间
     */
    private String startDay;
    private String endDay;

}
