package com.upedge.oms.modules.tickets.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.tickets.entity.SupportTickets;
import com.upedge.oms.modules.tickets.request.*;
import com.upedge.oms.modules.tickets.response.SupportTicketsInfoResponse;
import com.upedge.oms.modules.tickets.response.SupportTicketsListResponse;
import com.upedge.oms.modules.tickets.vo.SupportTicketsVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author author
 */
public interface SupportTicketsService{

    SupportTicketsVo selectOpenTicketDetailByOrderId(Long orderId);

    Long selectCustomerTicketCount(CustomerTicketListRequest request);

    SupportTicketsVo ticketDetail(Long id);

    BaseResponse claimTicket(Long id,Session session);

    BaseResponse customerTicketList(CustomerTicketListRequest request);

    SupportTickets selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(SupportTickets record);

    int updateByPrimaryKeySelective(SupportTickets record);

    int insert(SupportTickets record);

    int insertSelective(SupportTickets record);

    List<SupportTickets> select(Page<SupportTickets> record);

    long count(Page<SupportTickets> record);

    BaseResponse openTicket(OpenTicketRequest request, Session session) throws CustomerException;

    SupportTicketsListResponse adminList(SupportTicketsListRequest request);

    BaseResponse closeTicket(Long ticketId, Session session);

    BaseResponse sendTextMsg(SendTextMsgRequest request, Session session);

    BaseResponse sendImgMsg(MultipartFile file, Long ticketId, Session session);

    SupportTicketsInfoResponse ticketsInfo(Long id, Session session);

    BaseResponse sendBase64ImgMsg(SendImgMsgRequest request, Session session);

    Long processingCount(CustomerTicketListRequest request);
}

