package com.upedge.oms.modules.tickets.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.tickets.entity.SupportTicketsCount;
import com.upedge.oms.modules.tickets.request.*;
import com.upedge.oms.modules.tickets.response.*;
import com.upedge.oms.modules.tickets.service.SupportTicketsCountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/supportTicketsCount")
public class SupportTicketsCountController {
    @Autowired
    private SupportTicketsCountService supportTicketsCountService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "tickets:supportticketscount:info:id")
    public SupportTicketsCountInfoResponse info(@PathVariable Long id) {
        SupportTicketsCount result = supportTicketsCountService.selectByPrimaryKey(id);
        SupportTicketsCountInfoResponse res = new SupportTicketsCountInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketscount:list")
    public SupportTicketsCountListResponse list(@RequestBody @Valid SupportTicketsCountListRequest request) {
        List<SupportTicketsCount> results = supportTicketsCountService.select(request);
        Long total = supportTicketsCountService.count(request);
        request.setTotal(total);
        SupportTicketsCountListResponse res = new SupportTicketsCountListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketscount:add")
    public SupportTicketsCountAddResponse add(@RequestBody @Valid SupportTicketsCountAddRequest request) {
        SupportTicketsCount entity=request.toSupportTicketsCount();
        supportTicketsCountService.insertSelective(entity);
        SupportTicketsCountAddResponse res = new SupportTicketsCountAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketscount:del:id")
    public SupportTicketsCountDelResponse del(@PathVariable Long id) {
        supportTicketsCountService.deleteByPrimaryKey(id);
        SupportTicketsCountDelResponse res = new SupportTicketsCountDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "tickets:supportticketscount:update")
    public SupportTicketsCountUpdateResponse update(@PathVariable Long id, @RequestBody @Valid SupportTicketsCountUpdateRequest request) {
        SupportTicketsCount entity=request.toSupportTicketsCount(id);
        supportTicketsCountService.updateByPrimaryKeySelective(entity);
        SupportTicketsCountUpdateResponse res = new SupportTicketsCountUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 报表  ticket 统计
     */
    @ApiOperation("ticket统计")
    @Permission(permission = "tickets:supportticketscount:ticketsData")
    @PostMapping("ticketsData")
    public BaseResponse ticketsData(@RequestBody TicketsDataRequest ticketsDataRequest){

        return  supportTicketsCountService.ticketsData(ticketsDataRequest);
    }

    /**
     * 报表 ticket数据
     */
    @ApiOperation("ticket数据")
    @Permission(permission = "tickets:supportticketscount:listData")
    @PostMapping("/listData")
    public BaseResponse listData(@RequestBody TicketsListDataRequest request){
        return  supportTicketsCountService.listData(request);
    }
}
