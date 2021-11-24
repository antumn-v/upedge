package com.upedge.pms.modules.quote.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.response.CustomerProductQuoteSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public CustomerProductQuoteSearchResponse searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request){
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteService.selectQuoteDetail(request);

        return CustomerProductQuoteSearchResponse.success(customerProductQuoteVos);
    }



}
