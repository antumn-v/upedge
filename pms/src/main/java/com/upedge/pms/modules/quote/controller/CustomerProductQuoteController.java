package com.upedge.pms.modules.quote.controller;

import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CustomerProductQuoteVo> searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request){
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteService.selectQuoteDetail(request);
        return customerProductQuoteVos;
    }



}
