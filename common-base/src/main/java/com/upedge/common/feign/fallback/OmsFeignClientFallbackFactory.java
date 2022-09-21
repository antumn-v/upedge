package com.upedge.common.feign.fallback;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.cart.request.CartAddRequest;
import com.upedge.common.model.cart.request.CartSelectByIdsRequest;
import com.upedge.common.model.cart.request.CartSubmitRequest;
import com.upedge.common.model.cart.request.CartVo;
import com.upedge.common.model.oms.stock.StockOrderVo;
import com.upedge.common.model.order.OrderItemQuantityDto;
import com.upedge.common.model.order.dto.OrderItemPurchaseAdviceDto;
import com.upedge.common.model.order.request.ManagerActualRequest;
import com.upedge.common.model.order.request.OrderDailyCountRequest;
import com.upedge.common.model.order.request.OrderStockStateUpdateRequest;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.order.vo.OrderItemPurchaseAdviceVo;
import com.upedge.common.model.order.vo.OrderItemUpdateImageNameRequest;
import com.upedge.common.model.order.vo.UplodaSaiheOnMqVo;
import com.upedge.common.model.pms.vo.VariantPreSaleQuantity;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.request.OrderStatisticsRequest;
import com.upedge.common.model.statistics.response.ManagerOrderCountResponse;
import com.upedge.common.model.statistics.response.ManagerPackageStatisticsResponse;
import com.upedge.common.model.store.request.StoreApiRequest;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OmsFeignClientFallbackFactory implements FallbackFactory<OmsFeignClient> {
    @Override
    public OmsFeignClient create(Throwable cause) {
        return new OmsFeignClient() {


            @Override
            public void checkStock(OrderItemQuantityDto orderItemQuantityDto) {
            }

            @Override
            public int updateStockState(OrderStockStateUpdateRequest request) {
                return 0;
            }

            @Override
            public BaseResponse updateWarehouseByMethod(Long methodId) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse orderConfirmReceipt(StockOrderVo stockOrderVo) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse updateImageNameByStoreVariantId(OrderItemUpdateImageNameRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public List<VariantPreSaleQuantity> selectVariantPreSaleQuantity(List<Long> variantIds) {
                return new ArrayList<>();
            }

            @Override
            public OrderItemPurchaseAdviceVo purchaseItems() {
                return null;
            }

            @Override
            public List<Long> selectItemAdminVariantIds(OrderItemPurchaseAdviceDto orderItemPurchaseAdviceDto) {
                return new ArrayList<>();
            }

            @Override
            public ManagerPackageStatisticsResponse managerPackageStatistics(ManagerPackageStatisticsRequest request) {
                return (ManagerPackageStatisticsResponse) ManagerPackageStatisticsResponse.failed();
            }

            @Override
            public BaseResponse customerPayOrderStatistics(OrderStatisticsRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse managerOrderStatistics(OrderStatisticsRequest request) {
                return null;
            }

            @Override
            public BaseResponse cartAdd(CartAddRequest request) {
                log.warn(request + "--->添加购物车失败");
                return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
            }

            @Override
            public List<CartVo> selectByIds(CartSelectByIdsRequest request) {
                return new ArrayList<>();
            }

            @Override
            public BaseResponse submitByIds(CartSubmitRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse updateShopifyOrder(StoreApiRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse woocommerceOrderUpdate(StoreApiRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse updateShoplazzaOrder(StoreApiRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse orderByRollback(PaypalOrder paypalOrder) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse orderPaypalCompleted(@Valid PaypalPayment paypalPayment) {
                return BaseResponse.failed();
            }

            /**
             * 处理paypal支付的批发订单
             * @param paypalPayment
             * @return
             */
            @Override
            public BaseResponse wholesalePaypalCompleted(@Valid PaypalPayment paypalPayment) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse storeProductSales() {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse stockPaypalCompleted(@Valid PaypalPayment paypalPayment) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse storeOrderCount(Long storeId) {
                return BaseResponse.failed();
            }

            /**
             * 根据年月  客户经理id  查询 该目标经理的  销售额、 下单客户数
             * @param request
             * @return
             */
            @Override
            public BaseResponse getManagerActual(ManagerActualRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse customerStockNum() {
                return BaseResponse.failed();
            }

            @Override
            public ManagerOrderCountResponse managerDateOrderCount(ManagerPackageStatisticsRequest request) {
                return new ManagerOrderCountResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL, null, request);
            }

            @Override
            public BaseResponse getWholeSaleOrderAmountByManagerCodeSet(AllOrderAmountVo allOrderAmountVo) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse getNormalOrderAmountByManagerCodeSet(AllOrderAmountVo allOrderAmountVo) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse getNormalOrderCount(AllOrderAmountVo allOrderAmountVo) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse getWholeSaleOrderCount(AllOrderAmountVo allOrderAmountVo) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse getCustomerOrderStatistical(Long customerId) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse selectCurrMonthSaleAmount(OrderDailyCountRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public void uploadPaymentIdOnMq(UplodaSaiheOnMqVo uplodaSaiheOnMq) {
                return;
            }
        };
    }
}
