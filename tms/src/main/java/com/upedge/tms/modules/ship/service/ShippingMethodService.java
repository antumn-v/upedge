package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest;
import com.upedge.common.model.ship.request.ShipMethodPriceRequest;
import com.upedge.common.model.ship.request.ShipMethodSearchRequest;
import com.upedge.common.model.ship.request.ShippingMethodsRequest;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShipMethodNameVo;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.request.ShippingMethodAddRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodListRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodUpdateRequest;
import com.upedge.tms.modules.ship.response.ShippingMethodDisableResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodEnableResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodListResponse;

import java.util.List;

/**
 * @author author
 */
public interface ShippingMethodService{

    BaseResponse addShipMethod(ShippingMethodAddRequest request) throws CustomerException;

    BaseResponse updateShipMethod(ShippingMethodUpdateRequest request,Long id) throws CustomerException;

    ShipMethodBatchSearchResponse batchSearchShipMethods(ShipMethodBatchSearchRequest request);

    List<ShipMethodNameVo> selectMixedShipMethodNamesByCountries(List<String> countries);

    List<ShipDetail> searchShipMethods(ShipMethodSearchRequest request);

    ShipDetail searchShipPriceByMethodId(ShipMethodPriceRequest request);

    ShippingMethod selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ShippingMethod record);

    int updateByPrimaryKeySelective(ShippingMethod record);

    int insert(ShippingMethod record);

    int insertSelective(ShippingMethod record);

    List<ShippingMethod> select(Page<ShippingMethod> record);

    long count(Page<ShippingMethod> record);

    ShippingMethodEnableResponse enableShippingMethod(Long id);

    ShippingMethodDisableResponse disableShippingMethod(Long id) throws CustomerException;

    List<String> listUseAllShippingMethodName();

    ShippingMethod getShippingMethodByName(String methodName);

    List<ShippingMethod> allShippingMethod();

    ShippingMethodListResponse list(ShippingMethodListRequest request);

    ShippingMethodListResponse listShippingMethod(ShippingMethodsRequest request);

    /**
     * 根据赛盒运输id查询admin运输方式
     * @param transportId
     * @return
     */
    BaseResponse getShippingMethodByTransportId(Integer transportId, Long shippingMethodId);

    /**
     * 修改赛盒运输信息时维护冗余字段
     * @param record
     */
    int updateBySaiheTransport(SaiheTransport record);

    boolean sendMq(Long id);
}

