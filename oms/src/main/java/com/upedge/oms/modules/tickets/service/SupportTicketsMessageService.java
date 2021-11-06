package com.upedge.oms.modules.tickets.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.tickets.entity.SupportTicketsMessage;
import com.upedge.oms.modules.tickets.request.SupportTicketsMessageListRequest;
import com.upedge.oms.modules.tickets.response.SupportTicketsMessageListResponse;

import java.util.List;

/**
 * @author author
 */
public interface SupportTicketsMessageService{

    List<SupportTicketsMessage> customerReceiverMessage(SupportTicketsMessageListRequest request, Session session);

    SupportTicketsMessage selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(SupportTicketsMessage record);

    int updateByPrimaryKeySelective(SupportTicketsMessage record);

    int insert(SupportTicketsMessage record);

    int insertSelective(SupportTicketsMessage record);

    List<SupportTicketsMessage> select(Page<SupportTicketsMessage> record);

    long count(Page<SupportTicketsMessage> record);

    SupportTicketsMessageListResponse pageTicketMsg(SupportTicketsMessageListRequest request);

    BaseResponse ticketMsgNum(Session session);
}

