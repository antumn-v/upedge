package com.upedge.common.feign;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ServiceNameConstants;
import com.upedge.common.feign.fallback.TmsFeignClientFallbackFactory;
import com.upedge.common.model.ship.request.*;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.response.ShipMethodSearchResponse;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
//import com.upedge.common.model.tms.ShippingUnitInfoResponse;
import com.upedge.common.model.tms.ShippingUnitInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 海桐
 */
@FeignClient(value = ServiceNameConstants.TMS_SERVICE,fallbackFactory = TmsFeignClientFallbackFactory.class,decode404 = true)
public interface TmsFeignClient {

    @RequestMapping(value="/shippingMethod/info/{id}", method=RequestMethod.GET)
    public BaseResponse info(@PathVariable Long id);

    @PostMapping("/shippingMethod/search")
    public ShipMethodSearchResponse shipSearch(@RequestBody ShipMethodSearchRequest request);

    @PostMapping("/shippingMethod/search/batch")
    public ShipMethodBatchSearchResponse batchShipSearch(@RequestBody ShipMethodBatchSearchRequest request);

    @PostMapping("/shippingMethod/search/price")
    public BaseResponse shipMethodPrice(@RequestBody ShipMethodPriceRequest request);

    @PostMapping("/shippingMethod/search/mixShipMethodByCountries")
    public BaseResponse mixShipMethodNameByCountries(@RequestBody CountriesMixShipMethodRequest request);

    @RequestMapping(value="/shippingMethod/listShippingMethod", method=RequestMethod.GET)
    public BaseResponse listShippingMethod(@RequestBody ShippingMethodsRequest request);

    @RequestMapping(value="/area/listArea", method=RequestMethod.GET)
    public BaseResponse listArea(@RequestBody AreaListAreaRequest request);
    @PostMapping("/area/select")
    public BaseResponse areaSelect(@RequestBody AreaSelectRequest request);

    /**
     * 根据赛盒运输id查询admin运输方式
     * @return
     */
    @RequestMapping(value="/shippingMethod/getShippingMethodByTransportId", method=RequestMethod.GET)
    BaseResponse getShippingMethodByTransportId(@RequestBody ShippingMethodVo shippingMethodVo);

    @RequestMapping(value="/shippingMethod/info/{id}", method=RequestMethod.GET)
    BaseResponse shippingMethodInfo(@PathVariable Long id);

    /**
     * 单个运输单元查询
     * @param id
     * @return
     */
    @RequestMapping(value="/shippingUnit/unit/info/{id}", method=RequestMethod.POST)
    ShippingUnitInfoResponse unitInfo(@PathVariable Long id);

}
