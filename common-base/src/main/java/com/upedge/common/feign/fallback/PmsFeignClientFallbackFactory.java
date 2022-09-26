package com.upedge.common.feign.fallback;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.model.pms.request.CustomerProductQuoteSearchRequest;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.pms.request.QuotedProductSelectBySkuRequest;
import com.upedge.common.model.pms.request.StoreCustomVariantRecordSaveRequest;
import com.upedge.common.model.pms.response.QuotedProductSelectBySkuResponse;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.product.request.PlatIdSelectStoreVariantRequest;
import com.upedge.common.model.product.request.ProductVariantShipsRequest;
import com.upedge.common.model.product.request.RelateDetailSearchRequest;
import com.upedge.common.model.store.request.StoreApiRequest;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PmsFeignClientFallbackFactory implements FallbackFactory<PmsFeignClient> {
    @Override
    public PmsFeignClient create(Throwable cause) {
        return new PmsFeignClient() {
            @Override
            public int orderCancelShip(OrderItemQuantityVo orderItemQuantityVo) {
                return 0;
            }

            @Override
            public BaseResponse customsInfo(Long id) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse packageEx(OrderItemQuantityVo orderItemQuantityVo) {
                return BaseResponse.failed();
            }

            @Override
            public List<CustomerProductQuoteVo> saveStoreCustomVariantRecords(StoreCustomVariantRecordSaveRequest request) {
                return new ArrayList<>();
            }

            @Override
            public void deleteStoreProduct(@Valid StoreApiRequest request) {
                return;
            }

            @Override
            public Boolean updateShopifyProduct(StoreApiRequest request) {
                return false;
            }

            @Override
            public void updateWoocommerceProduct(@Valid StoreApiRequest request) {
                return;
            }

            @Override
            public void updateShoplazzaProduct(@Valid StoreApiRequest request) {
                return;
            }

            @Override
            public BaseResponse listProductByShippingId(Long shippingId) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse countProductByShippingId(Long shippingId) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse listVariantByIds(ListVariantsRequest request) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse selectVariantBySku(String sku) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse listVariantDetailByIds(ListVariantsRequest request) {
                return null;
            }

            @Override
            public BaseResponse selectByPlatId(PlatIdSelectStoreVariantRequest request) {
                return BaseResponse.failed("服务异常");
            }

            @Override
            public BaseResponse searchRelateDetail(RelateDetailSearchRequest request) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse productSearchShips(ProductVariantShipsRequest request) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse userInfo(Long id) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public List<VariantDetail> getVariantDetail(Long id) {
                return new ArrayList<>();
            }

            @Override
            public BaseResponse queryProductSaiheInventory(ProductSaiheInventoryVo productSaiheInventory) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse insertProductSaiheInventory(ProductSaiheInventoryVo productSaiheInventory) {
                return BaseResponse.failed();
            }

            @Override
            public List<CustomerProductQuoteVo> searchCustomerProductQuote(CustomerProductQuoteSearchRequest request) {
                return new ArrayList<>();
            }

            @Override
            public BaseResponse orderQuoteApply(OrderQuoteApplyRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public QuotedProductSelectBySkuResponse selectQuoteProductBySkus(QuotedProductSelectBySkuRequest request) {
                return new QuotedProductSelectBySkuResponse(ResultCode.SUCCESS_CODE,new ArrayList<>());
            }
        };
    }
}
