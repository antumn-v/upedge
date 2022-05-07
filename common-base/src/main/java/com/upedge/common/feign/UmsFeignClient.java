package com.upedge.common.feign;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ServiceNameConstants;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.fallback.UmsFeignClientFallbackFactory;
import com.upedge.common.model.access.vo.AccessHistoryAddRequestVo;
import com.upedge.common.model.account.AccountOrderRefundedRequest;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.request.PaypalExecuteRequest;
import com.upedge.common.model.account.request.ReturnOrderPayAmountToAccountRequest;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.store.request.StoreSearchRequest;
import com.upedge.common.model.user.request.*;
import com.upedge.common.model.user.vo.CommissionRecordVo;
import org.apache.rocketmq.common.message.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 海桐
 */
@FeignClient(name = ServiceNameConstants.USER_SERVICE,fallbackFactory = UmsFeignClientFallbackFactory.class,decode404 = true)
public interface UmsFeignClient {

    @PostMapping("/affiliate/addCommissionRecord")
    public BaseResponse addAffiliateCommissionRecord(@RequestBody@Valid CommissionRecordVo commissionRecordVo);

    @PostMapping("/account/returnOrderPayAmount")
    public BaseResponse returnOrderPayAmount(@RequestBody ReturnOrderPayAmountToAccountRequest request);

    @PostMapping("/account/saveTransactionDetails")
    public BaseResponse saveTransactionDetails(@RequestBody PaymentDetail detail);

    /**
     * 获取客户的客户经理信息
     * @param customerId
     * @return
     */
    @GetMapping("/customerManager/get/{customerId}")
    public BaseResponse getCustomerManager(@PathVariable Long customerId);

    /**
     * 获取客户信息
     * @param customerId
     * @return
     */
    @GetMapping("/customer/info/{customerId}")
    public BaseResponse customerInfo(@PathVariable Long customerId);

    /**
     * 店铺查询
     * @param request
     * @return
     */
    @PostMapping("/store/search")
    public BaseResponse storeSearch(@RequestBody StoreSearchRequest request);

    /**
     * 获取速卖通授权信息
     * @param customerId
     * @return
     */
    @GetMapping("/customer/{customerId}/ae")
    public BaseResponse getCustomerAe(@PathVariable Long customerId);

    /**
     * 获取最新的Token
     * @return
     */
    @GetMapping("/customer/aeToken")
    public BaseResponse queryAeToken();

    @GetMapping("/customer/setting/settingValue")
    public BaseResponse getSettingValueByName(@RequestBody CustomerSettingValuegRequest request);

    @PostMapping("/paypal/order/url")
    public BaseResponse getPaypalOrderUrl(@RequestBody PaypalOrder paypalOrder);

    @PostMapping("/paypal/order/execute")
    public BaseResponse executePaypalOrder(@RequestBody PaypalExecuteRequest request);

    @PostMapping("/account/payment")
    public BaseResponse accountPayment(@RequestBody AccountPaymentRequest request);

    @PostMapping("/mq/log/select")
    public BaseResponse selectMqLog(@RequestBody @Valid MqMessageLog log);

    @PostMapping("/mq/log/save")
    public BaseResponse saveMqLog(@RequestBody @Valid MqMessageLog log);

    @PostMapping("/mq/log/update")
    public BaseResponse updateMqLog(@RequestBody @Valid MqMessageLog log);

    @PostMapping("/mq/message/send")
    public BaseResponse sendMessage(@RequestBody Message message);

    @PostMapping("/account/internal-service/orderRefunded")
    BaseResponse accountOrderRefunded(@RequestBody AccountOrderRefundedRequest request);

    @GetMapping("/currency/{code}/rate")
    public BaseResponse getCurrencyRate(@PathVariable String code);

    @RequestMapping(value="/store/info/{id}", method=RequestMethod.GET)
    BaseResponse storeInfo(@PathVariable Long id);

    @PostMapping("/account/log/payInfo/{transactionId}")
    BaseResponse accountLogPayInfo(@PathVariable String transactionId);

    @RequestMapping(value="/customer/customerByIds", method=RequestMethod.POST)
    BaseResponse customerByIds(@RequestBody CustomerByIdsRequest request);

    /**
     * 查询客户账单地址信息
     */
    @GetMapping("/customer/{customerId}/address/billing")
    public BaseResponse getBillingAddress(@PathVariable Long customerId) throws CustomerException;

    /**
     * 根据 id / loginname  查询客户信息
     */
    @PostMapping("/user/info/select")
    public BaseResponse userInfoSelect(@RequestBody UserInfoSelectRequest request);

    /**
     * 客户经理个人客户列表
     * @return
     */
    @PostMapping("/customerManager/customer/list")
     BaseResponse managerCustomerList(@RequestBody ManagerCustomerListRequest managerCustomerListRequest);

    @PostMapping("/manager/getManagerByOrderSourceId/{orderSourceId}")
    BaseResponse getManagerByOrderSourceId(@PathVariable Long orderSourceId);

    @PostMapping("/affiliateCommissionRecord/packageMonthOrderBenefitsListAmount")
    BaseResponse packageMonthOrderBenefitsListAmount(@RequestBody OrderBenefitsRequest orderBenefitsRequest);

    @PostMapping("/affiliateCommissionRecord/packageMonthWholeSaleOrderBenefitsListAmount")
    BaseResponse packageMonthWholeSaleOrderBenefitsListAmount(@RequestBody OrderBenefitsRequest orderBenefitsRequest);

    @PostMapping("/adminAffiliate/customerAffiliateInfo/{customerId}")
    BaseResponse customerAffiliateInfo(@PathVariable Long customerId);

    @RequestMapping("/account/log/queryCount/accountLog")
    BaseResponse selectAccountLogByOrder(@Validated @RequestBody OrderAccountLogRequest accountLogQuery);

    @PostMapping("/account/log/payInfo/accountLogPayInfoByTransactionDetail")
    BaseResponse accountLogPayInfoByTransactionDetail(@RequestBody TransactionDetail transactionDetail);

    @PostMapping("/accessHistory/save")
    BaseResponse saveAccessHistory(@RequestBody @Valid AccessHistoryAddRequestVo request);
}
