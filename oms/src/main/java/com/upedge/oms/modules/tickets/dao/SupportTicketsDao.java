package com.upedge.oms.modules.tickets.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.tickets.entity.SupportTickets;
import com.upedge.oms.modules.tickets.request.CustomerTicketListRequest;
import com.upedge.oms.modules.tickets.vo.CustomerTicketVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface SupportTicketsDao{

    List<CustomerTicketVo> selectProcessList(CustomerTicketListRequest request);

    Long countProcessList(CustomerTicketListRequest request);

    List<CustomerTicketVo> selectCustomerTicketList(CustomerTicketListRequest request);

    Long selectCustomerTicketCount(CustomerTicketListRequest request);

    SupportTickets selectOpenTicketByOrderId(Long orderId);

    SupportTickets selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(SupportTickets record);

    int updateByPrimaryKey(SupportTickets record);

    int updateByPrimaryKeySelective(SupportTickets record);

    int insert(SupportTickets record);

    int insertSelective(SupportTickets record);

    int insertByBatch(List<SupportTickets> list);

    List<SupportTickets> select(Page<SupportTickets> record);

    long count(Page<SupportTickets> record);

    int existTicketByOrderId(Long orderId);

    int countTicketByOrderId(Long orderId);

    //未读tickets
    long unreadMsgNum(@Param("userManager") String userManager);
    //待处理tickets
    long handleTicketNum(@Param("userManager") String userManager);
}
