package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.logistics.param.AlibabaLogisticsOpenPlatformLogisticsTrace;
import com.alibaba.trade.param.*;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.pms.dto.CreatePurchaseOrderDto;
import com.upedge.common.model.pms.dto.CustomerStockPurchaseOrderRefundItemVo;
import com.upedge.common.model.pms.dto.CustomerStockPurchaseOrderRefundVo;
import com.upedge.common.model.pms.dto.StockPurchaseOrderItemReceiveDto;
import com.upedge.common.model.pms.request.CreatePurchaseOrderRequest;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
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
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OmsFeignClient omsFeignClient;


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
    public BaseResponse check(Long orderId) {

        PurchaseOrder purchaseOrder = selectByPrimaryKey(orderId);
        if (purchaseOrder == null){
            return BaseResponse.failed();
        }
        Long stockOrderId = Long.parseLong(purchaseOrder.getRelateId());
        if (stockOrderId != null && !stockOrderId.equals(0L)) {
            return BaseResponse.failed();
        }
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemService.selectByOrderId(orderId);
        List<StockPurchaseOrderItemReceiveDto> stockPurchaseOrderItemReceiveDtos = new ArrayList<>();
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
            StockPurchaseOrderItemReceiveDto stockPurchaseOrderItemReceiveDto = new StockPurchaseOrderItemReceiveDto();
            stockPurchaseOrderItemReceiveDto.setOrderId(orderId);
            stockPurchaseOrderItemReceiveDto.setVariantId(purchaseOrderItem.getVariantId());
            stockPurchaseOrderItemReceiveDto.setQuantity(purchaseOrderItem.getReceiveQuantity());
            stockPurchaseOrderItemReceiveDtos.add(stockPurchaseOrderItemReceiveDto);
        }
        omsFeignClient.updateInboundQuantity(stockPurchaseOrderItemReceiveDtos);
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse partItemRecreateOrder(PurchasePartItemRecreateOrderRequest request, Session session) {
        List<Long> itemIds = request.getItemIds();
        Long orderId = request.getOrderId();
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemService.selectByIds(itemIds, orderId);
        if (ListUtils.isEmpty(purchaseOrderItems)) {
            return BaseResponse.failed();
        }
        List<CreatePurchaseOrderDto> createPurchaseOrderDtos = new ArrayList<>();
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
            Long variantId = purchaseOrderItem.getVariantId();
            Integer quantity = purchaseOrderItem.getQuantity();
            Integer receivedQuantity = purchaseOrderItem.getReceiveQuantity();
            Integer reduceQuantity = quantity - receivedQuantity;
            if (reduceQuantity > 0) {
                variantWarehouseStockService.updatePurchaseStockReduce(variantId, "CNHZ", reduceQuantity);
            }
            CreatePurchaseOrderDto createPurchaseOrderDto = new CreatePurchaseOrderDto();
            createPurchaseOrderDto.setQuantity(quantity);
            createPurchaseOrderDto.setVariantId(variantId);
            createPurchaseOrderDtos.add(createPurchaseOrderDto);
        }
        CreatePurchaseOrderRequest createPurchaseOrderRequest = new CreatePurchaseOrderRequest();
        createPurchaseOrderRequest.setCreatePurchaseOrderDtos(createPurchaseOrderDtos);
        return customCreate(createPurchaseOrderRequest, session);
    }

    @Override
    public BaseResponse create1688PurchaseOrder(Long orderId, Session session) {
        PurchaseOrder purchaseOrder = selectByPrimaryKey(orderId);
        if (purchaseOrder == null || StringUtils.isNotBlank(purchaseOrder.getPurchaseId()) || purchaseOrder.getEditState() != 0) {
            return BaseResponse.failed("订单不存在或不是待创建状态");
        }
        purchaseOrderItemService.updateStateInitByOrderId(orderId);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemService.selectGroupByPurchaseSku(orderId);
        List<AlibabaTradeFastCargo> alibabaTradeFastCargos = new ArrayList<>();
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {

            AlibabaTradeFastCargo alibabaTradeFastCargo = new AlibabaTradeFastCargo();
            alibabaTradeFastCargo.setOfferId(Long.parseLong(purchaseOrderItem.getPurchaseLink()));
            alibabaTradeFastCargo.setSpecId(purchaseOrderItem.getSpecId());
            alibabaTradeFastCargo.setQuantity(purchaseOrderItem.getQuantity().doubleValue());
            alibabaTradeFastCargos.add(alibabaTradeFastCargo);
        }

        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);

        AlibabaTradeFastResult alibabaTradeFastResult = null;

        AlibabaTradeFastCreateOrderResult result = null;
        String message = "下单号： " + orderId;

        try {
            result = Ali1688Service.createOrder(alibabaTradeFastCargos, alibabaApiVo, message);
            boolean b = process1688OrderFailedReason(orderId, result);
            if (!b) {
                return BaseResponse.failed(result.getMessage());
            }
        } catch (CustomerException e) {
            redisTemplate.opsForHash().put(RedisKey.HASH_PURCHASE_ORDER_CREATE_FAILED_REASON, orderId.toString(), e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }
        alibabaTradeFastResult = result.getResult();
        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(orderId);
        purchaseOrder.setPurchaseId(alibabaTradeFastResult.getOrderId());
        purchaseOrder.setShipPrice(new BigDecimal((alibabaTradeFastResult.getPostFee().doubleValue() / 100)));
        purchaseOrder.setAmount(new BigDecimal(alibabaTradeFastResult.getTotalSuccessAmount().doubleValue() / 100));
        purchaseOrder.setUpdateTime(new Date());
        purchaseOrder.setPurchaseState(0);
        updateByPrimaryKeySelective(purchaseOrder);

        completeOrderInfo(orderId);
        return BaseResponse.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse revokePurchaseOrder(PurchaseOrderRevokeRequest request, Session session) throws CustomerException {
        Long orderId = request.getOrderId();
        PurchaseOrder purchaseOrder = selectByPrimaryKey(orderId);
        if (purchaseOrder == null || purchaseOrder.getPurchaseState() > 0) {
            return BaseResponse.failed("订单不存在或产品已入库");
        }
        if (purchaseOrder.getEditState() == -1) {
            return BaseResponse.success();
        }
        String purchaseId = purchaseOrder.getPurchaseId();
        if (StringUtils.isNotBlank(purchaseId)) {
            //先从1688取消订单
            AlibabaTradeCancelResult result = Ali1688Service.cancelOrder(Long.parseLong(purchaseOrder.getPurchaseId()), request.getCancelReason(), request.getRemark());
            if (!result.getSuccess()) {
                return BaseResponse.failed(result.getErrorMessage());
            }
        }

        //修改仓库采购中数量
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemService.selectByOrderId(orderId);
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
            if (purchaseOrderItem.getState() != 1) {
                continue;
            }
            boolean b = variantWarehouseStockService.purchaseOrderItemRevoke(purchaseOrderItem, purchaseOrder.getWarehouseCode());
            if (!b) {
                throw new CustomerException(purchaseOrderItem.getBarcode() + ": 修改库存失败");
            }
        }
        //修改订单状态
        purchaseOrderDao.updateOrderRevoke(orderId, new Date());

        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse createByCustomerStockOrder(CreatePurchaseOrderRequest request, Session session) {
        List<CreatePurchaseOrderDto> createPurchaseOrderDtos = request.getCreatePurchaseOrderDtos();
        if (ListUtils.isEmpty(createPurchaseOrderDtos)) {
            return BaseResponse.failed();
        }
        Long stockOrderId = request.getStockOrderId();
        Map<Long, Integer> variantQuantityMap = new HashMap<>();
        List<Long> variantIds = new ArrayList<>();
        for (CreatePurchaseOrderDto createPurchaseOrderDto : createPurchaseOrderDtos) {
            variantIds.add(createPurchaseOrderDto.getVariantId());
            variantQuantityMap.put(createPurchaseOrderDto.getVariantId(), createPurchaseOrderDto.getQuantity());
        }
        //获取产品信息
        List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);
        if (ListUtils.isEmpty(productVariants)) {
            return BaseResponse.failed("商品信息不存在");
        }
        //获取商品采购信息
        Set<String> purchaseSkus = new HashSet<>();
        Map<String, ProductVariant> skuVariantMap = new HashMap<>();
        List<ProductPurchaseInfo> productPurchaseInfos = new ArrayList<>();
        for (ProductVariant productVariant : productVariants) {
            String purchaseSku = productVariant.getPurchaseSku();
            if (StringUtils.isBlank(purchaseSku)) {
                return BaseResponse.failed(productVariant.getBarcode() + ": 缺少采购信息");
            }
            ProductPurchaseInfo productPurchaseInfo = productPurchaseInfoService.selectByPrimaryKey(purchaseSku);
            if (null == productPurchaseInfo) {
                return BaseResponse.failed(productVariant.getBarcode() + ": 采购信息异常");
            }
            purchaseSkus.add(productVariant.getPurchaseSku());
            skuVariantMap.put(productPurchaseInfo.getSpecId() + productPurchaseInfo.getPurchaseLink(), productVariant);
            productPurchaseInfos.add(productPurchaseInfo);
        }

        boolean isPreview = request.isPreview();

        Map<String, List<AlibabaTradeFastCargo>> supplierCargosMap = new HashMap<>();
        //不同供应商分组
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

        StringBuffer stringBuffer = new StringBuffer();
//        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);
        //根据供应商创建采购单
        for (Map.Entry<String, List<AlibabaTradeFastCargo>> map : supplierCargosMap.entrySet()) {
            List<AlibabaTradeFastCargo> tradeFastCargos = map.getValue();

//            if (isPreview){
//                AlibabaCreateOrderPreviewResult previewResult = null;
//                try {
//                    previewResult = Ali1688Service.createOrderPreview(tradeFastCargos, alibabaApiVo);
//                } catch (CustomerException e) {
//                    return BaseResponse.failed(e.getMessage());
//                }
//                if (!previewResult.getSuccess()){
//                    return BaseResponse.failed(previewResult.getErrorMsg());
//                }else {
//                    continue;
//                }
//            }
            AlibabaTradeFastCreateOrderResult result = null;
            AlibabaTradeFastResult alibabaTradeFastResult = null;
            Long id = purchaseService.getNextPurchaseOrderId();
            String message = "下单号： " + id;

//            try {
//                result = Ali1688Service.createOrder(tradeFastCargos, alibabaApiVo, message);
//                if (!result.getSuccess()){
//                    return BaseResponse.failed(result.getMessage());
//                }
//                alibabaTradeFastResult = result.getResult();
//            } catch (CustomerException e) {
//                return BaseResponse.success(e.getMessage());
//            }
//            if (alibabaTradeFastResult == null){
//                return BaseResponse.failed("采购单创建异常");
//            }
            redisTemplate.opsForHash().put(RedisKey.HASH_CUSTOMER_STOCK_RELATE_PURCHASE, stockOrderId.toString(), id);
            PurchaseOrder purchaseOrder = new PurchaseOrder(id,
//                    "0",
//                    alibabaTradeFastResult.getOrderId(),
                    null,
                    BigDecimal.ZERO,
//                    new BigDecimal((alibabaTradeFastResult.getPostFee().doubleValue() / 100)),
//                    new BigDecimal(alibabaTradeFastResult.getTotalSuccessAmount().doubleValue() / 100),
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    map.getKey(),
                    0, 0, 0L, 0);
            purchaseOrder.setRelateId(request.getStockOrderId().toString());
            List<PurchaseOrderItem> purchaseItems = new ArrayList<>();
            Double purchaseQuantity = 0.0;
            for (AlibabaTradeFastCargo tradeFastCargo : tradeFastCargos) {
                ProductVariant productVariant = skuVariantMap.get(tradeFastCargo.getSpecId() + tradeFastCargo.getOfferId());
                PurchaseOrderItem purchaseItem = new PurchaseOrderItem(productVariant, tradeFastCargo);

                purchaseItem.setOrderId(id);
                purchaseItem.setId(IdGenerate.nextId());
                purchaseItems.add(purchaseItem);
                purchaseQuantity += tradeFastCargo.getQuantity();
            }
            purchaseOrder.setPurchaseQuantity(purchaseQuantity.intValue());
            insert(purchaseOrder);
            purchaseOrderItemService.insertByBatch(purchaseItems);
            for (PurchaseOrderItem purchaseItem : purchaseItems) {
                variantWarehouseStockService.updateVariantPurchaseStockByPlan(purchaseItem.getVariantId(), "CNHZ", purchaseItem.getQuantity());
            }
            if (StringUtils.isBlank(stringBuffer)) {
                stringBuffer = stringBuffer.append(id);
            } else {
                stringBuffer = stringBuffer.append(",").append(id);
            }
        }
        return BaseResponse.success(stringBuffer.toString());
    }

    @GlobalTransactional
    @Override
    public BaseResponse refundByCustomerStockOrder(CustomerStockPurchaseOrderRefundVo customerStockPurchaseOrderRefundVo) {
        Long stockOrderId = customerStockPurchaseOrderRefundVo.getOrderId();
        List<CustomerStockPurchaseOrderRefundItemVo> refundItemVos = customerStockPurchaseOrderRefundVo.getRefundItemVos();
        List<PurchaseOrder> purchaseOrders = purchaseOrderDao.selectByRelateId(stockOrderId);
        if (ListUtils.isEmpty(purchaseOrders)) {
            return BaseResponse.failed("该备库订单未生成采购订单");
        }
        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            List<PurchaseOrderItem> items = purchaseOrderItemService.selectByOrderId(purchaseOrder.getId());
            purchaseOrderItems.addAll(items);
        }
        Map<Long, Integer> itemRefundQuantityMap = new HashMap<>();
        if (ListUtils.isNotEmpty(purchaseOrderItems)) {
            a:
            for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
                for (CustomerStockPurchaseOrderRefundItemVo refundItemVo : refundItemVos) {
                    if (purchaseOrderItem.getVariantId().equals(refundItemVo.getVariantId())) {
                        Integer refundQuantity = refundItemVo.getRefundQuantity();
                        itemRefundQuantityMap.put(purchaseOrderItem.getId(), refundQuantity);
                        refundItemVos.remove(refundItemVo);
                        continue a;
                    }
                }
            }
        }
        if (ListUtils.isNotEmpty(refundItemVos)) {
            return BaseResponse.failed("备库订单产品信息异常");
        }
        itemRefundQuantityMap.forEach((id, refundQuantity) -> {
            purchaseOrderItemService.updateRefundQuantityById(id, refundQuantity);
        });

        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse customCreate(CreatePurchaseOrderRequest request, Session session) {
        List<CreatePurchaseOrderDto> createPurchaseOrderDtos = request.getCreatePurchaseOrderDtos();
        if (ListUtils.isEmpty(createPurchaseOrderDtos)) {
            return BaseResponse.failed();
        }
        //变体数量
        Map<Long, Integer> variantQuantityMap = new HashMap<>();
        List<Long> variantIds = new ArrayList<>();
        for (CreatePurchaseOrderDto createPurchaseOrderDto : createPurchaseOrderDtos) {
            Long variantId = createPurchaseOrderDto.getVariantId();
            if (variantIds.contains(variantId)) {
                Integer quantity = variantQuantityMap.get(variantId);
                quantity += createPurchaseOrderDto.getQuantity();
                variantQuantityMap.put(variantId, quantity);
            } else {
                variantIds.add(variantId);
                variantQuantityMap.put(variantId, createPurchaseOrderDto.getQuantity());
            }
        }
        //获取产品信息
        List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);
        if (ListUtils.isEmpty(productVariants)) {
            return BaseResponse.failed("商品信息不存在");
        }
        //获取商品采购信息
        Map<String, ProductPurchaseInfo> purchaseInfoMap = new HashMap<>();
        for (ProductVariant productVariant : productVariants) {
            String purchaseSku = productVariant.getPurchaseSku();
            if (StringUtils.isBlank(purchaseSku)) {
                return BaseResponse.failed(productVariant.getBarcode() + ": 缺少采购信息");
            }
            ProductPurchaseInfo productPurchaseInfo = productPurchaseInfoService.selectByPrimaryKey(purchaseSku);
            if (null == productPurchaseInfo) {
                return BaseResponse.failed(productVariant.getBarcode() + ": 采购信息异常");
            }
            purchaseInfoMap.put(purchaseSku, productPurchaseInfo);
        }


        Map<String, List<ProductVariant>> supplierVariantMap = new HashMap<>();
        //不同供应商分组
        for (ProductVariant productVariant : productVariants) {
            String purchaseSku = productVariant.getPurchaseSku();
            ProductPurchaseInfo productPurchaseInfo = purchaseInfoMap.get(purchaseSku);
            if (null == productPurchaseInfo){
                continue;
            }
            String supplierName = productPurchaseInfo.getSupplierName();
            if (!supplierVariantMap.containsKey(supplierName)) {
                supplierVariantMap.put(supplierName, new ArrayList<ProductVariant>());
            }
            supplierVariantMap.get(supplierName).add(productVariant);

        }

        //根据供应商创建采购单
        for (Map.Entry<String, List<ProductVariant>> map : supplierVariantMap.entrySet()) {
            List<ProductVariant> variants = map.getValue();

            Long id = purchaseService.getNextPurchaseOrderId();

            PurchaseOrder purchaseOrder = new PurchaseOrder(id,
                    null,
                    BigDecimal.ZERO,
                    null,
                    null,
                    BigDecimal.ZERO,
                    map.getKey(),
                    0, 0, 0L, 0);
            List<PurchaseOrderItem> purchaseItems = new ArrayList<>();
            Double purchaseQuantity = 0.0;
            for (ProductVariant productVariant : variants) {
                Long variantId = productVariant.getId();
                String purchaseSku = productVariant.getPurchaseSku();

                Integer quantity = variantQuantityMap.get(variantId);
                ProductPurchaseInfo productPurchaseInfo = purchaseInfoMap.get(purchaseSku);

                PurchaseOrderItem purchaseItem = new PurchaseOrderItem(productVariant);
                purchaseItem.setQuantity(quantity);
                purchaseItem.setOriginalQuantity(quantity);
                purchaseItem.setSpecId(productPurchaseInfo.getSpecId());
                purchaseItem.setPurchaseLink(productPurchaseInfo.getPurchaseLink());
                purchaseItem.setOrderId(id);
                purchaseItem.setId(IdGenerate.nextId());
                purchaseItems.add(purchaseItem);

                purchaseQuantity += quantity;
            }
            purchaseOrder.setPurchaseQuantity(purchaseQuantity.intValue());
            insert(purchaseOrder);
            purchaseOrderItemService.insertByBatch(purchaseItems);
            for (PurchaseOrderItem purchaseItem : purchaseItems) {
                variantWarehouseStockService.updateVariantPurchaseStockByPlan(purchaseItem.getVariantId(), "CNHZ", purchaseItem.getQuantity());
            }
        }
        return BaseResponse.success();
    }

    @Override
    public void completeOrderInfo(Long id) {
        PurchaseOrder purchaseOrder = selectByPrimaryKey(id);
        if (null == purchaseOrder) {
            return;
        }
        String purchaseId = purchaseOrder.getPurchaseId();
        AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo = null;
        try {
            alibabaOpenplatformTradeModelTradeInfo = Ali1688Service.orderDetail(Long.parseLong(purchaseId), null);
        } catch (CustomerException e) {
            e.printStackTrace();
            return;
        }
        updateBaseInfo(id, purchaseOrder.getPurchaseId(), purchaseOrder.getTrackingCode(), alibabaOpenplatformTradeModelTradeInfo);
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
//        if(purchaseOrder.getPurchaseId() == null){
//            return BaseResponse.failed("订单未创建1688采购订单");
//        }
        if (purchaseOrder.getEditState() == 1) {
            return BaseResponse.failed("已提交的订单无法修改状态");
        }
        if (state == purchaseOrder.getEditState()) {
            return BaseResponse.success();
        }
        purchaseOrderDao.updateEditState(id, state);
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
        Long stockOrderId = null;
        try {
            stockOrderId = Long.parseLong(purchaseOrder.getRelateId());
        } catch (NumberFormatException e) {

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
        List<StockPurchaseOrderItemReceiveDto> stockPurchaseOrderItemReceiveDtos = new ArrayList<>();
        a:
        for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItems) {
            for (PurchaseOrderItemReceiveDto itemReceiveDto : itemReceiveDtos) {
                if (purchaseOrderItem.getId().equals(itemReceiveDto.getItemId())
                        && itemReceiveDto.getQuantity() > 0 && purchaseOrderItem.getState() == 1) {
                    recordUpdateRequest.setQuantity(itemReceiveDto.getQuantity());
                    recordUpdateRequest.setVariantSku(purchaseOrderItem.getVariantSku());
                    recordUpdateRequest.setRelateId(purchaseOrderItem.getId());
                    BaseResponse response = variantWarehouseStockService.variantStockIm(recordUpdateRequest, session);
                    if (response.getCode() != ResultCode.SUCCESS_CODE) {
                        throw new Exception(response.getMsg());
                    }
                    purchaseOrderItem.setReceiveQuantity(itemReceiveDto.getQuantity() + purchaseOrderItem.getReceiveQuantity());
                    purchaseOrderItemService.updateByPrimaryKeySelective(purchaseOrderItem);

                    //修改客户备库订单产品入库数据
                    if (stockOrderId != null && !stockOrderId.equals(0L)) {
                        StockPurchaseOrderItemReceiveDto stockPurchaseOrderItemReceiveDto = new StockPurchaseOrderItemReceiveDto();
                        stockPurchaseOrderItemReceiveDto.setOrderId(orderId);
                        stockPurchaseOrderItemReceiveDto.setVariantId(purchaseOrderItem.getVariantId());
                        stockPurchaseOrderItemReceiveDto.setQuantity(itemReceiveDto.getQuantity());
                        stockPurchaseOrderItemReceiveDtos.add(stockPurchaseOrderItemReceiveDto);
                    }
                    continue a;
                }
            }
        }
        //修改客户备库订单产品入库数据
        try {
            if (ListUtils.isNotEmpty(stockPurchaseOrderItemReceiveDtos)) {
                omsFeignClient.updateInboundQuantity(stockPurchaseOrderItemReceiveDtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.success();
    }

    @Override
    public BaseResponse refreshFrom1688(Long id) {
        String key = "purchase:1688info:sync:" + id;
        boolean b = RedisUtil.lock(redisTemplate, key, 5L, 30 * 60 * 1000L);
        if (!b) {
            return BaseResponse.success();
        }
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
        if (StringUtils.isBlank(trackingCode)) {
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
        if (alibabaOpenplatformTradeModelNativeLogisticsInfo != null && alibabaOpenplatformTradeModelNativeLogisticsInfo.getLogisticsItems() != null) {
            List<AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo> logisticsItemsInfos = Arrays.asList(alibabaOpenplatformTradeModelNativeLogisticsInfo.getLogisticsItems());
            if (ListUtils.isNotEmpty(logisticsItemsInfos)) {
                List<PurchaseOrderTracking> orderTrackingList = new ArrayList<>();
                StringBuffer code = new StringBuffer();
                for (int i = 0; i < logisticsItemsInfos.size(); i++) {
                    AlibabaOpenplatformTradeModelNativeLogisticsItemsInfo logisticsItemsInfo = logisticsItemsInfos.get(i);
                    String logisticsBillNo = logisticsItemsInfo.getLogisticsBillNo();
                    if (logisticsBillNo == null) {
                        continue;
                    }
                    if (code == null) {
                        code = code.append(logisticsBillNo);
                    } else {
                        code = code.append(",").append(logisticsBillNo);
                    }
                    if (trackingCode.contains(logisticsBillNo)) {
                        continue;
                    }
                    PurchaseOrderTracking purchaseOrderTracking = new PurchaseOrderTracking(id, purchaseId, logisticsBillNo, logisticsItemsInfo.getLogisticsCompanyName());
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

    void updateBaseInfo(Long id, String purchaseId, String trackingCode, AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo) {


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
                    && logisticsInfoLogisticsItems.length > 0) {
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

    void completeItemPrice(Long id, AlibabaOpenplatformTradeModelTradeInfo alibabaOpenplatformTradeModelTradeInfo) {
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
    public BaseResponse orderList(PurchaseOrderListRequest request) {
        PurchaseOrderListDto purchaseOrderListDto = request.getT();
        if (null == purchaseOrderListDto) {
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
        if (ListUtils.isNotEmpty(purchaseOrderTracks)) {
            for (PurchaseOrderTracking purchaseOrderTrack : purchaseOrderTracks) {
                orderIds.add(purchaseOrderTrack.getPurchaseOrderId());
            }
        }

        purchaseOrderListDto.setIds(orderIds);
        List<PurchaseOrder> purchaseOrders = purchaseOrderDao.selectPurchaseOrders(request);
        Long total = purchaseOrderDao.countPurchaseOrders(request);
        request.setTotal(total);
        if (ListUtils.isEmpty(purchaseOrders)) {
            return BaseResponse.success();
        }
        syncOrderTrackingInfo(purchaseOrders);
        //如果刚才没查询到变体,但是查询到订单则重新根据订单查询变体
        if (ListUtils.isEmpty(purchaseOrderItems)) {
            purchaseOrders.forEach(purchaseOrder -> {
                orderIds.add(purchaseOrder.getId());
            });
            purchaseOrderItems = purchaseOrderItemService.selectByOrderIds(orderIds);
        }

        List<PurchaseOrderTracking> orderTrackings = purchaseOrderTrackingService.selectByOrderIds(orderIds);
        //匹配同个订单下的数据
        List<PurchaseOrderVo> purchaseOrderVos = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrders) {
            Long orderId = purchaseOrder.getId();

            List<PurchaseOrderItem> orderItems = new ArrayList<>();
            PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
            BeanUtils.copyProperties(purchaseOrder, purchaseOrderVo);
            for (PurchaseOrderItem orderItem : purchaseOrderItems) {
                if (orderItem.getState() == 0) {
                    continue;
                }
                if (orderItem.getOrderId().equals(orderId)) {
                    orderItems.add(orderItem);
                }

            }
            for (PurchaseOrderTracking orderTracking : orderTrackings) {
                if (orderTracking.getPurchaseOrderId().equals(purchaseOrder.getId())) {
                    purchaseOrderVo.getTrackingList().add(orderTracking);
                }
            }
            purchaseOrderItems.removeAll(orderItems);
            if (StringUtils.isBlank(purchaseOrder.getPurchaseId())) {
                checkOrderItems(orderItems);
            }

            purchaseOrderVo.setPurchaseItemVos(orderItems);

            String purchaseId = purchaseOrder.getPurchaseId();
            if (StringUtils.isBlank(purchaseId)) {
                purchaseOrderVo.setCreateFailedReason((String) redisTemplate.opsForHash().get(RedisKey.HASH_PURCHASE_ORDER_CREATE_FAILED_REASON, orderId.toString()));
            }

            purchaseOrderVos.add(purchaseOrderVo);
        }


        return BaseResponse.success(purchaseOrderVos, request);
    }

    public void checkOrderItems(List<PurchaseOrderItem> orderItems) {
        Map<String, Integer> productMoq = new HashMap<>();
        Map<String, Integer> productQty = new HashMap<>();
        for (PurchaseOrderItem orderItem : orderItems) {
            if (orderItem.getState() == 0) {
                continue;
            }
            Integer quantity = orderItem.getQuantity();
            if (orderItem.getInventory() == null || orderItem.getInventory() < quantity) {
                orderItem.setState(-1);
                continue;
            }
            String productLink = orderItem.getPurchaseLink();
            if (!productMoq.containsKey(productLink)) {
                productMoq.put(productLink, orderItem.getMinOrderQuantity());
            } else {
                quantity += productQty.get(productLink);
            }
            productQty.put(productLink, quantity);
        }

        for (PurchaseOrderItem orderItem : orderItems) {
            if (orderItem.getState() < 1) {
                continue;
            }
            String productLink = orderItem.getPurchaseLink();
            if (!productMoq.containsKey(productLink)) {
                continue;
            }
            Integer moq = productMoq.get(productLink);
            Integer total = productQty.get(productLink);
            if (moq == null || total == null || total < moq) {
                orderItem.setState(-1);
            }
        }

    }

    public void syncOrderTrackingInfo(List<PurchaseOrder> purchaseOrders) {
        List<CompletableFuture<Void>> completableFutures = purchaseOrders.stream().map(purchaseOrder -> {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    if (purchaseOrder.getEditState() == 1 && purchaseOrder.getPurchaseState() != -1 && purchaseOrder.getPurchaseState() != 2) {
                        refreshFrom1688(purchaseOrder.getId());
                    }
                }
            }).handle((tet, ccc) -> {
                return null;
            });
            return completableFuture;
        }).collect(Collectors.toList());
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
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


    private boolean process1688OrderFailedReason(Long orderId, AlibabaTradeFastCreateOrderResult result) {
        if (result.getSuccess()) {
            return true;
        }
        redisTemplate.opsForHash().put(RedisKey.HASH_PURCHASE_ORDER_CREATE_FAILED_REASON, orderId.toString(), result.getMessage());
        List<String> ids = getStringsInBrackets(result.getMessage());
        if (ListUtils.isNotEmpty(ids)) {
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    for (String id : ids) {
                        productPurchaseInfoService.refreshAlibabaProductInventory(id);
                    }
                }
            }, threadPoolExecutor);
//            purchaseOrderItemService.updateStateByOrderIdAndPurchaseLink(orderId,ids,-1);
        }
        return false;
    }

    public static List<String> getStringsInBrackets(String s) {
        Pattern p = Pattern.compile("\\[(.*?)\\]");
        Matcher m = p.matcher(s);
        List<String> list = new ArrayList<String>();
        while (m.find()) {
            list.add(m.group(1));
        }
        return list;
    }


}