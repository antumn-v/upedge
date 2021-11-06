package com.upedge.oms.modules.tickets.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.oms.modules.tickets.entity.SupportTicketsCount;
import com.upedge.oms.modules.tickets.request.TicketsDataRequest;
import com.upedge.oms.modules.tickets.request.TicketsListDataRequest;

import java.util.List;

/**
 * @author author
 */
public interface SupportTicketsCountService{

    SupportTicketsCount selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(SupportTicketsCount record);

    int updateByPrimaryKeySelective(SupportTicketsCount record);

    int insert(SupportTicketsCount record);

    int insertSelective(SupportTicketsCount record);

    List<SupportTicketsCount> select(Page<SupportTicketsCount> record);

    long count(Page<SupportTicketsCount> record);

    /**
     * 统计报表：ticket统计
     * @param ticketsDataRequest
     * @return
     */
    BaseResponse ticketsData(TicketsDataRequest ticketsDataRequest);

    /**
     * 统计报表：ticket数据
     * @param request
     * @return
     */
    BaseResponse listData(TicketsListDataRequest request);
}

