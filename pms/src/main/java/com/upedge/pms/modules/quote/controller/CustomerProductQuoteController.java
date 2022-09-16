package com.upedge.pms.modules.quote.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.cart.request.CartAddRequest;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.pms.request.QuotedProductSelectBySkuRequest;
import com.upedge.common.model.pms.response.QuotedProductSelectBySkuResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.PriceUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.quote.entity.CustomerProductQuote;
import com.upedge.pms.modules.quote.request.AllCustomerQuoteProductSearchRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteListRequest;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteUpdateRequest;
import com.upedge.pms.modules.quote.request.QuoteProductImportCartRequest;
import com.upedge.pms.modules.quote.service.CustomerProductQuoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    ProductVariantService productVariantService;

    @Autowired
    OmsFeignClient omsFeignClient;

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
    public BaseResponse allProductQuote(@RequestBody AllCustomerQuoteProductSearchRequest request){
        List<CustomerProductQuote> customerProductQuotes = customerProductQuoteService.all(request);
        long total = customerProductQuoteService.countAllQuoteProduct(request);
        request.setTotal(total);
        return BaseResponse.success(customerProductQuotes,request);
    }

    @ApiOperation("修改客户报价")
    @PostMapping("/update")
    public BaseResponse updateCustomerProductQuote(@RequestBody@Valid CustomerProductQuoteUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse baseResponse = null;
        try {
            baseResponse = customerProductQuoteService.updateCustomerProductQuote(request,session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
        return baseResponse;
    }

    @ApiOperation("撤销报价")
    @PostMapping("/revoke/{id}")
    public BaseResponse revokeProductQuote(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return customerProductQuoteService.revokeQuote(id,session);
    }

    @PostMapping("/search")
    public List<CustomerProductQuoteVo> searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request){
        List<CustomerProductQuoteVo> customerProductQuoteVos = customerProductQuoteService.selectQuoteDetail(request);
        for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
            String key = RedisKey.STRING_QUOTED_STORE_VARIANT + customerProductQuoteVo.getStoreVariantId();
            redisTemplate.opsForValue().set(key,customerProductQuoteVo);
        }
        return customerProductQuoteVos;
    }

    @ApiOperation("已报价产品导入备库购物车")
    @PostMapping("/importCart")
    public BaseResponse importStockCart(@RequestBody@Valid QuoteProductImportCartRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        CustomerProductQuote customerProductQuote = customerProductQuoteService.selectByPrimaryKey(request.getStoreVariantId());
        if (null == customerProductQuote
        || !session.getCustomerId().equals(customerProductQuote.getCustomerId())){
            return BaseResponse.failed();
        }
        ProductVariant variant = productVariantService.selectByPrimaryKey(customerProductQuote.getVariantId());
        CartAddRequest cartAddRequest = new CartAddRequest();
        cartAddRequest.setCartType(0);
        cartAddRequest.setCustomerId(session.getCustomerId());
        cartAddRequest.setQuantity(request.getQuantity());
        cartAddRequest.setVariantId(variant.getId());
        cartAddRequest.setUsdPrice(PriceUtils.cnyToUsdByDefaultRate(customerProductQuote.getQuotePrice()));
        cartAddRequest.setVariantImage(customerProductQuote.getStoreVariantImage());
        cartAddRequest.setVariantName(customerProductQuote.getStoreVariantName());
        cartAddRequest.setVariantSku(customerProductQuote.getVariantSku());
        cartAddRequest.setProductId(customerProductQuote.getProductId());
        cartAddRequest.setProductTitle(customerProductQuote.getProductTitle());
        cartAddRequest.setWeight(variant.getWeight());
        cartAddRequest.setVolume(variant.getVolumeWeight());
        cartAddRequest.setMarkId(customerProductQuote.getStoreVariantId());
        return omsFeignClient.cartAdd(cartAddRequest);
    }

    @PostMapping("/selectBySkus")
    public QuotedProductSelectBySkuResponse selectQuoteProductBySkus(@RequestBody QuotedProductSelectBySkuRequest request){
        List<CustomerProductQuoteVo> customerProductQuoteVos =  customerProductQuoteService.selectQuoteProductBySkus(request);
        return new QuotedProductSelectBySkuResponse(ResultCode.SUCCESS_CODE,customerProductQuoteVos,customerProductQuoteVos);
    }
}
