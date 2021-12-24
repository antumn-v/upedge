package com.upedge.oms.modules.statistics.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.statistics.dto.InvoiceListDto;
import com.upedge.oms.modules.statistics.request.InvoiceDetailRequest;
import com.upedge.oms.modules.statistics.request.InvoiceListRequest;
import com.upedge.oms.modules.statistics.request.InvoiceSearchRequest;
import com.upedge.oms.modules.statistics.service.InvoiceService;
import com.upedge.oms.modules.statistics.vo.InvoiceDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "账单")
@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("账单列表")
    @ApiImplicitParam(name = "orderType",value = "订单类型，1=备库订单，2=普通订单，3=批发订单")
    @PostMapping("/list")
    public BaseResponse customerInvoiceList(@RequestBody InvoiceListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        if (null == request.getT()){
            request.setT(new InvoiceListDto());
        }
        request.getT().setCustomerId(session.getCustomerId());
        return invoiceService.customerInvoiceList(request);
    }

    @ApiOperation("账单详情")
    @PostMapping("/detail")
    public BaseResponse customerInvoiceDeatil(@RequestBody InvoiceDetailRequest request){
        InvoiceDetailVo detailVo = invoiceService.customerInvoiceDetail(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,detailVo,request);
    }

//    @ApiOperation("日期查询账单")
//    @PostMapping("/search")
//    public BaseResponse customerSearchInvoiceByType(@RequestBody InvoiceSearchRequest request){
//        Session session = UserUtil.getSession(redisTemplate);
//        request.setCustomerId(session.getCustomerId());
//        InvoiceDetailVo detailVo = invoiceService.customerInvoiceSearch(request);
//        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,detailVo,request);
//    }

    @ApiOperation("excel导出订单")
    @PostMapping("/creatExcel")
    public BaseResponse customerInvoiceCreatExcel(@RequestBody InvoiceSearchRequest request) throws CustomerException {
        Session session = UserUtil.getSession(redisTemplate);
        request.setCustomerId(session.getCustomerId());
        String url = invoiceService.customerSearchInvoice(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,url);
    }
}
