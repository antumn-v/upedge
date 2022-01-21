package com.upedge.pms.modules.quote.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.PriceUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteListRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户报价产品管理")
@RestController
@RequestMapping("/customerProductQuote")
public class CustomerProductQuoteController {
    @Autowired
    private CustomerProductQuoteService customerProductQuoteService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("客户查看已报价产品")
    @PostMapping("/list")
    public BaseResponse customerProductQuoteList(@RequestBody CustomerProductQuoteListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        if (request.getT() == null){
            request.setT(new CustomerProductQuote());
        }
        request.getT().setQuoteState(1);
        request.getT().setCustomerId(session.getCustomerId());
        List<CustomerProductQuote> customerProductQuotes = customerProductQuoteService.select(request);
        if (session.getApplicationId().equals(Constant.APP_APPLICATION_ID)){
            for (CustomerProductQuote customerProductQuote : customerProductQuotes) {
                if (customerProductQuote.getQuoteState() == 1){
                    customerProductQuote.setQuotePrice(PriceUtils.cnyToUsdByDefaultRate(customerProductQuote.getQuotePrice()));
                }
            }
        }
        Long total = customerProductQuoteService.count(request);
        request.setTotal(total);
        return BaseResponse.success(customerProductQuotes,request);
    }


    @ApiOperation("所有已报价产品")
    @PostMapping("/all")
    public BaseResponse allProductQuote(@RequestBody CustomerProductQuoteListRequest request){
        List<CustomerProductQuote> customerProductQuotes = customerProductQuoteService.select(request);
        Long total = customerProductQuoteService.count(request);
        request.setTotal(total);
        return BaseResponse.success(customerProductQuotes,request);
    }

    @ApiOperation("修改客户报价")
    @PostMapping("/update")
    public BaseResponse updateCustomerProductQuote(@RequestBody@Valid CustomerProductQuoteUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse baseResponse = customerProductQuoteService.updateCustomerProductQuote(request,session);
//        if (baseResponse.getCode() == ResultCode.SUCCESS_CODE){
//            List<Long> storeVariantIds = new ArrayList<>();
//            storeVariantIds.add(request.getStoreVariantId());
//            customerProductQuoteService.sendCustomerProductQuoteUpdateMessage(storeVariantIds);
//        }
        return baseResponse;
    }

    @PostMapping("/search")
    public List<CustomerProductQuoteVo> searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request){
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteService.selectQuoteDetail(request);
        return customerProductQuoteVos;
    }



}
