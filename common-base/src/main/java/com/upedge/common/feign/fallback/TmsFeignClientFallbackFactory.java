package com.upedge.common.feign.fallback;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.model.ship.request.*;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.response.ShipMethodSearchResponse;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
//import com.upedge.common.model.tms.ShippingUnitInfoResponse;
import com.upedge.common.model.tms.ShippingUnitInfoResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Slf4j
@Component
public class TmsFeignClientFallbackFactory implements FallbackFactory<TmsFeignClient> {
    @Override
    public TmsFeignClient create(Throwable cause) {
        return new TmsFeignClient() {
            @Override
            public BaseResponse info(Long id) {
                return BaseResponse.failed();
            }

            @Override
            public ShipMethodSearchResponse shipSearch(ShipMethodSearchRequest request) {
                return new ShipMethodSearchResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,new ArrayList<>());
            }

            @Override
            public ShipMethodBatchSearchResponse batchShipSearch(ShipMethodBatchSearchRequest request) {
                return new ShipMethodBatchSearchResponse(ResultCode.FAIL_CODE,null);
            }

            @Override
            public BaseResponse shipMethodPrice(ShipMethodPriceRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse mixShipMethodNameByCountries(CountriesMixShipMethodRequest request) {
                return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,new ArrayList<>());
            }

            @Override
            public BaseResponse listShippingMethod(ShippingMethodsRequest request) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse listArea(AreaListAreaRequest request) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse areaSelect(AreaSelectRequest request) {
                return BaseResponse.failed();
            }

            @Override
            public BaseResponse getShippingMethodByTransportId(ShippingMethodVo shippingMethodVo) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public BaseResponse shippingMethodInfo(Long id) {
                return new BaseResponse(ResultCode.FAIL_CODE,"服务异常");
            }

            @Override
            public ShippingUnitInfoResponse unitInfo(@PathVariable Long id){
                return new ShippingUnitInfoResponse(ResultCode.FAIL_CODE,"服务异常",null,id);
            };
        };
    }
}
