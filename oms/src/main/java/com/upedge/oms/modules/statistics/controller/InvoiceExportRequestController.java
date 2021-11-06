package com.upedge.oms.modules.statistics.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.statistics.entity.InvoiceExportRequest;
import com.upedge.oms.modules.statistics.request.InvoiceExportRequestAddRequest;
import com.upedge.oms.modules.statistics.request.InvoiceExportRequestListRequest;
import com.upedge.oms.modules.statistics.request.InvoiceExportRequestUpdateRequest;
import com.upedge.oms.modules.statistics.response.*;
import com.upedge.oms.modules.statistics.service.InvoiceExportRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author 夕雾
 */
@RestController
@RequestMapping("/invoiceExportRequest")
public class InvoiceExportRequestController {
    @Autowired
    private InvoiceExportRequestService invoiceExportRequestService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "statistics:invoiceexportrequest:info:id")
    public InvoiceExportRequestInfoResponse info(@PathVariable Integer id) {
        InvoiceExportRequest result = invoiceExportRequestService.selectByPrimaryKey(id);
        InvoiceExportRequestInfoResponse res = new InvoiceExportRequestInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

//    @RequestMapping(value="/list", method=RequestMethod.POST)
//    @Permission(permission = "statistics:invoiceexportrequest:list")
//    public InvoiceExportRequestListResponse list(@RequestBody @Valid InvoiceExportRequestListRequest request) {
//        Session session = UserUtil.getSession(redisTemplate);
//        request.getT().setCustomerId(session.getCustomerId());
//        List<InvoiceExportRequest> results = invoiceExportRequestService.select(request);
//        Long total = invoiceExportRequestService.count(request);
//        request.setTotal(total);
//        InvoiceExportRequestListResponse res = new InvoiceExportRequestListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
//        return res;
//    }




}
