package com.upedge.pms.modules.quote.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteAddRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteListRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;

import com.upedge.pms.modules.quote.response.CustomerProductQuoteAddResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteDelResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteInfoResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteListResponse;
import com.upedge.pms.modules.quote.response.CustomerProductQuoteUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerProductQuote")
public class CustomerProductQuoteController {
    @Autowired
    private CustomerProductQuoteService customerProductQuoteService;

    @PostMapping("/search")
    public BaseResponse searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request){
        return null;
    }



}
