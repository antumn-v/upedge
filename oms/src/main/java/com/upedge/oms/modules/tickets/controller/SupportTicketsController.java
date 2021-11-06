package com.upedge.oms.modules.tickets.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.tickets.dto.CustomerTicketListDto;
import com.upedge.oms.modules.tickets.entity.SupportTickets;
import com.upedge.oms.modules.tickets.request.*;
import com.upedge.oms.modules.tickets.response.SupportTicketsAddResponse;
import com.upedge.oms.modules.tickets.response.SupportTicketsInfoResponse;
import com.upedge.oms.modules.tickets.response.SupportTicketsListResponse;
import com.upedge.oms.modules.tickets.response.SupportTicketsUpdateResponse;
import com.upedge.oms.modules.tickets.service.SupportTicketsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/supportTickets")
public class SupportTicketsController {
    @Autowired
    private SupportTicketsService supportTicketsService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @ApiOperation("客户ticket列表")
    @PostMapping("/customer/list")
    public BaseResponse customerTicketList(@RequestBody CustomerTicketListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        if(null == request.getT()){
            request.setT(new CustomerTicketListDto());
        }
        request.getT().setCustomerId(session.getCustomerId());
        return supportTicketsService.customerTicketList(request);
    }

    @ApiOperation("客户ticket列表Processing数量")
    @PostMapping("/customer/processingCount")
    public BaseResponse processingCount(@RequestBody CustomerTicketListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        if(null == request.getT()){
            request.setT(new CustomerTicketListDto());
        }
        request.getT().setCustomerId(session.getCustomerId());
        request.getT().setState(0);
      Long count  =  supportTicketsService.processingCount(request);
      request.setTotal(count);
        return BaseResponse.success(count,request);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "tickets:supporttickets:info:id")
    public SupportTicketsInfoResponse info(@PathVariable Long id) {
        SupportTickets result = supportTicketsService.selectByPrimaryKey(id);
        SupportTicketsInfoResponse res = new SupportTicketsInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "tickets:supporttickets:add")
    public SupportTicketsAddResponse add(@RequestBody @Valid SupportTicketsAddRequest request) {
        SupportTickets entity=request.toSupportTickets();
        supportTicketsService.insertSelective(entity);
        SupportTicketsAddResponse res = new SupportTicketsAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "tickets:supporttickets:update")
    public SupportTicketsUpdateResponse update(@PathVariable Long id, @RequestBody @Valid SupportTicketsUpdateRequest request) {
        SupportTickets entity=request.toSupportTickets(id);
        supportTicketsService.updateByPrimaryKeySelective(entity);
        SupportTicketsUpdateResponse res = new SupportTicketsUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    //========================admin======================================

    /**
     * support tickets列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    public SupportTicketsListResponse adminList(@RequestBody @Valid SupportTicketsListRequest request) {
        return supportTicketsService.adminList(request);
    }

    /**
     * 开启support tickets
     * @param request
     * @return
     */
    @RequestMapping(value="/openTicket",method=RequestMethod.POST)
    public BaseResponse openTicket(@RequestBody @Valid OpenTicketRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.openTicket(request,session);
    }

    /**
     * 关闭 support tickets
     * @param ticketId
     * @return
     */
    @RequestMapping(value="/admin/closeTicket/{ticketId}",method=RequestMethod.POST)
    public BaseResponse closeTicket(@PathVariable Long ticketId) {
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.closeTicket(ticketId,session);
    }

    /**
     * 发送文本消息
     */
    @RequestMapping(value = "/admin/sendTextMsg",method=RequestMethod.POST)
    public BaseResponse sendTextMsg(@RequestBody @Valid SendTextMsgRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.sendTextMsg(request,session);
    }

    /**
     * 发送图片消息
     */
    @RequestMapping(value = "/admin/sendImgMsg",method=RequestMethod.POST)
    public BaseResponse sendImgMsg(@RequestBody @Valid SendImgMsgRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.sendBase64ImgMsg(request,session);
    }

    /**
     * 进入tickets详情 标记消息为已读
     */
//    @RequestMapping(value="/admin/info/{id}",method=RequestMethod.POST)
//    public SupportTicketsInfoResponse ticketsInfo(@PathVariable Long id) {
//        Session session = UserUtil.getSession(redisTemplate);
//        return supportTicketsService.ticketsInfo(id,session);
//    }


}
