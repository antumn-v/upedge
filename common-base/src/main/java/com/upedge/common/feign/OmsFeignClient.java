package com.upedge.common.feign;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ServiceNameConstants;
import com.upedge.common.feign.fallback.OmsFeignClientFallbackFactory;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.cart.request.CartAddRequest;
import com.upedge.common.model.cart.request.CartSelectByIdsRequest;
import com.upedge.common.model.cart.request.CartSubmitRequest;
import com.upedge.common.model.cart.request.CartVo;
import com.upedge.common.model.oms.stock.StockOrderVo;
import com.upedge.common.model.order.OrderItemQuantityDto;
import com.upedge.common.model.order.request.ManagerActualRequest;
import com.upedge.common.model.order.request.OrderDailyCountRequest;
import com.upedge.common.model.order.request.OrderStockStateUpdateRequest;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.order.vo.OrderItemUpdateImageNameRequest;
import com.upedge.common.model.order.vo.UplodaSaiheOnMqVo;
import com.upedge.common.model.pms.vo.PurchaseAdviceItemVo;
import com.upedge.common.model.pms.vo.VariantPreSaleQuantity;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.request.OrderStatisticsRequest;
import com.upedge.common.model.statistics.response.ManagerOrderCountResponse;
import com.upedge.common.model.statistics.response.ManagerPackageStatisticsResponse;
import com.upedge.common.model.store.request.StoreApiRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 海桐
 */
@FeignClient(value = ServiceNameConstants.OMS_SERVICE,fallbackFactory = OmsFeignClientFallbackFactory.class,decode404 = true)
public interface OmsFeignClient  {

    @PostMapping("/orderCommon/checkStock")
    public void checkStock(@RequestBody OrderItemQuantityDto orderItemQuantityDto);

    @PostMapping("/orderCommon/updateStockState")
    public int updateStockState(OrderStockStateUpdateRequest request);

    @PostMapping("/order/updateWarehouse/{methodId}")
    public BaseResponse updateWarehouseByMethod(@PathVariable Long methodId);

    @PostMapping("/stock/order/overseaWarehouseReceipt")
    public BaseResponse orderConfirmReceipt(@RequestBody StockOrderVo stockOrderVo);

    @PostMapping("/orderItem/updateImageNameByStoreVariantId")
    public BaseResponse updateImageNameByStoreVariantId(@RequestBody OrderItemUpdateImageNameRequest request);

    @PostMapping("/orderItem/variantPreSaleQuantity")
    public List<VariantPreSaleQuantity> selectVariantPreSaleQuantity(@RequestBody List<Long> variantIds);

    @PostMapping("/orderItem/purchaseItems")
    public List<PurchaseAdviceItemVo> purchaseItems();
    /**
     * 客户经理包裹统计
     * @param request
     * @return
     */
    @PostMapping("/packageDailyCount/manager/statistics")
    public ManagerPackageStatisticsResponse managerPackageStatistics(@RequestBody ManagerPackageStatisticsRequest request);

    //客户下单排名
    @PostMapping("/orderDailyPayCount/customer/statistics")
    public BaseResponse customerPayOrderStatistics(@RequestBody OrderStatisticsRequest request);

    /**
     * 客户经理订单统计
     * @param request
     * @return
     */
    @PostMapping("/orderDailyPayCount/manager/statistics")
    public BaseResponse managerOrderStatistics(@RequestBody OrderStatisticsRequest request);

    @PostMapping("/cart/add")
    public BaseResponse cartAdd(@RequestBody @Valid CartAddRequest request);

    @PostMapping("/cart/selectByIds")
    public List<CartVo> selectByIds(@RequestBody CartSelectByIdsRequest request);

    @PostMapping("/cart/submitByIds")
    public BaseResponse submitByIds(@RequestBody CartSubmitRequest request);

    @PostMapping("/storeOrder/shopify/update")
    public BaseResponse updateShopifyOrder(@RequestBody StoreApiRequest request);

    @PostMapping("/storeOrder/woocommerce/update")
    public BaseResponse woocommerceOrderUpdate(@RequestBody StoreApiRequest request);

    @PostMapping("/storeOrder/shoplazza/update")
    public BaseResponse updateShoplazzaOrder(@RequestBody StoreApiRequest request);

    @PostMapping("/order/pay/rollback")
    BaseResponse orderByRollback(@RequestBody PaypalOrder paypalOrder);

    @PostMapping("/order/pay/paypal/completed")
    public BaseResponse orderPaypalCompleted(@RequestBody @Valid PaypalPayment paypalPayment);

    @PostMapping("/wholesaleOrder/pay/paypal/completed")
    public BaseResponse wholesalePaypalCompleted(@RequestBody @Valid PaypalPayment paypalPayment);

    @GetMapping("/storeOrderItem/sales")
    public BaseResponse storeProductSales();

    @PostMapping("/stock/pay/paypal/completed")
    public BaseResponse stockPaypalCompleted(@RequestBody @Valid PaypalPayment paypalPayment);

    /**
     * 查询店铺下所有订单
     * @param storeId
     * @return
     */
    @PostMapping("/storeOrder/{storeId}/order")
    public BaseResponse storeOrderCount(@PathVariable("storeId") Long storeId);

    /**
     * 查寻客户经理的营收额和客户下单数
     */
    @PostMapping("/order/ManagerActual")
    public BaseResponse getManagerActual(@RequestBody ManagerActualRequest request);

    /**
     * 根据variant_sku分组查询商品数量
     */
    @RequestMapping(value="/customer/stock/list", method=RequestMethod.GET)
    public BaseResponse customerStockNum();
    /**
     * 客户经理指定时间内的发货订单数量
     * @param request
     * @return
     */
    @PostMapping("/packageOrderInfo/manager/orderCount")
    public ManagerOrderCountResponse managerDateOrderCount(ManagerPackageStatisticsRequest request);

    /**
     * 本月客户经理付款批发订单金额
     * @param allOrderAmountVo
     * @return
     */
    @PostMapping("/wholesaleOrder/amountByManagerCodeSet")
    BaseResponse getWholeSaleOrderAmountByManagerCodeSet(@RequestBody AllOrderAmountVo allOrderAmountVo);

    @PostMapping("/order/amountByManagerCodeSet")
    BaseResponse getNormalOrderAmountByManagerCodeSet(@RequestBody AllOrderAmountVo allOrderAmountVo);

    /**
     * 获取某月客户经理普通订单下单客户数
     * @param allOrderAmountVo
     * @return
     */
    @PostMapping("/order/normalOrderCount")
    BaseResponse getNormalOrderCount(@RequestBody AllOrderAmountVo allOrderAmountVo);

    /**
     * 获取某月客户经理批发订单下单客户数
     * @param allOrderAmountVo
     * @return
     */
    @PostMapping("/wholesaleOrder/wholeSaleOrderCount")
    BaseResponse getWholeSaleOrderCount(@RequestBody AllOrderAmountVo allOrderAmountVo);

    @PostMapping("order/customerOrderStatistical/{customerId}")
    BaseResponse getCustomerOrderStatistical(@PathVariable Long customerId);

    /**
     * 根据时间区间和客户经理code  查询订单数量和金额 按照订单类型分组
     */
    @PostMapping("/orderDailyPayCount/selectCurrMonthSaleAmount")
    BaseResponse selectCurrMonthSaleAmount(@RequestBody OrderDailyCountRequest request);

    @PostMapping("/orderCommon/uploadPaymentIdOnMq")
    void uploadPaymentIdOnMq(@RequestBody UplodaSaiheOnMqVo uplodaSaiheOnMq);
}
