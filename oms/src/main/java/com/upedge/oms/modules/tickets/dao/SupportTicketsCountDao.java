package com.upedge.oms.modules.tickets.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.tickets.entity.SupportTicketsCount;
import com.upedge.oms.modules.tickets.request.TicketsDataRequest;
import com.upedge.oms.modules.tickets.request.TicketsListDataRequest;
import com.upedge.oms.modules.tickets.vo.TicketsDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface SupportTicketsCountDao{

    SupportTicketsCount selectByPrimaryKey(SupportTicketsCount record);

    int deleteByPrimaryKey(SupportTicketsCount record);

    int updateByPrimaryKey(SupportTicketsCount record);

    int updateByPrimaryKeySelective(SupportTicketsCount record);

    int insert(SupportTicketsCount record);

    int insertSelective(SupportTicketsCount record);

    int insertByBatch(List<SupportTicketsCount> list);

    List<SupportTicketsCount> select(Page<SupportTicketsCount> record);

    long count(Page<SupportTicketsCount> record);

    void updateSupportTicketsCount(@Param("ticketId") Long ticketId,
                                   @Param("aNum") Integer aNum, @Param("bNum") Integer bNum);

    List<TicketsDataVo> selectTicketsDataPage(TicketsDataRequest ticketsDataRequest);

    Long selectTicketsDataCount(TicketsDataRequest ticketsDataRequest);

    Long totalSupportTicketsCount(TicketsListDataRequest request);

    List<SupportTicketsCount> pageSupportTickets(TicketsListDataRequest request);

    int addMessageAllByTicketId(@Param("ticketsId") Long ticketsId, @Param("managerCode") String managerCode);
}
