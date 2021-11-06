package com.upedge.common.model.statistics.request;

import com.upedge.common.base.Page;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.statistics.dto.OrderStatisticsDto;
import com.upedge.common.model.user.vo.Session;
import lombok.Data;

@Data
public class OrderStatisticsRequest extends Page<OrderStatisticsDto> {

    Session session;

    ManagerInfoVo managerInfoVo;
}
