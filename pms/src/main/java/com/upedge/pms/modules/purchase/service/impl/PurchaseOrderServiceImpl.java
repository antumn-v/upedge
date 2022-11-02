package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.alibaba.trade.param.*;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.dao.PurchaseOrderDao;
import com.upedge.pms.modules.purchase.dto.PurchaseOrderItemReceiveDto;
import com.upedge.pms.modules.purchase.dto.PurchaseOrderListDto;
import com.upedge.pms.modules.purchase.entity.*;
import com.upedge.pms.modules.purchase.request.*;
import com.upedge.pms.modules.purchase.service.*;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
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
    VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    PurchaseOrderImRecordService purchaseOrderImRecordService;

    @Autowired
    PurchaseOrderImItemService purchaseOrderImItemService;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductPurchaseInfoService productPurchaseInfoService;


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

    @Transactional
    @Override
    public BaseResponse customCreate(PurchaseOrderCustomCreateRequest request, Session session) {
        List<PurchasePlan> purchasePlans = request.getPurchasePlans();

        Map<Long,Integer> variantQuantityMap = new HashMap<>();
        List<Long> variantIds = new ArrayList<>();
        for (PurchasePlan purchasePlan : purchasePlans) {
            variantIds.add(purchasePlan.getVariantId());
            variantQuantityMap.put(purchasePlan.getVariantId(),purchasePlan.getQuantity());
        }
        List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);
        if (ListUtils.isEmpty(productVariants)){
            return BaseResponse.failed("商品信息不存在");
        }
        Set<String> purchaseSkus = new HashSet<>();
        for (ProductVariant productVariant : productVariants) {
            purchaseSkus.add(productVariant.getPurchaseSku());
        }

        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);

        Map<String, List<AlibabaTradeFastCargo>> supplierCargosMap = new HashMap<>();

        for (ProductVariant productVariant : productVariants) {

            for (ProductPurchaseInfo productPurchaseInfo : productPurchaseInfos) {
                String supplierName = productPurchaseInfo.getSupplierName();
                if (productPurchaseInfo.getPurchaseSku().equals(productVariant.getPurchaseSku())) {
                    AlibabaTradeFastCargo alibabaTradeFastCargo = new AlibabaTradeFastCargo();
                    alibabaTradeFastCargo.setOfferId(Long.parseLong(productPurchaseInfo.getPurchaseLink()));
                    alibabaTradeFastCargo.setSpecId(productPurchaseInfo.getSpecId());
                    alibabaTradeFastCargo.setQuantity(variantQuantityMap.get(productVariant.getId()).doubleValue());

                    if (!supplierCargosMap.containsKey(supplierName)) {
                        supplierCargosMap.put(supplierName, new ArrayList<AlibabaTradeFastCargo>());
                    }
                    supplierCargosMap.get(supplierName).add(alibabaTradeFastCargo);
                }
            }
        }

        return null;
    }

    @Override
    public void completeOrderInfo(Long id) {
        PurchaseOrder purchaseOrder = selectByPrimaryKey(id);
        if (null == purchaseOrder) {
            return ;
        }
        String purchaseId = purchaseOrder.getPurchaseId();
        AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo = null;
        try {
            alibabaOpenplatformTradeModelTradeInfo = Ali1688Service.orderDetail(Long.parseLong(purchaseId), null);
        } catch (CustomerException e) {
            e.printStackTrace();
            return;
        }
        updateBaseInfo(id,purchaseOrder.getPurchaseId(),purchaseOrder.getTrackingCode(),alibabaOpenplatformTradeModelTradeInfo);
        completeItemPrice(id, alibabaOpenplatformTradeModelTradeInfo);
    }

    @Override
    public int updateProductAmount(Long id, BigDecimal productAmount) {
        return purchaseOrderDao.updateProductAmount(id, productAmount);
    }

    @Override
    public BaseResponse updateEditState(PurchaseOrderEditStateUpdateRequest request, Session session) {
        Long id = request.getId();
        Integer state = request.getEditState();
        PurchaseOrder purchaseOrder = selectByPrimaryKey(id);
        if (purchaseOrder.getEditState() == 1){
            return BaseResponse.failed("已提交的订单无法修改状态");
        }
        if (state == purchaseOrder.getEditState()){
            return BaseResponse.success();
        }
        purchaseOrderDao.updateEditState(id,state);
        return BaseResponse.success();
    }

    @Override
    public void checkOrderReceiveQuantity(Long id) {
        Integer purchaseQuantity = 0;

        Integer receiveQuantity = 0;

        List<PurchaseOrderItem> items = purchaseOrderItemService.selectByOrderId(id);
        for (PurchaseOrderItem item : items) {
            purchaseQuantity += item.getQuantity();
            receiveQuantity += item.getReceiveQuantity();
        }
        if (receiveQuantity == 0) {
            return;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        purchaseOrder.setUpdateTime(new Date());
        if (receiveQuantity < purchaseQuantity) {
            purchaseOrder.setPurchaseState(1);
        } else {
            purchaseOrder.setPurchaseState(2);
        }
        updateByPrimaryKeySelective(purchaseOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse orderReceive(PurchaseOrderReceiveRequest request, Session session) throws Exception {
        BaseResponse response = orderReceiveItemIm(request, session);
        if (response.getCode() == ResultCode.SUCCESS_CODE) {
            checkOrderReceiveQuantity(request.getOrderId());
        }
        return response;
    }


    BaseResponse orderReceiveItemIm(PurchaseOrderReceiveRequest request, Session session) throws Exception {
        Long orderId = request.getOrderId();
        PurchaseOrder purchaseOrder = selectByPrimaryKey(orderId);
        if (purchaseOrder == null || purchaseOrder.getEditState() != 1) {
            return BaseResponse.failed("订单不存在或编辑未完成");
        }
        List<PurchaseOrderItemReceiveDto> itemReceiveDtos = request.getItemReceiveDtos();
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemService.selectByOrderId(orderId);

        Long relateId = IdGenerate.nextId();

        PurchaseOrderImRecord purchaseOrderImRecord = new PurchaseOrderImRecord();
        purchaseOrderImRecord.setId(relateId);
        purchaseOrderImRecord.setPurchaseOrderId(orderId);
        purchaseOrderImRecord.setTrackingCode(request.getTrackingCode());
        purchaseOrderImRecord.setCreateTime(new Date());
        purchaseOrderImRecord.setOperatorId(session.getId());
        purchaseOrderImRecordService.insert(purchaseOrderImRecord);

        VariantStockExImRecordUpdateRequest recordUpdateRequest = new VariantStockExImRecordUpdateRequest();

        recordUpdateRequest.setProcessType(VariantWarehouseStockRecord.PURCHASE_ADD);
        recordUpdateRequest.setWarehouseCode(purchaseOrder.getWarehouseCode());
        recordUpdateRequest.setTrackingCode(recordUpdateRequest.getTrackingCode());
        a:
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
            for (PurchaseOrderItemReceiveDto itemReceiveDto : itemReceiveDtos) {
                if (purchaseOrderItem.getId().equals(itemReceiveDto.getItemId())
                        && itemReceiveDto.getQuantity() > 0) {
                    recordUpdateRequest.setQuantity(itemReceiveDto.getQuantity());
                    recordUpdateRequest.setVariantSku(purchaseOrderItem.getVariantSku());
                    recordUpdateRequest.setRelateId(purchaseOrderItem.getId());
                    BaseResponse response = variantWarehouseStockService.variantStockIm(recordUpdateRequest, session);
                    if (response.getCode() != ResultCode.SUCCESS_CODE) {
                        throw new Exception(response.getMsg());
                    }
                    purchaseOrderItem.setReceiveQuantity(itemReceiveDto.getQuantity() + purchaseOrderItem.getReceiveQuantity());
                    purchaseOrderItemService.updateByPrimaryKeySelective(purchaseOrderItem);
                    continue a;
                }
            }
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse refreshFrom1688(Long id) {
        PurchaseOrder purchaseOrder = selectByPrimaryKey(id);
        if (null == purchaseOrder) {
            return BaseResponse.failed("订单不存在");
        }
        try {
            updateBaseInfo(id, purchaseOrder.getPurchaseId(), purchaseOrder.getTrackingCode());
        } catch (CustomerException e) {
            return BaseResponse.failed(e.getMessage());
        }

        List<AlibabaLogisticsOpenPlatformLogisticsTrace> alibabaLogisticsOpenPlatformLogisticsTraces = null;
        try {
            alibabaLogisticsOpenPlatformLogisticsTraces = Ali1688Service.orderShipDetail(Long.parseLong(purchaseOrder.getPurchaseId()), null);
        } catch (CustomerException e) {
        }
        purchaseOrderTrackingService.updateOrderTrackingLatestUpdateInfo(id, alibabaLogisticsOpenPlatformLogisticsTraces);

        return BaseResponse.success();
    }


    void updateBaseInfo(Long id, String purchaseId, String trackingCode) throws CustomerException {
        if (StringUtils.isBlank(trackingCode)){
            trackingCode = "";
        }
        AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo = null;
        try {
            alibabaOpenplatformTradeModelTradeInfo = Ali1688Service.orderDetail(Long.parseLong(purchaseId), null);
        } catch (CustomerException e) {
            throw new CustomerException(e.getMessage());
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        AlibabaOpenplatformTradeModelOrderBaseInfo baseInfo = alibabaOpenplatformTradeModelTradeInfo.getBaseInfo();
        purchaseOrder.setPurchaseStatus(baseInfo.getStatus());
        purchaseOrder.setReceiveTime(baseInfo.getReceivingTime());
        purchaseOrder.setDeliveredTime(baseInfo.getAllDeliveredTime());
        purchaseOrder.setRemark(baseInfo.getRemark());
        purchaseOrder.setShipPrice(baseInfo.getShippingFee());
        purchaseOrder.setDiscountAmount(new BigDecimal(baseInfo.getDiscount()));
        purchaseOrder.setUpdateTime(new Date());
        AlibabaOpenplatformTradeModelNativeLogisticsInfo alibabaOpenplatformTradeModelNativeLogisticsInfo = alibabaOpenplatformTradeModelTradeInfo.getNativeLogistics();
        if (alibabaOpenplatformTradeModelNativeLogisticsInfo != null) {
            List<AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo> logisticsItemsInfos = Arrays.asList(alibabaOpenplatformTradeModelNativeLogisticsInfo.getLogisticsItems());
            if (ListUtils.isNotEmpty(logisticsItemsInfos)) {
                List<PurchaseOrderTracking> orderTrackingList = new ArrayList<>();
                StringBuffer code = new StringBuffer();
                for (int i = 0; i < logisticsItemsInfos.size(); i++) {
                    AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo logisticsItemsInfo = logisticsItemsInfos.get(i);
                    if (logisticsItemsInfo.getLogisticsCode() == null){
                        continue;
                    }
                    if (code == null) {
                        code = code.append(logisticsItemsInfo.getLogisticsCode());
                    } else {
                        code = code.append(",").append(logisticsItemsInfo.getLogisticsCode());
                    }
                    if (trackingCode.contains(logisticsItemsInfo.getLogisticsCode())) {
                        continue;
                    }
                    PurchaseOrderTracking purchaseOrderTracking = new PurchaseOrderTracking(id, purchaseId, logisticsItemsInfo.getLogisticsCode(), logisticsItemsInfo.getLogisticsCompanyName());
                    purchaseOrderTracking.setUpdateTime(logisticsItemsInfo.getDeliveredTime());
                    orderTrackingList.add(purchaseOrderTracking);
                }
                purchaseOrder.setTrackingCode(code.toString());
                purchaseOrderTrackingService.insertByBatch(orderTrackingList);
            }

        }
        purchaseOrder.setId(id);
        updateByPrimaryKeySelective(purchaseOrder);



    }

    void updateBaseInfo(Long id,String purchaseId,String trackingCode,AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo) {


        PurchaseOrder purchaseOrder = new PurchaseOrder();
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
        if (alibabaOpenplatformTradeModelNativeLogisticsInfo != null) {
            AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo[] logisticsInfoLogisticsItems = alibabaOpenplatformTradeModelNativeLogisticsInfo.getLogisticsItems();
            if (logisticsInfoLogisticsItems != null
            && logisticsInfoLogisticsItems.length > 0){
                List<AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo> logisticsItemsInfos = Arrays.asList(alibabaOpenplatformTradeModelNativeLogisticsInfo.getLogisticsItems());
                if (ListUtils.isNotEmpty(logisticsItemsInfos)) {
                    List<PurchaseOrderTracking> orderTrackingList = new ArrayList<>();
                    StringBuffer code = new StringBuffer();
                    for (int i = 0; i < logisticsItemsInfos.size(); i++) {
                        AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo logisticsItemsInfo = logisticsItemsInfos.get(i);

                        if (code == null) {
                            code = code.append(logisticsItemsInfo.getLogisticsCode());
                        } else {
                            code = code.append(",").append(logisticsItemsInfo.getLogisticsCode());
                        }
                        if (trackingCode.contains(logisticsItemsInfo.getLogisticsCode())) {
                            continue;
                        }
                        PurchaseOrderTracking purchaseOrderTracking = new PurchaseOrderTracking(id, purchaseId, logisticsItemsInfo.getLogisticsCode(), logisticsItemsInfo.getLogisticsCompanyName());
                        purchaseOrderTracking.setUpdateTime(logisticsItemsInfo.getDeliveredTime());
                        orderTrackingList.add(purchaseOrderTracking);
                    }
                    purchaseOrder.setTrackingCode(code.toString());
                    purchaseOrderTrackingService.insertByBatch(orderTrackingList);
                }
            }
        }
        purchaseOrder.setId(id);
        updateByPrimaryKeySelective(purchaseOrder);
    }

    void completeItemPrice(Long id, AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo){
        List<AlibabaOpenplatformTradeModelProductItemInfo> productItems = Arrays.asList(alibabaOpenplatformTradeModelTradeInfo.getProductItems());
        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
        for (AlibabaOpenplatformTradeModelProductItemInfo productItem : productItems) {
            PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
            purchaseOrderItem.setOrderId(id);
            purchaseOrderItem.setSpecId(productItem.getSpecId());
            purchaseOrderItem.setPrice(productItem.getPrice());
            purchaseOrderItem.setOriginalPrice(productItem.getPrice());
            purchaseOrderItems.add(purchaseOrderItem);
        }
        purchaseOrderItemService.updatePriceBySpecId(purchaseOrderItems);
    }

    @Override
    public List<PurchaseOrderVo> orderList(PurchaseOrderListRequest request) {
        PurchaseOrderListDto purchaseOrderListDto = request.getT();
        if (null == purchaseOrderListDto){
            purchaseOrderListDto = new PurchaseOrderListDto();
        }
        List<Long> orderIds = new ArrayList<>();
        //判断是否是变体条件查询的订单
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemService.selectByOrderListDto(purchaseOrderListDto);
        //获取变体的订单ID集合
        if (ListUtils.isNotEmpty(purchaseOrderItems)) {
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                if (!orderIds.contains(purchaseOrderItem.getOrderId())) {
                    orderIds.add(purchaseOrderItem.getOrderId());
                }
            }
        }

        String trackCode = purchaseOrderListDto.getTrackingCode();
        List<PurchaseOrderTracking> purchaseOrderTracks = purchaseOrderTrackingService.selectByTrackCode(trackCode);
        if (ListUtils.isNotEmpty(purchaseOrderTracks)){
            for (PurchaseOrderTracking purchaseOrderTrack : purchaseOrderTracks) {
                orderIds.add(purchaseOrderTrack.getPurchaseOrderId());
            }
        }

        purchaseOrderListDto.setIds(orderIds);
        List<PurchaseOrder> purchaseOrders = purchaseOrderDao.selectPurchaseOrders(request);
        if (ListUtils.isEmpty(purchaseOrders)) {
            return new ArrayList<>();
        }
        //如果刚才没查询到变体,但是查询到订单则重新根据订单查询变体
        if (ListUtils.isEmpty(purchaseOrderItems)){
            purchaseOrders.forEach(purchaseOrder -> {
                orderIds.add(purchaseOrder.getId());
            });
            purchaseOrderItems = purchaseOrderItemService.selectByOrderIds(orderIds);
        }

        List<PurchaseOrderTracking> orderTrackings = purchaseOrderTrackingService.selectByOrderIds(orderIds);
        //匹配同个订单下的数据
        List<PurchaseOrderVo> purchaseOrderVos = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            List<PurchaseOrderItem> orderItems = new ArrayList<>();
            PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
            BeanUtils.copyProperties(purchaseOrder, purchaseOrderVo);
            for (PurchaseOrderItem orderItem : purchaseOrderItems) {
                if (orderItem.getOrderId().equals(purchaseOrder.getId())) {
                    orderItems.add(orderItem);
                }
            }
            for (PurchaseOrderTracking orderTracking : orderTrackings) {
                if (orderTracking.getPurchaseOrderId().equals(purchaseOrder.getId())) {
                    purchaseOrderVo.getTrackingList().add(orderTracking);
                }
            }
            purchaseOrderItems.removeAll(orderItems);
            purchaseOrderVo.setPurchaseItemVos(orderItems);
            purchaseOrderVos.add(purchaseOrderVo);
        }

        return purchaseOrderVos;
    }

    /**
     *
     */
    public PurchaseOrder selectByPrimaryKey(Long id) {
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
    public List<PurchaseOrder> select(Page<PurchaseOrder> page) {
        page.initFromNum();
        return purchaseOrderDao.select(page);
    }

    /**
     *
     */
    public long count(Page<PurchaseOrder> record) {
        return purchaseOrderDao.count(record);
    }

    @Override
    public long countPurchaseOrder(PurchaseOrderListRequest request) {
        return purchaseOrderDao.countPurchaseOrders(request);
    }

}