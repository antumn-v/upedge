package com.upedge.oms.modules.tickets.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.tickets.dao.SupportTicketsDao;
import com.upedge.oms.modules.tickets.dao.SupportTicketsMessageDao;
import com.upedge.oms.modules.tickets.entity.SupportTickets;
import com.upedge.oms.modules.tickets.entity.SupportTicketsMessage;
import com.upedge.oms.modules.tickets.request.SupportTicketsMessageListRequest;
import com.upedge.oms.modules.tickets.response.SupportTicketsMessageListResponse;
import com.upedge.oms.modules.tickets.service.SupportTicketsMessageService;
import com.upedge.oms.modules.tickets.vo.SupportTicketsMessageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class SupportTicketsMessageServiceImpl implements SupportTicketsMessageService {

    @Autowired
    private SupportTicketsMessageDao supportTicketsMessageDao;
    @Autowired
    private SupportTicketsDao supportTicketsDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        SupportTicketsMessage record = new SupportTicketsMessage();
        record.setId(id);
        return supportTicketsMessageDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(SupportTicketsMessage record) {
        return supportTicketsMessageDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(SupportTicketsMessage record) {
        return supportTicketsMessageDao.insert(record);
    }

    @Override
    public List<SupportTicketsMessage> customerReceiverMessage(SupportTicketsMessageListRequest request, Session session) {
        request.initFromNum();
        SupportTicketsMessage supportTicketsMessage = request.getT();
        if (null == supportTicketsMessage || null == supportTicketsMessage.getTicketId()){
            return new ArrayList<>();
        }
        Long ticketId = supportTicketsMessage.getTicketId();

        SupportTicketsMessage message = supportTicketsMessageDao.selectTicketLastUnReceiveMessage(ticketId,session.getCustomerId());
        List<SupportTicketsMessage> messages = new ArrayList<>();
        if(null == message){
            messages = supportTicketsMessageDao.select(request);
        }else {
            Date date = new Date();
            messages = supportTicketsMessageDao.selectTicketLastUnReceiveMessageByDate(ticketId,session.getCustomerId(),message.getSendTime(),date);
            //标记未读消息为已读
            message = new SupportTicketsMessage();
            message.setTicketId(ticketId);
            message.setReadTime(new Date());
            message.setReceiverCustomerId(session.getCustomerId());
            message.setReceiverUserId(session.getId());
            supportTicketsMessageDao.updateMessageHaveRead(message);
        }
        Collections.sort(messages, new Comparator<SupportTicketsMessage>() {
            @Override
            public int compare(SupportTicketsMessage o1, SupportTicketsMessage o2) {
                //升序
                return o1.getSendTime().compareTo(o2.getSendTime());
            }
        });
        return messages;
    }

    /**
     *
     */
    public SupportTicketsMessage selectByPrimaryKey(Long id){
        SupportTicketsMessage record = new SupportTicketsMessage();
        record.setId(id);
        return supportTicketsMessageDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(SupportTicketsMessage record) {
        return supportTicketsMessageDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(SupportTicketsMessage record) {
        return supportTicketsMessageDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<SupportTicketsMessage> select(Page<SupportTicketsMessage> record){
        record.initFromNum();
        return supportTicketsMessageDao.select(record);
    }

    /**
    *
    */
    public long count(Page<SupportTicketsMessage> record){
        return supportTicketsMessageDao.count(record);
    }


    @Override
    public SupportTicketsMessageListResponse pageTicketMsg(SupportTicketsMessageListRequest request) {
        if(request.getT()==null){
            return new SupportTicketsMessageListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        SupportTicketsMessage supportTicketsMessage=request.getT();
        if(supportTicketsMessage.getTicketId()==null){
            return new SupportTicketsMessageListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        SupportTickets supportTickets = supportTicketsDao.selectByPrimaryKey(supportTicketsMessage.getTicketId());
        if(supportTickets==null||supportTickets.getState()==null){
            return new SupportTicketsMessageListResponse(ResultCode.FAIL_CODE, "supportTicket不存在!");
        }

        int pageSize=4;
        request.setPageSize(pageSize);
        request.initFromNum();
        long count=supportTicketsMessageDao.count(request);
        List<SupportTicketsMessage> list=supportTicketsMessageDao.select(request);
        Collections.reverse(list); // 倒序排列
        request.setTotal(count);
        List<SupportTicketsMessageVo> supportTicketsMessageVoList=new ArrayList<>();
        list.forEach(message->{
            SupportTicketsMessageVo supportTicketsMessageVo=new SupportTicketsMessageVo();
            BeanUtils.copyProperties(message,supportTicketsMessageVo);
            supportTicketsMessageVo.setManagerName(supportTickets.getManagerName());
            supportTicketsMessageVoList.add(supportTicketsMessageVo);
        });
        return new SupportTicketsMessageListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, supportTicketsMessageVoList,null);
    }

    @Override
    public BaseResponse ticketMsgNum(Session session) {
        //todo 根据admin用户获取消息数
        Integer msgNum=new Random().nextInt(10);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,msgNum);
    }
}