package com.upedge.oms.modules.tickets.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.tickets.entity.SupportTicketsMessage;
import com.upedge.oms.modules.tickets.vo.SupportTicketsVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author author
 */
public interface SupportTicketsMessageDao{

    List<SupportTicketsMessage> selectTicketLastUnReceiveMessageByDate(@Param("ticketId") Long ticketId,
                                                                       @Param("receiverCustomerId") Long receiverCustomerId,
                                                                       @Param("beginTime") Date beginTime,
                                                                       @Param("endTime") Date endTime);

    SupportTicketsMessage selectTicketLastUnReceiveMessage(@Param("ticketId") Long ticketId,
                                                           @Param("receiverCustomerId") Long receiverCustomerId);

    void updateMessageHaveRead(SupportTicketsMessage supportTicketsMessage);

    SupportTicketsMessage selectByPrimaryKey(SupportTicketsMessage record);

    int deleteByPrimaryKey(SupportTicketsMessage record);

    int updateByPrimaryKey(SupportTicketsMessage record);

    int updateByPrimaryKeySelective(SupportTicketsMessage record);

    int insert(SupportTicketsMessage record);

    int insertSelective(SupportTicketsMessage record);

    int insertByBatch(List<SupportTicketsMessage> list);

    List<SupportTicketsMessage> select(Page<SupportTicketsMessage> record);

    long count(Page<SupportTicketsMessage> record);

    //查询小于该时间最近一条Admin消息
    SupportTicketsMessage queryPrevAdminMsg(@Param("currDate") Date currDate, @Param("ticketId") Long ticketId);
    //查询小于该时间最近一条客户消息
    List<SupportTicketsMessage> queryNearestUserMsg(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("ticketId") Long ticketId);
    //客户回复标记
    void markReplyFlag(@Param("id") Long id, @Param("flag") Integer flag);
    //更新未读消息设置为已读
    void markReadMsg(@Param("ticketId") Long ticketId, @Param("fromUserId") Long fromUserId,
                     @Param("readTime") Date readTime);

    List<SupportTicketsVo> listMsgCountByTicketIds(@Param("ticketIds") Set<Long> ticketIds);

    List<SupportTicketsMessage> selectNearestUserMsg(@Param("ticketId") Long ticketId);
}
