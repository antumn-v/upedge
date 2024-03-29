package com.upedge.sms.modules.overseaWarehouse.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.request.*;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderService{

    BaseResponse confirmReceipt(Long orderId, Session session);

    BaseResponse updateTrackingCode(@RequestBody@Valid OverseaWarehouseServiceOrderUpdateTrackingRequest request);

    BaseResponse createFpxInbound(Long id,Session session);

    List<OverseaWarehouseServiceOrderVo> selectAllUnPaidList();

    List<OverseaWarehouseServiceOrderVo> orderList(OverseaWarehouseServiceOrderListRequest request);

    BaseResponse orderPay(OverseaWarehouseServiceOrderPayRequest request,Session session);

    OverseaWarehouseServiceOrderVo orderDetail(Long orderId);

    BaseResponse create(OverseaWarehouseServiceOrderCreateRequest request, Session session);

    OverseaWarehouseServiceOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OverseaWarehouseServiceOrder record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrder record);

    int insert(OverseaWarehouseServiceOrder record);

    int insertSelective(OverseaWarehouseServiceOrder record);

    List<OverseaWarehouseServiceOrder> select(Page<OverseaWarehouseServiceOrder> record);

    long count(Page<OverseaWarehouseServiceOrder> record);

    void saveTransactionRecord(Long userId, Long orderId);
}

