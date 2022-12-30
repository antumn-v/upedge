package com.upedge.common.feign;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ServiceNameConstants;
import com.upedge.common.feign.fallback.PmsFeignClientFallbackFactory;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.pms.dto.CustomerStockPurchaseOrderRefundVo;
import com.upedge.common.model.pms.dto.VariantPurchaseInfoDto;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.*;
import com.upedge.common.model.pms.response.QuotedProductSelectBySkuResponse;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.common.model.product.VariantDetail;
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

    @PostMapping("/purchaseOrder/refundByCustomerStockOrder")
    public BaseResponse refundByCustomerStockOrder(@RequestBody CustomerStockPurchaseOrderRefundVo customerStockPurchaseOrderRefundVo);

    @PostMapping("/productPurchaseInfo/variants")
    public List<VariantPurchaseInfoDto> variantPurchaseInfo(@RequestBody List<Long> variantIds);

    @PostMapping("/purchaseOrder/createByCustomerStockOrder")
    public BaseResponse createByCustomerStockOrder(@RequestBody CreatePurchaseOrderRequest request);

    @PostMapping("/variantWarehouseStock/orderCancelShip")
    public int orderCancelShip(@RequestBody OrderItemQuantityVo orderItemQuantityVo);

    @PostMapping("/variantWarehouseStock/restoreLockQuantity")
    public BaseResponse restoreLockQuantity(@RequestBody VariantStockRestoreLockQuantityRequest request);

    @PostMapping("/variantWarehouseStock/orderCheckStock")
    public BaseResponse orderCheckStock(@RequestBody List<OrderItemQuantityVo> orderItemQuantityVos);

    @PostMapping("/product/customsInfo/{id}")
    public BaseResponse customsInfo(@PathVariable Long id);

    @PostMapping("/variantWarehouseStock/packageEx")
    public BaseResponse packageEx(@RequestBody OrderItemQuantityVo orderItemQuantityVo);

    @PostMapping("/storeCustomVariantRecord/save")
    public List<CustomerProductQuoteVo> saveStoreCustomVariantRecords(@RequestBody StoreCustomVariantRecordSaveRequest request);

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

    @PostMapping("/storeVariant/selectByPlatId")
    BaseResponse selectByPlatId(@RequestBody PlatIdSelectStoreVariantRequest request);

    @PostMapping("/relate/detail/search")
    BaseResponse searchRelateDetail(@RequestBody RelateDetailSearchRequest request);

    @PostMapping("/app/product/search/ships")
    public BaseResponse productSearchShips(@RequestBody ProductVariantShipsRequest request);

    @RequestMapping(value = "/product/userInfo/{id}", method=RequestMethod.POST)
    BaseResponse userInfo(@PathVariable Long id);

    @GetMapping("/product/variantDetail/{id}")
    public List<VariantDetail> getVariantDetail(@PathVariable Long id);

    @RequestMapping(value = "/productSaiheInventory/all" , method = RequestMethod.POST)
    BaseResponse queryProductSaiheInventory(@RequestBody ProductSaiheInventoryVo productSaiheInventory);

    @RequestMapping(value = "/productSaiheInventory/insertProductSaiheInventory" , method = RequestMethod.POST)
    BaseResponse insertProductSaiheInventory(@RequestBody ProductSaiheInventoryVo productSaiheInventory);

    @PostMapping("/customerProductQuote/search")
    List<CustomerProductQuoteVo> searchCustomerProductQuote(@RequestBody CustomerProductQuoteSearchRequest request);


    @PostMapping("/quoteApply/order")
    public BaseResponse orderQuoteApply(@RequestBody OrderQuoteApplyRequest request);

    @PostMapping("/customerProductQuote/selectBySkus")
    public QuotedProductSelectBySkuResponse selectQuoteProductBySkus(@RequestBody QuotedProductSelectBySkuRequest request);
}
