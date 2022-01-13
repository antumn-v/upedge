package com.upedge.oms.modules.tickets.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.tickets.entity.SupportTicketsMessage;
import com.upedge.oms.modules.tickets.request.SupportTicketsMessageAddRequest;
import com.upedge.oms.modules.tickets.request.SupportTicketsMessageListRequest;
import com.upedge.oms.modules.tickets.request.SupportTicketsMessageUpdateRequest;
import com.upedge.oms.modules.tickets.response.*;
import com.upedge.oms.modules.tickets.service.SupportTicketsMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/**
 * 
 *
 * @author author
 */
@Api(tags = "ticket消息列表")
@RestController
@RequestMapping("/supportTicketsMessage")
public class SupportTicketsMessageController {
    @Autowired
    private SupportTicketsMessageService supportTicketsMessageService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "tickets:supportticketsmessage:info:id")
    public SupportTicketsMessageInfoResponse info(@PathVariable Long id) {
        SupportTicketsMessage result = supportTicketsMessageService.selectByPrimaryKey(id);
        SupportTicketsMessageInfoResponse res = new SupportTicketsMessageInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }
    @ApiOperation("ticket消息列表")
    @ApiImplicitParam(name = "t.ticketId",value = "ticketId",required = true)
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketsmessage:list")
    public SupportTicketsMessageListResponse list(@RequestBody @Valid SupportTicketsMessageListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        List<SupportTicketsMessage> results = supportTicketsMessageService.customerReceiverMessage(request,session);
        if (ListUtils.isEmpty(results)){
            request.setTotal(0L);
        }else {
            Long total = supportTicketsMessageService.count(request);
            request.setTotal(total);
        }
        SupportTicketsMessageListResponse res = new SupportTicketsMessageListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketsmessage:add")
    public SupportTicketsMessageAddResponse add(@RequestBody @Valid SupportTicketsMessageAddRequest request) {
        SupportTicketsMessage entity=request.toSupportTicketsMessage();
        supportTicketsMessageService.insertSelective(entity);
        SupportTicketsMessageAddResponse res = new SupportTicketsMessageAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketsmessage:del:id")
    public SupportTicketsMessageDelResponse del(@PathVariable Long id) {
        supportTicketsMessageService.deleteByPrimaryKey(id);
        SupportTicketsMessageDelResponse res = new SupportTicketsMessageDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketsmessage:update")
    public SupportTicketsMessageUpdateResponse update(@PathVariable Long id, @RequestBody @Valid SupportTicketsMessageUpdateRequest request) {
        SupportTicketsMessage entity=request.toSupportTicketsMessage(id);
        supportTicketsMessageService.updateByPrimaryKeySelective(entity);
        SupportTicketsMessageUpdateResponse res = new SupportTicketsMessageUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    //======================admin=========================

    /**
     * ticket消息列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/pageTicketMsg", method=RequestMethod.POST)
    public SupportTicketsMessageListResponse pageTicketMsg(@RequestBody @Valid SupportTicketsMessageListRequest request) {
        return supportTicketsMessageService.pageTicketMsg(request);
    }

    /**
     * ticket消息数
     * @return
     */
    @RequestMapping(value = "/admin/ticketMsgNum", method=RequestMethod.POST)
    @ResponseBody
    public BaseResponse ticketMsgNum() {
        Session session = UserUtil.getSession(redisTemplate);
        return supportTicketsMessageService.ticketMsgNum(session);
    }

}
