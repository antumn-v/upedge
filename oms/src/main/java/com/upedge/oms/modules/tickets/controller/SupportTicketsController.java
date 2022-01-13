package com.upedge.oms.modules.tickets.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.tickets.dto.CustomerTicketListDto;
import com.upedge.oms.modules.tickets.entity.SupportTickets;
import com.upedge.oms.modules.tickets.request.*;
import com.upedge.oms.modules.tickets.response.SupportTicketsInfoResponse;
import com.upedge.oms.modules.tickets.response.SupportTicketsListResponse;
import com.upedge.oms.modules.tickets.service.SupportTicketsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author author
 */
@Api(tags = "Ticket管理")
@RestController
@RequestMapping("/supportTickets")
public class SupportTicketsController {
    @Autowired
    private SupportTicketsService supportTicketsService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @ApiOperation("客户ticket列表")
    @PostMapping("/customer/list")
    public BaseResponse customerTicketList(@RequestBody CustomerTicketListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (null == request.getT()) {
            request.setT(new CustomerTicketListDto());
        }
        request.getT().setCustomerId(session.getCustomerId());
        return supportTicketsService.customerTicketList(request);
    }

    @ApiOperation("认领列表")
    @PostMapping("/claimList")
    public BaseResponse claimList(@RequestBody CustomerTicketListRequest request) {
        if (null == request.getT()) {
            request.setT(new CustomerTicketListDto());
        }
        request.getT().setState(2);
        return supportTicketsService.customerTicketList(request);
    }

    @ApiOperation("客户ticket列表Processing数量")
    @PostMapping("/customer/processingCount")
    public BaseResponse processingCount(@RequestBody CustomerTicketListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (null == request.getT()) {
            request.setT(new CustomerTicketListDto());
        }
        request.getT().setCustomerId(session.getCustomerId());
        request.getT().setState(0);
        Long count = supportTicketsService.processingCount(request);
        request.setTotal(count);
        return BaseResponse.success(count, request);
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @Permission(permission = "tickets:supporttickets:info:id")
    public SupportTicketsInfoResponse info(@PathVariable Long id) {
        SupportTickets result = supportTicketsService.selectByPrimaryKey(id);
        SupportTicketsInfoResponse res = new SupportTicketsInfoResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, result, id);
        return res;
    }


    @ApiOperation("认领")
    @PostMapping("/claim/{id}")
    public BaseResponse claimTicket(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        if (session.getApplicationId().equals(Constant.ADMIN_APPLICATION_ID)) {
            return supportTicketsService.claimTicket(id, session);
        }
        return BaseResponse.failed();
    }

    //========================admin======================================

    /**
     * support tickets列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SupportTicketsListResponse adminList(@RequestBody @Valid SupportTicketsListRequest request) {
        return supportTicketsService.adminList(request);
    }

    @ApiOperation("开启support tickets")
    @RequestMapping(value = "/openTicket", method = RequestMethod.POST)
    public BaseResponse openTicket(@RequestBody @Valid OpenTicketRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        try {
            return supportTicketsService.openTicket(request, session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
    }

    /**
     * 关闭 support tickets
     *
     * @param ticketId
     * @return
     */
    @ApiOperation("关闭 support tickets")
    @RequestMapping(value = "/closeTicket/{ticketId}", method = RequestMethod.POST)
    public BaseResponse closeTicket(@PathVariable Long ticketId) {
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.closeTicket(ticketId, session);
    }

    /**
     * 发送文本消息
     */
    @ApiOperation("发送文本消息")
    @RequestMapping(value = "/sendTextMsg", method = RequestMethod.POST)
    public BaseResponse sendTextMsg(@RequestBody @Valid SendTextMsgRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.sendTextMsg(request, session);
    }

    /**
     * 发送图片消息
     */
    @ApiOperation("发送图片消息")
    @RequestMapping(value = "/sendImgMsg", method = RequestMethod.POST)
    public BaseResponse sendImgMsg(@RequestBody @Valid SendImgMsgRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.sendBase64ImgMsg(request, session);
    }

    /**
     * 进入tickets详情 标记消息为已读
     */
    @ApiOperation("进入tickets详情 标记消息为已读")
    @RequestMapping(value="/info/{id}",method=RequestMethod.POST)
    public SupportTicketsInfoResponse ticketsInfo(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsService.ticketsInfo(id,session);
    }


}
