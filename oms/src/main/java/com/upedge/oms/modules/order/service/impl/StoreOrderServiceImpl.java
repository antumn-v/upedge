package com.upedge.oms.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.model.product.StoreProductVariantVo;
import com.upedge.common.model.product.request.PlatIdSelectStoreVariantRequest;
import com.upedge.common.model.ship.request.AreaSelectRequest;
import com.upedge.common.model.ship.vo.AreaVo;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreApiRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.dao.*;
import com.upedge.oms.modules.order.dto.UnrecognizedStoreOrderDto;
import com.upedge.oms.modules.order.entity.StoreOrder;
import com.upedge.oms.modules.order.entity.StoreOrderAddress;
import com.upedge.oms.modules.order.entity.StoreOrderItem;
import com.upedge.oms.modules.order.entity.StoreOrderRelate;
import com.upedge.oms.modules.order.request.StoreDataListRequest;
import com.upedge.oms.modules.order.request.StoreOrderListRequest;
import com.upedge.oms.modules.order.request.UnrecognizedStoreOrderListRequest;
import com.upedge.oms.modules.order.response.StoreOrderListResponse;
import com.upedge.oms.modules.order.service.OrderAddressService;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderService;
import com.upedge.oms.modules.order.vo.StoreOrderVariantData;
import com.upedge.oms.modules.order.vo.StoreOrderVo;
import com.upedge.oms.modules.statistics.request.AppUserSortRequest;
import com.upedge.oms.modules.statistics.vo.AppUserSortVo;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyLineItem;
import com.upedge.thirdparty.shopify.moudles.order.entity.ShopifyOrder;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaOrder;
import com.upedge.thirdparty.shoplazza.moudles.order.entity.ShoplazzaOrder.ShoplazzaLineItems;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrder;
import com.upedge.thirdparty.woocommerce.moudles.order.entity.WoocommerceOrderItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class StoreOrderServiceImpl implements StoreOrderService {

    @Autowired
    private StoreOrderDao storeOrderDao;
    @Autowired
    private StoreOrderItemDao storeOrderItemDao;

    @Autowired
    StoreOrderAddressDao storeOrderAddressDao;

    @Autowired
    StoreOrderRefundDao storeOrderRefundDao;

    @Autowired
    StoreOrderRelateDao storeOrderRelateDao;

    @Autowired
    OrderAddressService orderAddressService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    TmsFeignClient tmsFeignClient;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    private static List<String> financialStatus = new ArrayList<>();

    static {
        financialStatus.add("pending");
        financialStatus.add("authorized");
        financialStatus.add("partially_paid");
        financialStatus.add("waiting");
        financialStatus.add("paying");
    }


    /**
     *
     */
    @Override
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        StoreOrder record = new StoreOrder();
        record.setId(id);
        return storeOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(StoreOrder record) {
        return storeOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(StoreOrder record) {
        return storeOrderDao.insert(record);
    }

    @Override
    public List<Long> selectIdsByCreateTime() {
        return storeOrderDao.selectIdsByCreateTime();
    }

    @Override
    public BaseResponse unrecognizedStoreOrderList(UnrecognizedStoreOrderListRequest request, Session session) {
        if (null == request.getT()) {
            request.setT(new UnrecognizedStoreOrderDto());
        }
        if (request.getT().getOrgId() == null) {
            if (session.getUserType() == BaseCode.USER_ROLE_ADMIN) {
                request.getT().setOrgId(session.getParentOrganization().getId());
            }
        }
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            List<Long> orgIds = request.getT().getOrgIds();
            if (ListUtils.isNotEmpty(orgIds)) {
                List<Long> longs = new ArrayList<>();
                orgIds.forEach(orgId -> {
                    if (!session.getOrgIds().contains(orgId)) {
                        longs.add(orgId);
                    }
                });
                orgIds.removeAll(longs);
            } else {
                orgIds = session.getOrgIds();
            }
            request.getT().setOrgIds(orgIds);
        }
        request.initFromNum();
        List<StoreOrderVo> storeOrderVos = storeOrderDao.selectUnGeneratedStoreOrder(request);
        Long total = storeOrderDao.countUnGeneratedStoreOrder(request);
        request.setTotal(total);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, storeOrderVos, request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StoreOrder shopifyOrderUpdate(StoreApiRequest request) {
        StoreVo storeVo = request.getStoreVo();
        ShopifyOrder shopifyOrder = null;

        try {
            JSONObject jsonObject = request.getJsonObject();
            shopifyOrder = jsonObject.toJavaObject(ShopifyOrder.class);
            if (shopifyOrder == null){
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        String platOrderId = shopifyOrder.getId();
        String key = RedisKey.STRING_STORE_PALT_ORDER_UPDATE + storeVo.getId() + ":" + platOrderId;
        boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 5 * 1000L);
        if (!flag) {
            return null;
        }
        StoreOrder storeOrder = storeOrderDao.selectByStorePlatId(storeVo.getId(), shopifyOrder.getId());
        if (null != storeOrder){
            updateShopifyStoreOrder(storeOrder,shopifyOrder,storeVo);
            return storeOrder;
        }
        if (shopifyOrder.getFinancial_status() == null
            || !shopifyOrder.getFinancial_status().equals("paid")
            || shopifyOrder.getFulfillment_status() != null){
            return null;
        }

        Long storeAddressId = null;
        Long storeOrderId = null;

        Date date = new Date();

        storeAddressId = IdGenerate.nextId();
        storeOrderId = IdGenerate.nextId();
        storeOrder = new StoreOrder(shopifyOrder);
        storeOrder.setStoreName(storeVo.getStoreName());
        storeOrder.setId(storeOrderId);
        storeOrder.setStoreId(storeVo.getId());
        storeOrder.setOrgId(storeVo.getOrgId());
        storeOrder.setOrgPath(storeVo.getOrgPath());
        storeOrder.setStoreAddressId(storeAddressId);
        storeOrder.setCreateTime(shopifyOrder.getCreated_at());
        storeOrder.setUpdateTime(shopifyOrder.getUpdated_at());
        storeOrder.setImportTime(date);
        storeOrder.setCustomerId(storeVo.getCustomerId());


        StoreOrderAddress storeOrderAddress = null;
        if (null != shopifyOrder.getShipping_address()) {
            storeOrderAddress = new StoreOrderAddress(shopifyOrder.getShipping_address());
        } else {
            storeOrderAddress = new StoreOrderAddress();
        }
        storeOrderAddress.setNote(shopifyOrder.getNote());
        storeOrderAddress.setId(storeAddressId);
        storeOrderAddress.setPlatOrderId(platOrderId);
        storeOrderAddress.setStoreOrderId(storeOrderId);

        if (StringUtils.isNotBlank(shopifyOrder.getEmail())) {
            storeOrderAddress.setEmail(shopifyOrder.getEmail());
        } else if (StringUtils.isNotBlank(shopifyOrder.getContact_email())) {
            storeOrderAddress.setEmail(shopifyOrder.getContact_email());
        } else if (shopifyOrder.getCustomer() != null && StringUtils.isNotBlank(shopifyOrder.getCustomer().getEmail())) {
            storeOrderAddress.setEmail(shopifyOrder.getCustomer().getEmail());
        }

        List<StoreOrderItem> insertItems = new ArrayList<>();
        List<ShopifyLineItem> lineItemsBeans = shopifyOrder.getLine_items();
        Iterator<ShopifyLineItem> iterator = lineItemsBeans.iterator();

        while (iterator.hasNext()) {
            ShopifyLineItem lineItemsBean = iterator.next();
            StoreOrderItem storeOrderItem = new StoreOrderItem(lineItemsBean);
            storeOrderItem.setPlatOrderId(platOrderId);
            storeOrderItem.setStoreOrderId(storeOrderId);
            storeOrderItem.setId(IdGenerate.nextId());
            insertItems.add(storeOrderItem);
            break;
        }
        storeOrderItemDao.insertByBatch(insertItems);
        storeOrderAddressDao.insert(storeOrderAddress);
        storeOrderDao.insert(storeOrder);

        storeOrder.setItems(insertItems);
        storeOrder.setAddress(storeOrderAddress);
        RedisUtil.unLock(redisTemplate, key);
        return storeOrder;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StoreOrder woocommerceOrderUpdate(StoreApiRequest request) {

        StoreVo storeVo = request.getStoreVo();
        WoocommerceOrder woocommerceOrder = null;
        try {
            woocommerceOrder = request.getJsonObject().toJavaObject(WoocommerceOrder.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (!woocommerceOrder.getStatus().equals("processing")) {
            return null;
        }
        String platOrderId = woocommerceOrder.getId();
        String key = RedisKey.STRING_STORE_PALT_ORDER_UPDATE + storeVo.getId() + ":" + platOrderId;
        boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 10 * 1000L);
        if (!flag) {
            return null;
        }
        StoreOrder storeOrder = storeOrderDao.selectByStorePlatId(storeVo.getId(), platOrderId);
        Long storeAddressId = null;
        Long storeOrderId = null;
        boolean b = false;
        Date date = new Date();
        if (null == storeOrder) {
            storeAddressId = IdGenerate.nextId();
            storeOrderId = IdGenerate.nextId();
            storeOrder = new StoreOrder(woocommerceOrder);
            storeOrder.setId(storeOrderId);
            storeOrder.setStoreName(storeVo.getStoreName());
            storeOrder.setStoreId(storeVo.getId());
            storeOrder.setOrgId(storeVo.getOrgId());
            storeOrder.setOrgPath(storeVo.getOrgPath());
            storeOrder.setStoreAddressId(storeAddressId);
            storeOrder.setCreateTime(date);
            storeOrder.setUpdateTime(date);
            storeOrder.setImportTime(date);
            storeOrder.setCustomerId(storeVo.getCustomerId());
        } else {
            b = true;
            storeOrderId = storeOrder.getId();
            storeAddressId = storeOrder.getStoreAddressId();
            storeOrder.setUpdateTime(date);
        }
        StoreOrderAddress storeOrderAddress = null;
        if (null != woocommerceOrder) {
            storeOrderAddress = new StoreOrderAddress(woocommerceOrder.getShipping());
            if (StringUtils.isNotBlank(storeOrderAddress.getCountryCode())) {
                AreaSelectRequest areaSelectRequest = new AreaSelectRequest();
                areaSelectRequest.setAreaCode(storeOrderAddress.getCountryCode());
                BaseResponse response = tmsFeignClient.areaSelect(areaSelectRequest);
                if (ResultCode.SUCCESS_CODE == response.getCode()) {
                    AreaVo areaVo = JSONObject.parseObject(JSON.toJSON(response.getData()).toString()).toJavaObject(AreaVo.class);
                    storeOrderAddress.setCountry(areaVo.getEnName());
                }
            }
        } else {
            storeOrderAddress = new StoreOrderAddress();
        }
        storeOrderAddress.setId(storeAddressId);
        storeOrderAddress.setPlatOrderId(platOrderId);
        storeOrderAddress.setStoreOrderId(storeOrderId);
        if (null != woocommerceOrder.getBilling()) {
            storeOrderAddress.setEmail(woocommerceOrder.getBilling().getEmail());
            storeOrderAddress.setPhone(woocommerceOrder.getBilling().getPhone());
        }
        List<StoreOrderItem> insertItems = new ArrayList<>();
        List<StoreOrderItem> updateItems = new ArrayList<>();
        List<WoocommerceOrderItem> lineItemsBeans = woocommerceOrder.getLine_items();
        Iterator<WoocommerceOrderItem> iterator = lineItemsBeans.iterator();
        BigDecimal productAmount = BigDecimal.ZERO;
        if (b) {
            List<String> platItemIdList = new ArrayList<>();
            List<String> platItemIds = storeOrderItemDao.selectPlatItemIdByStoreOrderId(storeOrderId);
            while (iterator.hasNext()) {
                WoocommerceOrderItem lineItemsBean = iterator.next();
                productAmount = productAmount.add(lineItemsBean.getPrice().multiply(new BigDecimal(lineItemsBean.getQuantity())));
                platItemIdList.add(lineItemsBean.getId());
                StoreOrderItem storeOrderItem = new StoreOrderItem(lineItemsBean);
                storeOrderItem.setPlatOrderId(platOrderId);
                storeOrderItem.setStoreOrderId(storeOrderId);
                if (platItemIds.contains(lineItemsBean.getId())) {
                    updateItems.add(storeOrderItem);
                } else {
                    storeOrderItem.setId(IdGenerate.nextId());
                    insertItems.add(storeOrderItem);
                }
            }
            platItemIds.removeAll(platItemIdList);
            if (ListUtils.isNotEmpty(insertItems)) {
                storeOrderItemDao.insertByBatch(insertItems);
            }
            if (ListUtils.isNotEmpty(platItemIds)) {
                storeOrderItemDao.updateRemoveState(storeOrderId, platItemIds);
            }
            int i = storeOrderAddressDao.updateByPrimaryKeySelective(storeOrderAddress);
            if (i == 1){
                orderAddressService.updateByStoreOrderAddress(storeOrderAddress);
            }
        } else {
            while (iterator.hasNext()) {
                WoocommerceOrderItem lineItemsBean = iterator.next();
                productAmount = productAmount.add(lineItemsBean.getSubtotal());
                StoreOrderItem storeOrderItem = new StoreOrderItem(lineItemsBean);
                storeOrderItem.setPlatOrderId(platOrderId);
                storeOrderItem.setStoreOrderId(storeOrderId);
                storeOrderItem.setId(IdGenerate.nextId());
                insertItems.add(storeOrderItem);
            }
            storeOrder.setTotalLineItemsPrice(productAmount);
            storeOrderItemDao.insertByBatch(insertItems);
            storeOrderAddressDao.insert(storeOrderAddress);
            storeOrderDao.insert(storeOrder);
        }
        updateItems.addAll(insertItems);
        storeOrder.setItems(updateItems);
        storeOrder.setAddress(storeOrderAddress);
        RedisUtil.unLock(redisTemplate, key);
        return storeOrder;
    }

    @Override
    public StoreOrder shoplazzaOrderUpdate(StoreApiRequest request) {
        StoreVo storeVo = request.getStoreVo();
        ShoplazzaOrder shoplazzaOrder = null;
        try {
            JSONObject jsonObject = request.getJsonObject();
            shoplazzaOrder = jsonObject.toJavaObject(ShoplazzaOrder.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (!shoplazzaOrder.getFinancial_status().equals("paid")
                || !shoplazzaOrder.getFulfillment_status().equals("waiting")) {
            return null;
        }
        String platOrderId = shoplazzaOrder.getId();

        String key = RedisKey.STRING_STORE_PALT_ORDER_UPDATE + storeVo.getId() + ":" + platOrderId;
        boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 10 * 1000L);
        if (!flag) {
            return null;
        }

        StoreOrder storeOrder = storeOrderDao.selectByStorePlatId(storeVo.getId(), platOrderId);
        Long storeAddressId = null;
        Long storeOrderId = null;

        boolean b = false;

        Date date = new Date();

        if (null == storeOrder) {
            storeAddressId = IdGenerate.nextId();
            storeOrderId = IdGenerate.nextId();
            storeOrder = new StoreOrder(shoplazzaOrder);
            storeOrder.setStoreName(storeVo.getStoreName());
            storeOrder.setId(storeOrderId);
            storeOrder.setStoreId(storeVo.getId());
            storeOrder.setOrgId(storeVo.getOrgId());
            storeOrder.setOrgPath(storeVo.getOrgPath());
            storeOrder.setStoreAddressId(storeAddressId);
            storeOrder.setCreateTime(date);
            storeOrder.setUpdateTime(date);
            storeOrder.setImportTime(date);
            storeOrder.setCustomerId(storeVo.getCustomerId());
            storeOrder.setOrgId(storeVo.getOrgId());
        } else {
            b = true;
            storeOrderId = storeOrder.getId();
            storeAddressId = storeOrder.getStoreAddressId();
            storeOrder.setUpdateTime(date);
        }

        StoreOrderAddress storeOrderAddress = null;
        if (null != shoplazzaOrder.getShipping_address()) {
            storeOrderAddress = new StoreOrderAddress(shoplazzaOrder.getShipping_address());
        } else {
            storeOrderAddress = new StoreOrderAddress();
        }
        storeOrderAddress.setNote(shoplazzaOrder.getNote());
        storeOrderAddress.setId(storeAddressId);
        storeOrderAddress.setPlatOrderId(platOrderId);
        storeOrderAddress.setStoreOrderId(storeOrderId);

        List<StoreOrderItem> insertItems = new ArrayList<>();
        List<StoreOrderItem> updateItems = new ArrayList<>();
        List<ShoplazzaLineItems> lineItemsBeans = shoplazzaOrder.getLine_items();
        Iterator<ShoplazzaLineItems> iterator = lineItemsBeans.iterator();
        if (b) {
            List<String> platItemIdList = new ArrayList<>();
            List<String> platItemIds = storeOrderItemDao.selectPlatItemIdByStoreOrderId(storeOrderId);
            while (iterator.hasNext()) {
                ShoplazzaLineItems lineItemsBean = iterator.next();
                platItemIdList.add(lineItemsBean.getId());
                StoreOrderItem storeOrderItem = new StoreOrderItem(lineItemsBean);
                storeOrderItem.setPlatOrderId(platOrderId);
                storeOrderItem.setStoreOrderId(storeOrderId);
                if (platItemIds.contains(lineItemsBean.getId())) {
                    updateItems.add(storeOrderItem);
                } else {
                    storeOrderItem.setId(IdGenerate.nextId());
                    insertItems.add(storeOrderItem);
                }
            }
            platItemIds.removeAll(platItemIdList);
            if (ListUtils.isNotEmpty(insertItems)) {
                storeOrderItemDao.insertByBatch(insertItems);
            }
            if (ListUtils.isNotEmpty(platItemIds)) {
                storeOrderItemDao.updateRemoveState(storeOrderId, platItemIds);
            }
            int i = storeOrderAddressDao.updateByPrimaryKeySelective(storeOrderAddress);
            if (i == 1){
                orderAddressService.updateByStoreOrderAddress(storeOrderAddress);
            }
        } else {
            while (iterator.hasNext()) {
                ShoplazzaLineItems lineItemsBean = iterator.next();
                StoreOrderItem storeOrderItem = new StoreOrderItem(lineItemsBean);
                storeOrderItem.setPlatOrderId(platOrderId);
                storeOrderItem.setStoreOrderId(storeOrderId);
                storeOrderItem.setId(IdGenerate.nextId());
                insertItems.add(storeOrderItem);
            }
            storeOrderItemDao.insertByBatch(insertItems);
            storeOrderAddressDao.insert(storeOrderAddress);
            storeOrderDao.insert(storeOrder);
        }
        updateItems.addAll(insertItems);
        storeOrder.setItems(updateItems);
        storeOrder.setAddress(storeOrderAddress);
        RedisUtil.unLock(redisTemplate, key);
        return storeOrder;
    }

    /**
     *
     */
    public StoreOrder selectByPrimaryKey(Long id) {

        return storeOrderDao.selectByPrimaryKey(id);
    }

    @Override
    public Long customerStoreOrderCount(Session session) {
        Long customerId = session.getCustomerId();
        List<Long> orgIds = new ArrayList<>();
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            orgIds = session.getOrgIds();
        }
        return storeOrderDao.selectOrderCountByCustomer(customerId, orgIds);
    }

    @Override
    public BigDecimal customerStoreOrderIncome(Session session) {
        Long customerId = session.getCustomerId();
        List<Long> orgIds = new ArrayList<>();
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            orgIds = session.getOrgIds();
        }
        return storeOrderDao.selectOrderIncomeByCustomer(customerId, orgIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void completeStoreOrderItemDetail(Long storeOrderId) {
        String key = RedisKey.STRING_STORE_ORDER_COMPLETE + storeOrderId;
        boolean flag = RedisUtil.lock(redisTemplate, key, 2L, 10 * 1000L);
        if (!flag) {
            return;
        }
        List<StoreOrderItem> items = storeOrderItemDao.selectIncompleteItems(storeOrderId);
        if (ListUtils.isEmpty(items)) {
            RedisUtil.unLock(redisTemplate, key);
            return;
        }

        StoreOrder storeOrder = storeOrderDao.selectByPrimaryKey(storeOrderId);

        List<StoreProductVariantVo> variants = new ArrayList<>();

        items.forEach(item -> {
            if (item.getPlatProductId() != null &&
            item.getPlatVariantId() != null){
                StoreProductVariantVo variant = new StoreProductVariantVo();
                variant.setPlatProductId(item.getPlatProductId());
                variant.setPlatVariantId(item.getPlatVariantId());
                variants.add(variant);
            }
        });
        if (ListUtils.isNotEmpty(variants)){
            PlatIdSelectStoreVariantRequest request = new PlatIdSelectStoreVariantRequest();
            request.setStoreId(storeOrder.getStoreId());
            request.setVariantVos(variants);
            // 查询商品信息 补充store_Order_Item表中商品信息
            BaseResponse response = pmsFeignClient.selectByPlatId(request);
            if (response.getCode() == ResultCode.SUCCESS_CODE && null != response.getData()) {
                List<StoreProductVariantVo> variantVos = JSONArray.parseArray(JSON.toJSON(response.getData()).toString()).toJavaList(StoreProductVariantVo.class);
                storeOrderItemDao.completeItemDetail(storeOrderId, variantVos);
            }
        }
        RedisUtil.unLock(redisTemplate, key);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(StoreOrder record) {
        return storeOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(StoreOrder record) {
        return storeOrderDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<StoreOrder> select(Page<StoreOrder> record) {
        record.initFromNum();
        return storeOrderDao.select(record);
    }

    /**
     *
     */
    public long count(Page<StoreOrder> record) {
        return storeOrderDao.count(record);
    }

    /**
     * 客户店铺未关联订单
     *
     * @return
     */
    @Override
    public StoreOrderListResponse disConnectList(StoreOrderListRequest request) {
        if (request.getT() == null || request.getT().getCustomerId() == null) {
            return new StoreOrderListResponse(ResultCode.FAIL_CODE, "客户id不能为空");
        }
        request.initFromNum();
        List<StoreOrderVo> results = storeOrderDao.disConnectList(request);
        Long total = storeOrderDao.disConnectCount(request);
        request.setTotal(total);
        return new StoreOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }

    /**
     * admin所有订单 店铺订单数据
     *
     * @param request
     * @return
     */
    @Override
    public StoreOrderListResponse dataList(StoreDataListRequest request) {
        List<StoreOrderVariantData> results = storeOrderItemDao.dataList(request);
        request.initFromNum();
        Long total = storeOrderItemDao.dataCount(request);
        request.setTotal(total);
        return new StoreOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }

    /**
     * 导出数据列表
     *
     * @param request
     * @return
     */
    @Override
    public StoreOrderListResponse exportList(StoreDataListRequest request) {
        Long total = storeOrderItemDao.dataCount(request);
        if (total > 5) {
            return new StoreOrderListResponse(ResultCode.FAIL_CODE, "数据量超出范围!");
        }
        request.setFields("nopage");
        List<StoreOrderVariantData> results = storeOrderItemDao.dataList(request);
        return new StoreOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }


    /**
     * 根据店铺id查询 店铺下所有订单
     *
     * @param storeId
     * @return
     */
    @Override
    public List<StoreVo> selectOrderListByStoreId(Long storeId) {
        return storeOrderDao.selectOrderListByStoreId(storeId);
    }

    @Override
    public List<AppUserSortVo> listAppUserSortPage(AppUserSortRequest request) {
        return storeOrderDao.listAppUserSortPage(request);
    }

    @Override
    public Long listAppUserSortCount(AppUserSortRequest request) {
        return storeOrderDao.listAppUserSortCount(request);
    }

    public void updateShopifyStoreOrder(StoreOrder storeOrder,ShopifyOrder shopifyOrder,StoreVo storeVo){
        Long storeOrderId = storeOrder.getId();
        //更新店铺地址
        if (null != shopifyOrder.getShipping_address()){
            StoreOrderAddress storeOrderAddress = new StoreOrderAddress(shopifyOrder.getShipping_address());
            storeOrderAddress.setNote(shopifyOrder.getNote());
            storeOrderAddress.setId(storeOrder.getStoreAddressId());
            if (StringUtils.isNotBlank(shopifyOrder.getEmail())) {
                storeOrderAddress.setEmail(shopifyOrder.getEmail());
            } else if (StringUtils.isNotBlank(shopifyOrder.getContact_email())) {
                storeOrderAddress.setEmail(shopifyOrder.getContact_email());
            } else if (shopifyOrder.getCustomer() != null && StringUtils.isNotBlank(shopifyOrder.getCustomer().getEmail())) {
                storeOrderAddress.setEmail(shopifyOrder.getCustomer().getEmail());
            }
            storeOrderAddress.setStoreOrderId(storeOrderId);
            int i = storeOrderAddressDao.updateByPrimaryKey(storeOrderAddress);
            if (i == 1){
                orderAddressService.updateByStoreOrderAddress(storeOrderAddress);
            }
            storeOrder.setAddress( storeOrderAddress);
        }

        //更新店铺订单状态
        StoreOrder newStoreOrder = new StoreOrder(shopifyOrder);
        if (!newStoreOrder.getFinancialStatus().equals(storeOrder.getFinancialStatus())
        || !newStoreOrder.getFulfillmentStatus().equals(storeOrder.getFulfillmentStatus())){
            newStoreOrder.setId(storeOrderId);
            storeOrderDao.updateByPrimaryKeySelective(newStoreOrder);
            storeOrderRelateDao.updateStoreStatusByStoreOrderId(newStoreOrder);
            List<StoreOrderRelate> storeOrderRelates = storeOrderRelateDao.selectByStoreOrderId(storeOrderId);
            if (shopifyOrder.getFinancial_status().equals("refunded")
                    || (shopifyOrder.getFulfillment_status() != null && shopifyOrder.getFulfillment_status().equals("fulfilled"))){
                List<Long> cancelIds = new ArrayList<>();
                for (StoreOrderRelate storeOrderRelate : storeOrderRelates) {
                    cancelIds.add(storeOrderRelate.getOrderId());
                }
                orderService.cancelOrderByIds(cancelIds);
                return;
            }
        }

        //更新店铺产品数量
        Map<String,ShopifyLineItem> lineItemMap = new HashMap<>();
        List<ShopifyLineItem> shopifyLineItems = shopifyOrder.getLine_items();
        for (ShopifyLineItem shopifyLineItem : shopifyLineItems) {
            lineItemMap.put(shopifyLineItem.getId(),shopifyLineItem);
        }

        List<Long> storeOrderItemIds = new ArrayList<>();
        List<StoreOrderItem> storeOrderItems = storeOrderItemDao.selectByStoreOrderId(storeOrderId);
        for (StoreOrderItem storeOrderItem : storeOrderItems) {
            ShopifyLineItem shopifyLineItem = lineItemMap.get(storeOrderItem.getPlatOrderItemId());
            if (null == shopifyLineItem){
                continue;
            }
            lineItemMap.remove(shopifyLineItem.getId());

            Integer quantity = shopifyLineItem.getFulfillable_quantity();
            if (quantity == null || quantity.equals(storeOrderItem.getQuantity())){
                continue;
            }
            storeOrderItemIds.add(storeOrderItem.getId());
            storeOrderItem.setQuantity(quantity);
            storeOrderItemDao.updateByPrimaryKey(storeOrderItem);
            orderItemService.updateQuantityByStoreOrderItemId(storeOrderItem.getId(),quantity);
        }
        if (MapUtils.isNotEmpty(lineItemMap)){
            List<ShopifyLineItem> newItems = new ArrayList<>();
            lineItemMap.forEach((lineItemId,lineItem) -> {
                if (lineItem.getFulfillable_quantity() != null && lineItem.getFulfillable_quantity() != 0){
                    newItems.add(lineItem);
                }
            });
            List<Long> newItemIds = storeOrderAddNewItem(storeOrder,newItems);
            storeOrderItemIds.addAll(newItemIds);
        }
        if (ListUtils.isNotEmpty(storeOrderItemIds)){
            List<Long> orderIds = orderItemService.selectOrderIdsByStoreOrderItemIds(storeOrderItemIds);
            if (ListUtils.isNotEmpty(orderIds)){
                orderService.initOrderProductAmount(orderIds);
                List<Long> cancelIds = new ArrayList<>();
                for (Long orderId : orderIds) {
                    int countItemQuantity = orderItemService.selectCountQuantityByOrderId(orderId);
                    if (countItemQuantity == 0){
                        cancelIds.add(orderId);
                    }else {
                        orderService.matchShipRule(orderId);
                    }
                }
                if (ListUtils.isNotEmpty(cancelIds)){
                    orderService.cancelOrderByIds(orderIds);
                }
            }
        }
    }

    public List<Long> storeOrderAddNewItem(StoreOrder storeOrder,List<ShopifyLineItem> shopifyLineItems){
//        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        if (ListUtils.isEmpty(shopifyLineItems)){
            return new ArrayList<>();
        }
        List<Long> itemIds = new ArrayList<>();
        Long storeOrderId = storeOrder.getId();
        List<StoreOrderItem> storeOrderItems = new ArrayList<>();
        List<StoreProductVariantVo> variants = new ArrayList<>();
        for (ShopifyLineItem shopifyLineItem : shopifyLineItems) {
            StoreOrderItem storeOrderItem = new StoreOrderItem(shopifyLineItem);
            Long id = IdGenerate.nextId();
            storeOrderItem.setPlatOrderId(storeOrder.getPlatOrderId());
            storeOrderItem.setStoreOrderId(storeOrderId);
            storeOrderItem.setId(id);
            itemIds.add(id);
            storeOrderItems .add(storeOrderItem);

            if (shopifyLineItem.getVariant_id() != null &&
                    shopifyLineItem.getProduct_id() != null){
                StoreProductVariantVo variant = new StoreProductVariantVo();
                variant.setPlatProductId(shopifyLineItem.getProduct_id());
                variant.setPlatVariantId(shopifyLineItem.getVariant_id());
                variants.add(variant);
            }
        }

        PlatIdSelectStoreVariantRequest platIdSelectStoreVariantRequest = new PlatIdSelectStoreVariantRequest();
        platIdSelectStoreVariantRequest.setStoreId(storeOrder.getStoreId());
        platIdSelectStoreVariantRequest.setVariantVos(variants);
        if (ListUtils.isNotEmpty(variants)){
            PlatIdSelectStoreVariantRequest request = new PlatIdSelectStoreVariantRequest();
            request.setStoreId(storeOrder.getStoreId());
            request.setVariantVos(variants);
            // 查询商品信息 补充store_Order_Item表中商品信息
            BaseResponse baseResponse = pmsFeignClient.selectByPlatId(request);
            if (baseResponse.getCode() == ResultCode.SUCCESS_CODE && null != baseResponse.getData()) {
                List<StoreProductVariantVo> variantVos = JSONArray.parseArray(JSON.toJSON(baseResponse.getData()).toString()).toJavaList(StoreProductVariantVo.class);
                for (StoreOrderItem storeOrderItem : storeOrderItems) {
                    for (StoreProductVariantVo variantVo : variantVos) {
                        if (variantVo.getPlatVariantId().equals(storeOrderItem.getPlatVariantId())){
                            storeOrderItem.setStoreVariantId(variantVo.getStoreVariantId());
                            storeOrderItem.setStoreProductId(variantVo.getStoreProductId());
                            storeOrderItem.setStoreVariantImage(variantVo.getImage());
                        }
                    }
                }
            }
        }
        storeOrderItemDao.insertByBatch(storeOrderItems);
        orderService.addNewStoreOrderItem(storeOrder,storeOrderItems);
//        platformTransactionManager.commit(transaction);
        return itemIds;
    }
}