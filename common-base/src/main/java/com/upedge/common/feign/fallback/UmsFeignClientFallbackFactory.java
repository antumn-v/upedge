package com.upedge.common.feign.fallback;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.UmsFeignClient;
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
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author 海桐
 */
@Slf4j
@Component
public class UmsFeignClientFallbackFactory implements FallbackFactory<UmsFeignClient> {
    @Override
    public UmsFeignClient create(Throwable cause) {
        return new UmsFeignClient() {

            @Override
            public BaseResponse addAffiliateCommissionRecord(CommissionRecordVo commissionRecordVo) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse returnOrderPayAmount(ReturnOrderPayAmountToAccountRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse saveTransactionDetails(PaymentDetail detail) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse getCustomerManager(Long customerId) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse customerInfo(Long customerId) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse storeSearch(StoreSearchRequest request) {
                return BaseResponse.failed("system error");
            }

            @Override
            public BaseResponse getCustomerAe(Long customerId) {
                return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
            }

            @Override
            public BaseResponse queryAeToken() {
                return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
            }

            @Override
            public BaseResponse getSettingValueByName(CustomerSettingValuegRequest request) {
                return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
            }

            @Override
            public BaseResponse getPaypalOrderUrl(PaypalOrder paypalOrder) {
                return BaseResponse.failed("module error");
            }

            @Override
            public BaseResponse executePaypalOrder(PaypalExecuteRequest request) {
                return BaseResponse.failed();
            }


            @Override
            public BaseResponse accountPayment(AccountPaymentRequest request) {
                return BaseResponse.failed("module error");
            }

            @Override
            public BaseResponse selectMqLog(@Valid MqMessageLog log) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse saveMqLog(@Valid MqMessageLog log) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse updateMqLog(@Valid MqMessageLog log) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse sendMessage(Message message) {
                return BaseResponse.failed("服务异常");
            }

            @Override
            public BaseResponse accountOrderRefunded(AccountOrderRefundedRequest request) {
                return BaseResponse.failed("module error");
            }

            @Override
            public BaseResponse getCurrencyRate(String code) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse storeInfo(Long id) {
                return BaseResponse.failed("module error");
            }

            @Override
            public BaseResponse accountLogPayInfo(String transactionId) {
                return BaseResponse.failed("module error");
            }


            @Override
            public BaseResponse customerByIds(CustomerByIdsRequest request) {
                return BaseResponse.failed("服务异常");
            }

            @Override
            public BaseResponse getBillingAddress(Long customerId) throws CustomerException {
                return BaseResponse.failed("服务异常");
            }

            @Override
            public BaseResponse userInfoSelect(UserInfoSelectRequest request) {
                return BaseResponse.failed("服务异常");
            }

            @Override
            public BaseResponse managerCustomerList(ManagerCustomerListRequest managerCustomerListRequest) {
                return BaseResponse.failed("服务异常");
            }

            @Override
            public BaseResponse getManagerByOrderSourceId(Long orderSourceId) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse packageMonthOrderBenefitsListAmount(OrderBenefitsRequest orderBenefitsRequest) {
                return BaseResponse.failed();
            }
            @Override
            public BaseResponse packageMonthWholeSaleOrderBenefitsListAmount(OrderBenefitsRequest orderBenefitsRequest) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse customerAffiliateInfo(Long customerId) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse selectAccountLogByOrder(OrderAccountLogRequest accountLogQuery) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse accountLogPayInfoByTransactionDetail(TransactionDetail transactionDetail) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse saveAccessHistory(@Valid AccessHistoryAddRequestVo request) {
                return BaseResponse.failed();
            }

        };
    }
}
