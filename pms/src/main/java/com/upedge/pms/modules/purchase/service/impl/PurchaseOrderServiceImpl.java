package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.trade.param.AlibabaOpenplatformTradeModelNativeLogisticsInfo;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelOrderBaseInfo;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelTradeInfo;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderDao;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderTracking;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderReceiveRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderReceiveRequest.PurchaseOrderItemReceiveDto;
import com.upedge.pms.modules.purchase.service.PurchaseOrderItemService;
import com.upedge.pms.modules.purchase.service.PurchaseOrderService;
import com.upedge.pms.modules.purchase.service.PurchaseOrderTrackingService;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderDao purchaseOrderDao;

    @Autowired
    PurchaseOrderItemService purchaseOrderItemService;

    @Autowired
    PurchaseOrderTrackingService purchaseOrderTrackingService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        PurchaseOrder record = new PurchaseOrder();
        record.setId(id);
        return purchaseOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(PurchaseOrder record) {
        return purchaseOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(PurchaseOrder record) {
        return purchaseOrderDao.insert(record);
    }

    @Override
    public BaseResponse orderReceive(PurchaseOrderReceiveRequest request, Session session) {
        Long orderId = request.getId();
        PurchaseOrder purchaseOrder = selectByPrimaryKey(orderId);
        if (purchaseOrder == null){
            return BaseResponse.failed("订单不存在");
        }
        List<PurchaseOrderItemReceiveDto> itemReceiveDtos = request.getItemReceiveDtos();
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemService.selectByOrderId(orderId);
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
            for (PurchaseOrderItemReceiveDto itemReceiveDto : itemReceiveDtos) {
                if (purchaseOrderItem.getId().equals(itemReceiveDto.getId())){
                    purchaseOrderItem.setReceiveQuantity(itemReceiveDto.getQuantity());
                }
            }
        }


        return null;
    }

    @Override
    public BaseResponse refreshFrom1688(Long id) {
        PurchaseOrder purchaseOrder = selectByPrimaryKey(id);
        if (null == purchaseOrder){
            return BaseResponse.failed("订单不存在");
        }
        AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo = null;
        try {
            alibabaOpenplatformTradeModelTradeInfo = Ali1688Service.orderDetail(Long.parseLong(purchaseOrder.getPurchaseId()),null);
        } catch (CustomerException e) {
            return BaseResponse.failed(e.getMessage());
        }
        purchaseOrder = new PurchaseOrder();
        AlibabaOpenplatformTradeModelOrderBaseInfo baseInfo = alibabaOpenplatformTradeModelTradeInfo.getBaseInfo();
        purchaseOrder.setPurchaseStatus(baseInfo.getStatus());
        purchaseOrder.setReceiveTime(baseInfo.getReceivingTime());
        purchaseOrder.setDeliveredTime(baseInfo.getAllDeliveredTime());
        purchaseOrder.setRemark(baseInfo.getRemark());
        purchaseOrder.setProductAmount(baseInfo.getSumProductPayment());
        purchaseOrder.setShipPrice(baseInfo.getShippingFee());
        purchaseOrder.setDiscountAmount(new BigDecimal(baseInfo.getDiscount()));
        purchaseOrder.setUpdateTime(new Date());
        AlibabaOpenplatformTradeModelNativeLogisticsInfo alibabaOpenplatformTradeModelNativeLogisticsInfo = alibabaOpenplatformTradeModelTradeInfo.getNativeLogistics();
        if (alibabaOpenplatformTradeModelNativeLogisticsInfo != null){
            List<AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo> logisticsItemsInfos = Arrays.asList(alibabaOpenplatformTradeModelNativeLogisticsInfo.getLogisticsItems());
            if (ListUtils.isNotEmpty(logisticsItemsInfos)){
                List<PurchaseOrderTracking> orderTrackingList = new ArrayList<>();
                StringBuffer code = new StringBuffer();
                for (int i = 0; i < logisticsItemsInfos.size(); i++) {
                    AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo logisticsItemsInfo = logisticsItemsInfos.get(i);
                    if (i == 0){
                        code = code.append(logisticsItemsInfo.getLogisticsCode());
                    }else {
                        code = code.append(",").append(logisticsItemsInfo.getLogisticsCode());
                    }
                    PurchaseOrderTracking purchaseOrderTracking = new PurchaseOrderTracking(id, purchaseOrder.getPurchaseId(), logisticsItemsInfo.getLogisticsCode(), logisticsItemsInfo.getLogisticsCompanyName());
                    purchaseOrderTracking.setCreateTime(logisticsItemsInfo.getDeliveredTime());
                    orderTrackingList.add(purchaseOrderTracking);
                }
                purchaseOrder.setTrackingCode(code.toString());
                purchaseOrderTrackingService.insertByBatch(orderTrackingList);
            }

        }
        purchaseOrder.setId(id);
        updateByPrimaryKeySelective(purchaseOrder);
        return BaseResponse.success();
    }

    @Override
    public List<PurchaseOrderVo> orderList(PurchaseOrderListRequest request) {
        List<PurchaseOrderVo> purchaseOrderVos = new ArrayList<>();
        List<PurchaseOrder> purchaseOrders = select(request);
        if (ListUtils.isNotEmpty(purchaseOrders)){
            List<Long> orderIds = new ArrayList<>();
            purchaseOrders.forEach(purchaseOrder -> {
                orderIds.add(purchaseOrder.getId());
            });
            List<PurchaseOrderItem> orderItems = purchaseOrderItemService.selectByOrderIds(orderIds);
            List<PurchaseOrderTracking> orderTrackings = purchaseOrderTrackingService.selectByOrderIds(orderIds);
            for (PurchaseOrder purchaseOrder : purchaseOrders) {
                PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
                BeanUtils.copyProperties(purchaseOrder,purchaseOrderVo);
                List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
                for (PurchaseOrderItem orderItem : orderItems) {
                    if (orderItem.getOrderId().equals(purchaseOrder.getId())){
                        purchaseOrderItems.add(orderItem);
                    }
                }
                for (PurchaseOrderTracking orderTracking : orderTrackings) {
                    if (orderTracking.getPurchaseOrderId().equals(purchaseOrder.getId())){
                        purchaseOrderVo.getTrackingList().add(orderTracking);
                    }
                }
                orderItems.removeAll(purchaseOrderItems);
                purchaseOrderVo.setPurchaseItemVos(purchaseOrderItems);
                purchaseOrderVos.add(purchaseOrderVo);
            }
        }
        return purchaseOrderVos;
    }

    /**
     *
     */
    public PurchaseOrder selectByPrimaryKey(Long id){
        PurchaseOrder record = new PurchaseOrder();
        record.setId(id);
        return purchaseOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(PurchaseOrder record) {
        return purchaseOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(PurchaseOrder record) {
        return purchaseOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<PurchaseOrder> select(Page<PurchaseOrder> record){
        record.initFromNum();
        return purchaseOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<PurchaseOrder> record){
        return purchaseOrderDao.count(record);
    }

}