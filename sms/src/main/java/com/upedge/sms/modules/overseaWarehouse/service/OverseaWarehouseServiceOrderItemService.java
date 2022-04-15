package com.upedge.sms.modules.overseaWarehouse.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemUploadFpxRequest;

import java.util.List;

/**
 * @author gx
 */
public interface OverseaWarehouseServiceOrderItemService{

    BaseResponse uploadFpx(OverseaWarehouseServiceOrderItemUploadFpxRequest request, Session session);

    int updateWarehouseSkuByOrderId(Long orderId);

    List<OverseaWarehouseServiceOrderItem> selectByOrderId(Long orderId);

    OverseaWarehouseServiceOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OverseaWarehouseServiceOrderItem record);

    int updateByPrimaryKeySelective(OverseaWarehouseServiceOrderItem record);

    int insert(OverseaWarehouseServiceOrderItem record);

    int insertByBatch(List<OverseaWarehouseServiceOrderItem> orderItems);

    int insertSelective(OverseaWarehouseServiceOrderItem record);

    List<OverseaWarehouseServiceOrderItem> select(Page<OverseaWarehouseServiceOrderItem> record);

    long count(Page<OverseaWarehouseServiceOrderItem> record);
}

