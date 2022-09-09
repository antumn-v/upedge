package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderReceiveRequest;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;

import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderService{

    void checkOrderReceiveQuantity(Long id);

    BaseResponse orderReceive(PurchaseOrderReceiveRequest request, Session session) throws Exception;

    BaseResponse refreshFrom1688(Long id);

    List<PurchaseOrderVo> orderList(PurchaseOrderListRequest request);

    PurchaseOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    List<PurchaseOrder> select(Page<PurchaseOrder> record);

    long count(Page<PurchaseOrder> record);

    long countPurchaseOrder(PurchaseOrderListRequest request);
}

