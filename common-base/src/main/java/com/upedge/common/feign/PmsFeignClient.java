package com.upedge.common.feign;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ServiceNameConstants;
import com.upedge.common.feign.fallback.PmsFeignClientFallbackFactory;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.common.model.product.request.PlatIdSelectStoreVariantRequest;
import com.upedge.common.model.product.request.ProductVariantShipsRequest;
import com.upedge.common.model.product.request.RelateDetailSearchRequest;
import com.upedge.common.model.store.request.StoreApiRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 海桐
 */
@FeignClient(name = ServiceNameConstants.PMS_SERVICE,fallbackFactory = PmsFeignClientFallbackFactory.class,decode404 = true)
public interface PmsFeignClient {

    @PostMapping("/webhook/product/delete")
    public void deleteStoreProduct(@RequestBody @Valid StoreApiRequest request);

    @PostMapping("/shopify/webhook/product/update")
    Boolean updateShopifyProduct(@RequestBody StoreApiRequest request);

    @PostMapping("/woocommerce/webhook/product/update")
    void updateWoocommerceProduct(@RequestBody @Valid StoreApiRequest request);

    @PostMapping("/shoplazza/webhook/product/update")
    void updateShoplazzaProduct(@RequestBody @Valid StoreApiRequest request);

    @RequestMapping(value = "/product/ship/{shippingId}/list", method= RequestMethod.POST)
    BaseResponse listProductByShippingId(@PathVariable Long shippingId);

    @RequestMapping(value="/product/ship/{shippingId}/count", method= RequestMethod.POST)
    BaseResponse countProductByShippingId(@PathVariable Long shippingId);

    @RequestMapping(value="/productVariant/listVariantByIds", method= RequestMethod.POST)
    BaseResponse listVariantByIds(@RequestBody ListVariantsRequest request);

    @PostMapping("/productVariant/selectBySku")
    public BaseResponse selectVariantBySku(@RequestBody String sku);

    @PostMapping("/productVariant/listVariantDetailByIds")
    public BaseResponse listVariantDetailByIds(@RequestBody ListVariantsRequest request);

    @PostMapping("/store/variant/selectByPlatId")
    BaseResponse selectByPlatId(@RequestBody PlatIdSelectStoreVariantRequest request);

    @PostMapping("/relate/detail/search")
    BaseResponse searchRelateDetail(@RequestBody RelateDetailSearchRequest request);

    @PostMapping("/app/product/search/ships")
    public BaseResponse productSearchShips(@RequestBody ProductVariantShipsRequest request);

    @RequestMapping(value = "/product/userInfo/{id}", method=RequestMethod.POST)
    BaseResponse userInfo(@PathVariable Long id);

    @RequestMapping(value = "/productSaiheInventory/all" , method = RequestMethod.POST)
    BaseResponse queryProductSaiheInventory(@RequestBody ProductSaiheInventoryVo productSaiheInventory);

    @RequestMapping(value = "/productSaiheInventory/insertProductSaiheInventory" , method = RequestMethod.POST)
    BaseResponse insertProductSaiheInventory(@RequestBody ProductSaiheInventoryVo productSaiheInventory);

    @PostMapping("/customerProductQuote/search")
    List<CustomerProductQuoteVo> searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request);


    @PostMapping("/quoteApply/order")
    public BaseResponse orderQuoteApply(@RequestBody OrderQuoteApplyRequest request);
}
