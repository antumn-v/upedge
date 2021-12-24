package com.upedge.oms.modules.wholesale.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.old.tms.ShippingUnit;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductVariantTo;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.product.request.ProductVariantShipsRequest;
import com.upedge.common.model.ship.request.AreaListAreaRequest;
import com.upedge.common.model.ship.request.ShipMethodSearchRequest;
import com.upedge.common.model.ship.request.ShippingMethodsRequest;
import com.upedge.common.model.ship.response.ShipMethodSearchResponse;
import com.upedge.common.model.ship.vo.AreaVo;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
import com.upedge.common.model.user.vo.AddressVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.utils.PriceUtils;
import com.upedge.oms.constant.VatRuleType;
import com.upedge.oms.enums.WholesaleOrderTagEnum;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.order.request.ApplyReshipOrderRequest;
import com.upedge.oms.modules.order.request.UpdatePendingOrderRequest;
import com.upedge.oms.modules.order.request.WholesaleOrderAppListRequest;
import com.upedge.oms.modules.order.vo.ReshipOrderItemVo;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.oms.modules.vat.dao.VatRuleDao;
import com.upedge.oms.modules.vat.entity.VatRule;
import com.upedge.oms.modules.wholesale.dao.*;
import com.upedge.oms.modules.wholesale.dto.WholesaleOrderAppListDto;
import com.upedge.oms.modules.wholesale.entity.*;
import com.upedge.oms.modules.wholesale.request.ExcelCreateWholesaleRequest;
import com.upedge.oms.modules.wholesale.request.ExcelCreateWholesaleRequest.WholesaleExcelData;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderListRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderShipUpdateRequest;
import com.upedge.oms.modules.wholesale.response.WholesaleOrderListResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleOrderUpdateResponse;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import com.upedge.oms.modules.wholesale.vo.*;
import com.upedge.thirdparty.saihe.entity.SaiheOrder;
import com.upedge.thirdparty.saihe.entity.SaiheOrderItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
public class WholesaleOrderServiceImpl implements WholesaleOrderService {

    @Autowired
    private WholesaleOrderDao wholesaleOrderDao;
    @Autowired
    private WholesaleTrackingDao wholesaleTrackingDao;
    @Autowired
    WholesaleOrderItemDao wholesaleOrderItemDao;
    @Autowired
    WholesaleOrderAddressDao wholesaleOrderAddressDao;
    @Autowired
    WholesaleReshipInfoDao wholesaleReshipInfoDao;

    @Autowired
    WholesaleExcelRecordDao wholesaleExcelRecordDao;

    @Autowired
    VatRuleDao vatRuleDao;

    @Autowired
    TmsFeignClient tmsFeignClient;
    @Autowired
    PmsFeignClient pmsFeignClient;
    @Autowired
    UmsFeignClient umsFeignClient;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OmsRedisService omsRedisService;

    /**
     * 批发订单和普通订单共用service
     */
    @Autowired
    OrderCommonService orderCommonService;

    /**
     * orderShippingUnitService
     */
    @Autowired
    private OrderShippingUnitService orderShippingUnitService;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleOrder record = new WholesaleOrder();
        record.setId(id);
        return wholesaleOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleOrder record) {
        return wholesaleOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleOrder record) {
        return wholesaleOrderDao.insert(record);
    }

    @Override
    public BaseResponse updateShip(WholesaleOrderShipUpdateRequest request, Session session) {
        Long id = request.getId();
        WholesaleOrder wholesaleOrder = wholesaleOrderDao.selectByPrimaryKey(id);
        if (wholesaleOrder == null){
            return BaseResponse.failed("订单不存在");
        }
        if (wholesaleOrder.getPayState() != OrderConstant.PAY_STATE_UNPAID){
            return BaseResponse.failed("不是待支付订单");
        }
        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,String.valueOf(request.getShipMethodId()));
        if (null== shippingMethodRedis
        || null == request.getShipPrice()
        || BigDecimal.ZERO.compareTo(request.getShipPrice()) == 0){
            return BaseResponse.failed("运输方式不存在或运费不大于0");
        }
        wholesaleOrder = new WholesaleOrder();
        wholesaleOrder.setId(id);
        wholesaleOrder.setFreightReview(1);
        wholesaleOrder.setShipPrice(request.getShipPrice());
        wholesaleOrder.setShipMethodId(request.getShipMethodId());
        wholesaleOrder.setUpdateTime(new Date());
        updateByPrimaryKeySelective(wholesaleOrder);
        return BaseResponse.success();
    }

    @Override
    public Long orderInitToAreaId(WholesaleOrder order) {
        if (null != order.getToAreaId()){
            return order.getToAreaId();
        }
        WholesaleOrderAddress wholesaleOrderAddress = wholesaleOrderAddressDao.queryWholesaleOrderAddressByOrderId(order.getId());
        if(null != wholesaleOrderAddress
        && null != wholesaleOrderAddress.getCountry()){
            Long toAreaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID,wholesaleOrderAddress.getCountry());
            wholesaleOrderDao.updateOrderToAreaId(order.getId(),toAreaId);
            return toAreaId;
        }
        return null;
    }

    @Override
    public List<WholesaleOrderExport> selectOrderTrackingByDate(Long customerId, Date start, Date end) {
        return wholesaleOrderDao.selectOrderTrackingByDate(customerId, start, end);
    }

    @Override
    public List<WholesaleOrderAppVo> orderAppList(WholesaleOrderAppListRequest request, Session session) {
        WholesaleOrderAppListDto appListDto = request.getT();
        if (null == appListDto) {
            appListDto = new WholesaleOrderAppListDto();
        } else {
            appListDto.initDateRange();
            appListDto.initOrderState();
        }

        if (appListDto.getTags().equals("PAID")) {
            appListDto.setPayState(null);
            request.setCondition("wo.pay_state > 0");
        }
        if (appListDto.getTags().equals("REFUND")) {
            appListDto.setRefundState(null);
            request.setCondition("wo.refund_state > 0");
        }
        appListDto.setCustomerId(session.getCustomerId());
        request.setOrderBy("wo.create_time desc");
        request.setT(appListDto);
        request.initFromNum();
        List<WholesaleOrderAppVo> orderAppVos = wholesaleOrderDao.selectOrderAppList(request);
        for (WholesaleOrderAppVo appVo : orderAppVos) {
            String shipMethodName = null;
            if (null != appVo.getShipMethodId()) {
                shipMethodName = (String) redisTemplate.opsForHash().get(RedisKey.HASH_SHIP_METHOD + appVo.getShipMethodId(), "name");
                if (StringUtils.isBlank(shipMethodName)) {
                    BaseResponse response = tmsFeignClient.info(appVo.getShipMethodId());
                    if (ResultCode.SUCCESS_CODE == response.getCode()) {
                        ShippingMethodVo methodVo = JSONObject.parseObject(JSON.toJSONString(response.getData())).toJavaObject(ShippingMethodVo.class);
                        shipMethodName = methodVo.getName();
                    }
                }
                appVo.setShipName(shipMethodName);
            } else {
                ShipDetail shipDetai = orderInitShipDetail(appVo.getId());
                if(shipDetai != null){
                    shipMethodName = shipDetai.getMethodName();
                }
            }
            appVo.setItemVos(wholesaleOrderItemDao.listWholesaleOrderItem(appVo.getId()));
        }

        return orderAppVos;
    }

    @Override
    public Map<String, Long> orderAppCount(WholesaleOrderAppListRequest request, Session session) {
        WholesaleOrderAppListDto appListDto = request.getT();
        Map<String, Long> map = new HashMap<>();
        for (WholesaleOrderTagEnum tag : WholesaleOrderTagEnum.values()) {
            if (null == appListDto) {
                appListDto = new WholesaleOrderAppListDto();
            } else {
                appListDto.setTags(tag.name());
                appListDto.initDateRange();
                appListDto.initOrderState();
            }
            if (tag.name().equals("PAID")) {
                appListDto.setPayState(null);
                request.setCondition("wo.pay_state > 0");
            } else {
                request.setCondition(null);
            }
            appListDto.setCustomerId(session.getCustomerId());
            request.setT(appListDto);
            Long count = wholesaleOrderDao.selectOrderAppCount(request);
            map.put(tag.name(), count);
        }
        return map;
    }

    @Override
    public List<ShipDetail> orderShipMethods(Long orderId, Long areaId) {
        Page<WholesaleOrderItem> page = new Page<>();
        WholesaleOrderItem wholesaleOrderItem = new WholesaleOrderItem();
        wholesaleOrderItem.setOrderId(orderId);
        page.setT(wholesaleOrderItem);
        List<WholesaleOrderItem> items = wholesaleOrderItemDao.select(page);
        BigDecimal weight = BigDecimal.ZERO;
        BigDecimal volumn = BigDecimal.ZERO;
        List<String> strings = new ArrayList<>();
        for (WholesaleOrderItem item : items) {
            BigDecimal quantity = new BigDecimal(item.getQuantity());
            weight = weight.add(item.getAdminVariantWeight().multiply(quantity));
            volumn = volumn.add(item.getAdminVariantVolume().multiply(quantity));
            strings.add(RedisKey.SHIPPING_METHODS + item.getShippingId());
        }
        Set<Object> shipMethodIds = redisTemplate.opsForSet().union(strings);
        if (ListUtils.isNotEmpty(shipMethodIds)) {
            Set<Long> methodIds = new HashSet<>();
            shipMethodIds.forEach(shipMethodId -> {
                methodIds.add((Long) shipMethodId);
            });
            ShipMethodSearchRequest searchRequest = new ShipMethodSearchRequest();
            searchRequest.setToAreaId(areaId);
            searchRequest.setWeight(weight);
            searchRequest.setVolumeWeight(volumn);
            searchRequest.setMethodIds(methodIds);
            ShipMethodSearchResponse searchResponse = tmsFeignClient.shipSearch(searchRequest);
            if (searchResponse.getCode() == ResultCode.SUCCESS_CODE) {
                List<ShipDetail> shipDetails = JSONArray.parseArray(JSON.toJSONString(searchResponse.getData())).toJavaList(ShipDetail.class);
                return shipDetails;
            }
        }
        return null;
    }

    ShipDetail orderShipMethodCheck(Long orderId, Long customerId, Long areaId, Long shipMethodId) {
        List<VariantDetail> variantDetails = wholesaleOrderItemDao.selectAdminVariantDetailByOrder(orderId);

        ProductVariantShipsRequest request = new ProductVariantShipsRequest();
        request.setAreaId(areaId);
        request.setCustomerId(customerId);
        request.setVariantDetails(variantDetails);
        request.setShipMethodId(shipMethodId);
        BaseResponse response = pmsFeignClient.productSearchShips(request);
        if (response.getCode() == ResultCode.SUCCESS_CODE) {
            ShipDetail shipDetail = JSONObject.parseObject(JSON.toJSONString(response.getData())).toJavaObject(ShipDetail.class);
            return shipDetail;
        }
        return null;
    }

    /**
     *
     */
    public WholesaleOrder selectByPrimaryKey(Long id) {
        return wholesaleOrderDao.selectByPrimaryKey(id);
    }

    @Override
    public List<WholesaleOrderItemVo> selectOrderItems(Long id) {
        return wholesaleOrderItemDao.listWholesaleOrderItem(id);
    }

    @Override
    public List<ExcelCreateWholesaleRequest.WholesaleExcelData> excelDataCheck(ExcelCreateWholesaleRequest request, Session session) {
        List<ExcelCreateWholesaleRequest.WholesaleExcelData> wholesaleExcelDates = request.getWholesaleExcelDataList();
        List<WholesaleNameNumber> nameNumbers = wholesaleExcelRecordDao.selectNameNumbersByCustomer(session.getCustomerId());
        Map<String, List<String>> map = new HashMap<>();
        if (ListUtils.isNotEmpty(nameNumbers)) {
            for (WholesaleNameNumber nameNumber : nameNumbers) {
                map.put(nameNumber.getStoreName(), nameNumber.getNumbers());
            }
        }
        List<ExcelCreateWholesaleRequest.WholesaleExcelData> dataList = new ArrayList<>();
        if (wholesaleExcelDates.size() > 500) {
            dataList = wholesaleExcelDates.subList(0, 500);
        } else {
            dataList = wholesaleExcelDates;
        }
        //创建一个线程池
        try {
            Map<String, WholesaleExcelData> orderAddress = new ConcurrentHashMap<>();
            Map<String, Integer> skuVariant = new ConcurrentHashMap<>();
            int threadNum = Runtime.getRuntime().availableProcessors();
            int threadSize = dataList.size() / threadNum;//给每个线程分发处理条数(总条数/线程数);
            ExecutorService eService = Executors.newFixedThreadPool(threadNum);//创建线程池
            List<Callable<List<ExcelCreateWholesaleRequest.WholesaleExcelData>>> cList = new ArrayList<Callable<List<ExcelCreateWholesaleRequest.WholesaleExcelData>>>();
            Callable<List<ExcelCreateWholesaleRequest.WholesaleExcelData>> task = null;
            List<ExcelCreateWholesaleRequest.WholesaleExcelData> sList = null;
            for (int i = 0; i < threadNum; i++) {
                if (i == threadNum - 1) {
                    sList = dataList.subList(i * threadSize, dataList.size());
                } else {
                    sList = dataList.subList(i * threadSize, (i + 1) * threadSize);
                }
                final List<ExcelCreateWholesaleRequest.WholesaleExcelData> nowList = sList;
                task = new Callable<List<ExcelCreateWholesaleRequest.WholesaleExcelData>>() {
                    @Override
                    public List<WholesaleExcelData> call() throws Exception {
                        List<WholesaleExcelData> list = new ArrayList<>();
                        for (int j = 0; j < nowList.size(); j++) {
                            //处理需要处理的业务
                            ExcelCreateWholesaleRequest.WholesaleExcelData data = nowList.get(j);
                            //----------------------验证是否有重复订单号-------------------------------
                            String tags = data.getStoreTags();
                            String number = data.getCustomerOrderNumber();
                            if (map.containsKey(tags)) {
                                if (map.get(tags).contains(number)) {
                                    data.setState(4);
                                    data.setFailReason("The same order cannot be imported repeatedly");
                                    list.add(data);
                                    continue;
                                }
                            }
                            //-----------------------------------------------------------------------
                            //--------------------------验证国家--------------------------
                            String country = data.getCountry().trim();
                            String sku = data.getSku();

                            Long id = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, country);
                            if (id == null) {
                                data.setState(6);
                                data.setFailReason("Country name error");
                                list.add(data);
                                continue;
                            }
                            //------------------------------------------------------------
                            //---------------------------验证必填信息---------------------------
                            if (StringUtils.isBlank(tags)
                                    || StringUtils.isBlank(data.getCustomerOrderNumber())
                                    || StringUtils.isBlank(sku)
                                    || data.getQuantity() == null
                                    || data.getQuantity() < 1
                                    || StringUtils.isBlank(data.getEmail())
                                    || StringUtils.isBlank(data.getFirstName())
                                    || StringUtils.isBlank(data.getLastName())
                                    || StringUtils.isBlank(data.getCity())
                                    || StringUtils.isBlank(data.getProvince())
                                    || StringUtils.isBlank(data.getPhone())
                                    || StringUtils.isBlank(data.getAddress1())
                                    || StringUtils.isBlank(data.getZip())) {
                                data.setState(0);
                                data.setFailReason("The key information is indeed!");
                                list.add(data);
                                continue;
                            }
                            //--------------------------------------------------------------
                            //-----------------------比较相同订单下的地址---------------------
                            String ss = tags.concat(number);
                            if (orderAddress.containsKey(ss)) {
                                WholesaleExcelData excelData = orderAddress.get(ss);
                                if (!excelData.equals(data)) {
                                    data.setState(3);
                                    excelData.setState(3);
                                    data.setFailReason("Different addresses under the same order");
                                    excelData.setFailReason("Different addresses under the same order");
                                    continue;
                                }
                            } else {
                                orderAddress.put(ss, data);
                            }
                            //---------------------------------------------------------------
                            //--------------------验证sku----------------------------------
                            Integer i = 0;
                            if (skuVariant.containsKey(sku)) {
                                i = skuVariant.get(sku);
                            } else {
                                BaseResponse response = pmsFeignClient.selectVariantBySku(sku);
                                i = response.getCode();
                                skuVariant.put(sku, i);
                            }
                            if (1 != i) {
                                data.setFailReason("SKU does not exist");
                                data.setState(2);
                            } else {
                                data.setState(1);
                            }
                            list.add(data);
                            //----------------------------------------------------------------
                        }
                        //返回处理的结果集
                        return list;
                    }
                };
                cList.add(task);
            }
            List<Future<List<WholesaleExcelData>>> results;
            results = eService.invokeAll(cList);
            eService.shutdown();

            dataList = new ArrayList<>();
            for (Future<List<WholesaleExcelData>> str : results) {
                dataList.addAll(str.get());
            }
            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrdersByExcel(Map<String, WholesaleExcelData> orderAddress, Map<String, Map<String, Integer>> orderSkuList, Long customerId) {
        List<WholesaleNameNumber> nameNumbers = wholesaleExcelRecordDao.selectNameNumbersByCustomer(customerId);
        Map<String, List<String>> map = new HashMap<>();
        if (ListUtils.isNotEmpty(nameNumbers)) {
            for (WholesaleNameNumber nameNumber : nameNumbers) {
                map.put(nameNumber.getStoreName(), nameNumber.getNumbers());
            }
        }

        Map<String, VariantDetail> variantMap = new HashMap<>();
        List<WholesaleOrderItem> orderItems = new ArrayList<>();
        List<WholesaleOrder> orders = new ArrayList<>();
        List<WholesaleOrderAddress> addresses = new ArrayList<>();
        List<WholesaleExcelRecord> records = new ArrayList<>();
        Date date = new Date();
        String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, customerId.toString());

        orderAddress.forEach((order, data) -> {
            String tags = data.getStoreTags();
            String number = data.getCustomerOrderNumber();
            if (map.containsKey(tags)) {
                if (map.get(tags).contains(number)) {
                    return;
                }
            }
            Map<String, Integer> skuList = orderSkuList.get(order);
            if (MapUtils.isNotEmpty(skuList)) {
                Long orderId = IdGenerate.nextId();
                Long addressId = IdGenerate.nextId();
                WholesaleOrderAddress address = new WholesaleOrderAddress();
                BeanUtils.copyProperties(data, address);
                address.setOrderId(orderId);
                address.setId(addressId);
                addresses.add(address);

                WholesaleOrder wholesaleOrder = new WholesaleOrder();
                wholesaleOrder.setId(orderId);
                wholesaleOrder.setOrderType(1);
                wholesaleOrder.initOrder(wholesaleOrder);
                wholesaleOrder.setCustomerId(customerId);
                wholesaleOrder.setCreateTime(date);
                wholesaleOrder.setUpdateTime(date);
                wholesaleOrder.setManagerCode(managerCode);
                wholesaleOrder.setToAreaId((Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID, address.getCountry()));
                orders.add(wholesaleOrder);

                WholesaleExcelRecord record = new WholesaleExcelRecord();
                record.setCustomerId(customerId);
                record.setCreateTime(date);
                record.setCustomerOrderNumber(data.getCustomerOrderNumber());
                record.setStoreName(data.getStoreTags());
                record.setWholesaleOrderId(orderId);
                records.add(record);

                skuList.forEach((sku, quantity) -> {
                    sku = sku.substring(sku.indexOf(" ") + 1);
                    VariantDetail variant = null;
                    if (variantMap.containsKey(sku)) {
                        variant = variantMap.get(sku);
                    } else {
                        BaseResponse response = pmsFeignClient.selectVariantBySku(sku);
                        if (ResultCode.SUCCESS_CODE == response.getCode()) {
                            variant = JSONObject.parseObject(JSON.toJSONString(response.getData())).toJavaObject(VariantDetail.class);
                        }
                        variantMap.put(sku, variant);
                    }
                    if (null != variant) {
                        Long productId = variant.getProductId();
                        WholesaleOrderItem item = new WholesaleOrderItem();
                        item.setQuantity(quantity);
                        item.setDischargeQuantity(0);
                        item.setAdminVariantSku(sku);
                        item.setCartId(0L);
                        item.setOrderId(orderId);
                        item.setAdminProductId(productId);
                        item.setShippingId(variant.getProductShippingId());
                        item.setUsdPrice(PriceUtils.cnyToUsdByDefaultRate(variant.getCnyPrice()));
                        item.setAdminVariantId(variant.getVariantId());
                        item.setAdminVariantWeight(variant.getWeight());
                        item.setAdminVariantVolume(variant.getVolume());
                        item.setAdminVariantImage(variant.getVariantImage());
                        orderItems.add(item);
                    }
                });
            }
        });
        if (ListUtils.isEmpty(orders)) {
            return;
        }
        wholesaleOrderDao.insertByBatch(orders);
        wholesaleOrderItemDao.insertByBatch(orderItems);
        wholesaleOrderAddressDao.insertByBatch(addresses);
        wholesaleExcelRecordDao.insertByBatch(records);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean orderUpdateAddress(Long id, AddressVo addressVo) {
        WholesaleOrder wholesaleOrder = wholesaleOrderDao.selectByPrimaryKey(id);
        if (null == wholesaleOrder || 0 != wholesaleOrder.getPayState()) {
            return false;
        }
        WholesaleOrderAddress address = wholesaleOrderAddressDao.queryWholesaleOrderAddressByOrderId(id);
        a:
        if (!address.getCountry().equals(addressVo.getCountry())
        || wholesaleOrder.getToAreaId() == null) {
            Long toAreaId = (Long) redisTemplate.opsForHash().get(RedisKey.HASH_COUNTRY_AREA_ID,address.getCountry());
            wholesaleOrderDao.updateOrderToAreaId(id,toAreaId);

            if (null != toAreaId) {
                List<VariantDetail> variantDetails = wholesaleOrderItemDao.selectAdminVariantDetailByOrder(id);

                wholesaleOrder.setToAreaId(toAreaId);
                ProductVariantShipsRequest productVariantShipsRequest = new ProductVariantShipsRequest();
                productVariantShipsRequest.setAreaId(toAreaId);
                productVariantShipsRequest.setCustomerId(wholesaleOrder.getCustomerId());
                productVariantShipsRequest.setVariantDetails(variantDetails);
                BaseResponse response = pmsFeignClient.productSearchShips(productVariantShipsRequest);

                if (response.getCode() == ResultCode.SUCCESS_CODE) {
                    List<ShipDetail> shipDetails = JSONArray.parseArray(JSON.toJSONString(response.getData())).toJavaList(ShipDetail.class);
                    wholesaleOrder = new WholesaleOrder();
                    if (ListUtils.isNotEmpty(shipDetails)) {
                        ShipDetail shipDetail = shipDetails.get(0);
                        wholesaleOrderDao.updateOrderShipDetail(shipDetail.getMethodId(), shipDetail.getPrice(), shipDetail.getWeight(), id);
                        break a;
                    }
                }
                wholesaleOrderDao.updateOrderShipDetail(null, BigDecimal.ZERO, BigDecimal.ZERO, id);
            } else {
                return false;
            }
        }
        BeanUtils.copyProperties(addressVo, address);
        address.setOrderId(id);
        wholesaleOrderAddressDao.updateByOrderId(address);
        return true;
    }

    @Transactional
    @Override
    public BaseResponse orderUpdateShipMethod(Long id, ShipDetail shipDetail) {
        WholesaleOrder wholesaleOrder = wholesaleOrderDao.selectByPrimaryKey(id);
        if (null != wholesaleOrder && 0 == wholesaleOrder.getPayState()) {

            shipDetail = orderShipMethodCheck(id, wholesaleOrder.getCustomerId(), wholesaleOrder.getToAreaId(), shipDetail.getMethodId());
            if (null == shipDetail) {
                return BaseResponse.failed();
            }

            if (shipDetail.getPrice().compareTo(wholesaleOrder.getShipPrice()) != 0
                    || !shipDetail.getMethodId().equals(wholesaleOrder.getShipMethodId())
                    || shipDetail.getWeight().compareTo(wholesaleOrder.getTotalWeight()) != 0) {
                wholesaleOrderDao.updateOrderShipDetail(shipDetail.getMethodId(), shipDetail.getPrice(), shipDetail.getWeight(), id);
            }

            // 删除原来的unit  并插入新的冗余
            //OrderShippingUnitVo OrderShippingUnitVo = orderShippingUnitService.selectByOrderId(id,OrderType.WHOLESALE);
            orderShippingUnitService.delByOrderId(id, OrderType.WHOLESALE);
            BaseResponse response = tmsFeignClient.unitInfo(shipDetail.getShippingUtilId());
            if (response.getCode() == ResultCode.SUCCESS_CODE && response.getData() != null) {
                ShippingUnit shippingUnit = JSON.parseObject(JSON.toJSONString(response.getData()), ShippingUnit.class);
                OrderShippingUnit orderShippingUnit = new OrderShippingUnit();
                BeanUtils.copyProperties(shippingUnit, orderShippingUnit);
                orderShippingUnit.setOrderId(id);
                orderShippingUnit.setOrderType(OrderType.WHOLESALE);
                orderShippingUnit.setId(IdGenerate.nextId());
                orderShippingUnit.setShipUnitId(shippingUnit.getId());
                orderShippingUnit.setCreateTime(new Date());
                orderShippingUnitService.insert(orderShippingUnit);
            }


            wholesaleOrder.setShipPrice(shipDetail.getPrice());
            BigDecimal vatAmount = updateOrderVatAmount(wholesaleOrder);
            Map<String, Object> map = new HashMap<>();
            map.put("vatAmount", vatAmount);
            map.put("ship", shipDetail);
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, map);
        }
        return BaseResponse.failed();
    }

    @Override
    public int updatePayStateById(Long id, Integer state) {
        return wholesaleOrderDao.updatePayStateById(id, state);
    }

    BigDecimal updateOrderVatAmount(WholesaleOrder wholesaleOrder) {
        VatRule rule = vatRuleDao.selectVatRuleByAreaId(wholesaleOrder.getToAreaId());
        BigDecimal vatAmount = BigDecimal.ZERO;
        if (null != rule) {
            switch (rule.getMethodType()) {
                case VatRuleType
                        .ORDER_AMOUNT:
                    vatAmount = (wholesaleOrder.getProductAmount().add(wholesaleOrder.getShipPrice())).multiply(rule.getRatio());
                    break;
                case VatRuleType.PRODUCT_AMOUNT:
                    vatAmount = wholesaleOrder.getProductAmount().multiply(rule.getRatio());
                    break;
            }
            if (vatAmount.compareTo(rule.getMinAmount()) == -1) {
                vatAmount = rule.getMinAmount();
            } else if (vatAmount.compareTo(rule.getMaxAmount()) == 1) {
                vatAmount = rule.getMaxAmount();
            }
        }
        if (vatAmount.compareTo(wholesaleOrder.getVatAmount()) != 0) {
            wholesaleOrderDao.updateVatAmountById(wholesaleOrder.getId(), vatAmount);
        }
        return vatAmount;
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleOrder record) {
        return wholesaleOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(WholesaleOrder record) {
        return wholesaleOrderDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<WholesaleOrder> select(Page<WholesaleOrder> record) {
        record.initFromNum();
        return wholesaleOrderDao.select(record);
    }

    /**
     *
     */
    public long count(Page<WholesaleOrder> record) {
        return wholesaleOrderDao.count(record);
    }

    /**
     * 批发订单列表
     *
     * @param request
     * @return
     */
    @Override
    public WholesaleOrderListResponse adminList(WholesaleOrderListRequest request, Session session) {
        if (request.getT() == null) {
            request.setT(new WholesaleOrder());
        }
        WholesaleOrder w = request.getT();
        //支付状态,待支付=0，已支付=1，取消订单=-1，支付中=2
        w.setPayState(1);
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        w.setRefundState(0);
        //  正常 = 0,待审查 = 1,作废 = 2
        w.setOrderStatus(0);
        //0=未发货。1=已发货。
        w.setShipState(0);
        request.initFromNum();
        request.setOrderBy("pay_time desc");
        List<WholesaleOrder> results = wholesaleOrderDao.select(request);
        Long total = wholesaleOrderDao.count(request);
        request.setTotal(total);

        List<WholesaleOrderVo> wholesaleOrderVoList = new ArrayList<>();
        try {
            wholesaleOrderVoList = loadWholesaleOrderInfo(results);
        } catch (Exception e) {
            e.printStackTrace();
            return new WholesaleOrderListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        return new WholesaleOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, wholesaleOrderVoList, request);
    }

    /**
     * 获取订单批发信息
     *
     * @param results
     * @return
     * @throws Exception
     */
    private List<WholesaleOrderVo> loadWholesaleOrderInfo(List<WholesaleOrder> results) throws Exception {
        List<WholesaleOrderVo> wholesaleOrderVoList = new ArrayList<>();
        if (results.size() == 0) {
            return wholesaleOrderVoList;
        }
        List<Long> areaIds = new ArrayList<>();
        List<Long> shipIds = new ArrayList<>();
        List<Long> reshipIds = new ArrayList<>();
        List<Long> trackOrderIds = new ArrayList<>();
        Set<Long> orderIds = new HashSet<>();
        results.forEach(order -> {
            WholesaleOrderVo orderListVo = new WholesaleOrderVo();
            BeanUtils.copyProperties(order, orderListVo);
            wholesaleOrderVoList.add(orderListVo);
            areaIds.add(order.getToAreaId());
            shipIds.add(order.getShipMethodId());
            orderIds.add(order.getId());
            if (order.getOrderType() == 2) {
                reshipIds.add(order.getId());
            }
            if (order.getShipState() == 1) {
                trackOrderIds.add(order.getId());
            }
        });

        //客户经理
        CompletableFuture<Void> manageFuture = CompletableFuture.runAsync(() -> {
            for (WholesaleOrderVo orderVo : wholesaleOrderVoList) {
              /*  BaseResponse baseResponse = umsFeignClient.getCustomerManager(orderVo.getCustomerId());
                if (baseResponse.getCode() == ResultCode.SUCCESS_CODE){
                    ManagerInfoVo managerInfoVo =   JSON.parseObject(JSON.toJSONString( baseResponse.getData()),ManagerInfoVo.class);
                    if (managerInfoVo != null){
                        orderVo.setManagerName(managerInfoVo.getManagerName());
                        orderVo.setManagerCode(managerInfoVo.getManagerCode());
                    }
                }*/
                String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, orderVo.getCustomerId().toString());
                ManagerInfoVo managerInfoVo = (ManagerInfoVo) redisTemplate.opsForValue().get(RedisKey.STRING_MANAGER_INFO + managerCode);
                if (null != managerInfoVo && StringUtils.isNotBlank(managerInfoVo.getManagerName())) {
                    orderVo.setManagerName(managerInfoVo.getManagerName());
                }
                orderVo.setManagerCode(managerCode);
            }
        }, threadPoolExecutor);

        //  客户信息
        CompletableFuture<Void> customerFuture = CompletableFuture.runAsync(() -> {
            for (WholesaleOrderVo orderVo : wholesaleOrderVoList) {
                BaseResponse customerResponse = umsFeignClient.customerInfo(orderVo.getCustomerId());
                if (customerResponse.getCode() == ResultCode.SUCCESS_CODE) {
                    CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(customerResponse.getData()), CustomerVo.class);
                    if (customerVo != null) {
                        orderVo.setCustomerLoginName(customerVo.getLoginName());
                    }
                }
            }
        }, threadPoolExecutor);
        //目的地信息
        CompletableFuture<Void> areaFuture = CompletableFuture.runAsync(() -> {
            Map<Long, AreaVo> areaMap = new HashMap<>();
            AreaListAreaRequest req = new AreaListAreaRequest();
            req.setIds(areaIds);
            BaseResponse response = tmsFeignClient.listArea(req);
            List<LinkedHashMap> mapList = (List<LinkedHashMap>) response.getData();
            mapList.forEach(v -> {
                AreaVo areaVo = JSON.parseObject(JSON.toJSONString(v), AreaVo.class);
                areaMap.put(areaVo.getId(), areaVo);
            });
            wholesaleOrderVoList.forEach(wholesaleOrderVo -> {
                AreaVo a = areaMap.get(wholesaleOrderVo.getToAreaId());
                if (a != null) {
                    wholesaleOrderVo.setToAreaName(a.getName());
                }
            });
        }, threadPoolExecutor);

        //运输方式信息
        CompletableFuture<Void> shipFuture = CompletableFuture.runAsync(() -> {
            Map<Long, ShippingMethodVo> shipMap = new HashMap<>();
            ShippingMethodsRequest shippingMethodsRequest = new ShippingMethodsRequest();
            shippingMethodsRequest.setIds(shipIds);
            BaseResponse responseShipMethod = tmsFeignClient.listShippingMethod(shippingMethodsRequest);
            List<LinkedHashMap> mapList = (List<LinkedHashMap>) responseShipMethod.getData();
            mapList.forEach(m -> {
                ShippingMethodVo shippingMethodVo = JSON.parseObject(JSON.toJSONString(m), ShippingMethodVo.class);
                shipMap.put(shippingMethodVo.getId(), shippingMethodVo);
            });
            wholesaleOrderVoList.forEach(wholesaleOrderVo -> {
                ShippingMethodVo s = shipMap.get(wholesaleOrderVo.getShipMethodId());
                if (s != null) {
                    wholesaleOrderVo.setShippingMethodName(s.getName());
                    wholesaleOrderVo.setSaiheTransportId(s.getSaiheTransportId());
                    wholesaleOrderVo.setSaiheTransportName(s.getSaiheTransportName());
                }
            });
        }, threadPoolExecutor);

        //补发订单补发信息
        CompletableFuture<Void> reshipFuture = CompletableFuture.runAsync(() -> {
            if (reshipIds.size() > 0) {
                Map<Long, WholesaleReshipInfo> reshipInfoMap = new HashMap<>();
                //补发订单
                List<WholesaleReshipInfo> orderReshipInfoList = wholesaleReshipInfoDao.listOrderReshipInfoByIds(reshipIds);
                orderReshipInfoList.forEach(orderReshipInfo -> {
                    reshipInfoMap.put(orderReshipInfo.getOrderId(), orderReshipInfo);
                });
                wholesaleOrderVoList.forEach(wholesaleOrderVo -> {
                    WholesaleReshipInfo a = reshipInfoMap.get(wholesaleOrderVo.getId());
                    //补发订单
                    if (a != null && wholesaleOrderVo.getOrderType() == 2) {
                        wholesaleOrderVo.setReshipTimes(a.getReshipTimes());
                        wholesaleOrderVo.setOriginalOrderId(a.getOriginalOrderId());
                    }
                });
            }
        }, threadPoolExecutor);

        CompletableFuture.allOf(customerFuture, manageFuture, areaFuture, shipFuture, reshipFuture).get();

        return wholesaleOrderVoList;
    }

    /**
     * 历史批发订单
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public WholesaleOrderListResponse historyList(WholesaleOrderListRequest request, Session session) {

        request.initFromNum();
        request.setOrderBy("update_time desc");
        List<WholesaleOrder> results = wholesaleOrderDao.select(request);
        Long total = wholesaleOrderDao.count(request);
        request.setTotal(total);
        List<WholesaleHistoryVo> wholesaleHistoryVoList = new ArrayList<>();

        List<WholesaleOrderVo> orderListVos;
        try {
            orderListVos = loadWholesaleOrderInfo(results);
        } catch (Exception e) {
            e.printStackTrace();
            return new WholesaleOrderListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        orderListVos.forEach(wholesaleOrderVo -> {
            WholesaleHistoryVo wholesaleHistoryVo = new WholesaleHistoryVo();
            BeanUtils.copyProperties(wholesaleOrderVo, wholesaleHistoryVo);
            wholesaleHistoryVoList.add(wholesaleHistoryVo);
        });
        return new WholesaleOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, wholesaleHistoryVoList, request);

    }

    /**
     * 批发订单详情页
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse orderDetails(Long id) {

        WholesaleDetailsVo wholesaleDetailsVo = new WholesaleDetailsVo();
        //查询订单信息
        CompletableFuture<Void> orderFuture = CompletableFuture.runAsync(() -> {
            WholesaleOrder wholesaleOrder = wholesaleOrderDao.selectByPrimaryKey(id);
            WholesaleOrderVo wholesaleOrderVo = new WholesaleOrderVo();
            BeanUtils.copyProperties(wholesaleOrder, wholesaleOrderVo);
            if (wholesaleOrder.getShipState() == 1) {
                WholesaleTracking orderTracking = wholesaleTrackingDao.queryWholesaleTrackingByOrderId(wholesaleOrder.getId());
                if (orderTracking != null) {
                    wholesaleOrderVo.setShipMethodName(orderTracking.getShippingMethodName());
                    wholesaleOrderVo.setTrackingCode(orderTracking.getTrackingCode());
                }
            }
            wholesaleDetailsVo.setOrder(wholesaleOrderVo);
        }, threadPoolExecutor);

        List<Long> variantIds = new ArrayList<>();
        Map<Long, ProductVariantTo> variantDetailMap = new HashMap<>();
        List<WholesaleOrderItemVo> orderItemVoList = new ArrayList<>();
        //订单项列表
        CompletableFuture<Void> orderItemsFuture = CompletableFuture.runAsync(() -> {
            List<WholesaleOrderItemVo> orderItems = wholesaleOrderItemDao.listWholesaleOrderItem(id);
            orderItems.forEach(orderItem -> {
                variantIds.add(orderItem.getAdminVariantId());
                orderItemVoList.add(orderItem);
            });
        }, threadPoolExecutor).thenRunAsync(() -> {
            //产品信息
            ListVariantsRequest request = new ListVariantsRequest();
            request.setVariantIds(variantIds);
            BaseResponse response = pmsFeignClient.listVariantByIds(request);
            List<LinkedHashMap> variantDetailList = (List<LinkedHashMap>) response.getData();
            variantDetailList.forEach(v -> {
                ProductVariantTo variantDetail = JSON.parseObject(JSON.toJSONString(v), ProductVariantTo.class);
                variantDetailMap.put(variantDetail.getId(), variantDetail);
            });
            orderItemVoList.forEach(orderItemVo -> {
                ProductVariantTo variantDetail = variantDetailMap.get(orderItemVo.getAdminVariantId());
                orderItemVo.setAdminVariantSku(variantDetail.getVariantSku());
                orderItemVo.setAdminVariantImg(variantDetail.getVariantImage());
                orderItemVo.setAdminVariantName(variantDetail.getCnName());
            });
            wholesaleDetailsVo.setOrderItems(orderItemVoList);
        });

        //地址信息
        CompletableFuture<Void> orderAddressFuture = CompletableFuture.runAsync(() -> {
            WholesaleOrderAddress orderAddress = wholesaleOrderAddressDao.queryWholesaleOrderAddressByOrderId(id);
            wholesaleDetailsVo.setOrderAddress(orderAddress);
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(orderFuture, orderItemsFuture, orderAddressFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, wholesaleDetailsVo);
    }

    /**
     * 申请补发订单
     *
     * @param request
     * @param session
     * @return
     * @throws CustomerException
     */
    @Override
    public BaseResponse applyWholesaleReshipOrder(ApplyReshipOrderRequest request, Session session) throws CustomerException {

        WholesaleOrder wholesaleOrder = wholesaleOrderDao.selectByPrimaryKey(request.getOriginalOrderId());

        int existCount = wholesaleOrderDao.existPendingOrderByOriginal(request.getOriginalOrderId());
        if (existCount > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单在补发待审核中!");
        }
        int c = wholesaleOrderDao.existReshipOrderWaitTrackByOriginal(request.getOriginalOrderId());
        if (c > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "存在未发货的补发订单!");
        }

        if (wholesaleOrder == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单不存在!");
        }
        if (wholesaleOrder.getPayState() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单状态异常!");
        }
        if (wholesaleOrder.getPayState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单状态异常!");
        }
        if (wholesaleOrder.getShipState() == null
                || wholesaleOrder.getShipState() == 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单未发货!");
        }

        //正常订单才能补发
        if (wholesaleOrder.getOrderType() == 2) {
            return new BaseResponse(ResultCode.FAIL_CODE, "该订单不满足补发条件!");
        }

        List<ReshipOrderItemVo> items = request.getItems();
        if (items == null || items.size() == 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "没有订单项!");
        }

        List<WholesaleOrderItem> orderItems = new ArrayList<>();
        List<Long> variantIds = new ArrayList<>();
        for (ReshipOrderItemVo reshipOrderItemVo : items) {
            Long itemId = reshipOrderItemVo.getItemId();
            if (itemId == null || reshipOrderItemVo.getQuantity() == null
                    || reshipOrderItemVo.getQuantity() <= 0) {
                return new BaseResponse(ResultCode.FAIL_CODE, "订单项参数异常!");
            }
            WholesaleOrderItem wholesaleOrderItem = wholesaleOrderItemDao.selectByPrimaryKey(itemId);
            if (wholesaleOrderItem == null) {
                return new BaseResponse(ResultCode.FAIL_CODE, "订单项不存在!");
            }
            if (wholesaleOrderItem.getAdminVariantId() == null
                    || !wholesaleOrderItem.getAdminVariantId().
                    equals(reshipOrderItemVo.getAdminVariantId())) {
                return new BaseResponse(ResultCode.FAIL_CODE, "订单项参数异常!");
            }
            if (reshipOrderItemVo.getQuantity() > wholesaleOrderItem.getQuantity()) {
                return new BaseResponse(ResultCode.FAIL_CODE, "补发数量异常!");
            }
            //使用新的修改的倍数和数量
            wholesaleOrderItem.setQuantity(reshipOrderItemVo.getQuantity());
            variantIds.add(wholesaleOrderItem.getAdminVariantId());
            orderItems.add(wholesaleOrderItem);
        }

        ShippingMethodsRequest shippingMethodsRequest = new ShippingMethodsRequest();
        shippingMethodsRequest.setIds(Collections.singletonList(request.getShipMethodId()));
        BaseResponse responseShipMethod = tmsFeignClient.listShippingMethod(shippingMethodsRequest);
        List<LinkedHashMap> mapList = (List<LinkedHashMap>) responseShipMethod.getData();
        if (responseShipMethod.getCode() == 0 || mapList == null || mapList.size() == 0) {
            return responseShipMethod;
        }
        LinkedHashMap map = mapList.get(0);
        ShippingMethodVo shippingMethodVo = JSON.parseObject(JSON.toJSONString(map), ShippingMethodVo.class);

        ListVariantsRequest r = new ListVariantsRequest();
        r.setVariantIds(variantIds);
        BaseResponse response = pmsFeignClient.listVariantByIds(r);
        if (response.getCode() == 0) {
            return response;
        }
        response.getData();
        List<LinkedHashMap> linkedHashMaps = (List<LinkedHashMap>) response.getData();
        Map<Long, BigDecimal> weightMap = new HashMap<>();
        linkedHashMaps.forEach(linkedHashMap -> {
            ProductVariantTo variantDetail = JSON.parseObject(JSON.toJSONString(linkedHashMap), ProductVariantTo.class);
            //0:实重 1:体积重
            if (shippingMethodVo.getWeightType() == 0) {
                weightMap.put(variantDetail.getId(), variantDetail.getWeight());
            } else {
                weightMap.put(variantDetail.getId(), variantDetail.getVolumeWeight());
            }
        });

        WholesaleOrderAddress wholesaleOrderAddress = wholesaleOrderAddressDao.queryWholesaleOrderAddressByOrderId(request.getOriginalOrderId());
        if (wholesaleOrderAddress == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "收获信息异常!");
        }

        String userCode = String.valueOf(session.getId());
        Long orderId = IdGenerate.nextId();
        wholesaleOrder.setId(orderId);
        //使用选择的运输方式
        wholesaleOrder.setShipMethodId(request.getShipMethodId());
        wholesaleOrder.setPayTime(new Date());
        wholesaleOrder.setPayAmount(BigDecimal.ZERO);
        wholesaleOrder.setShipPrice(BigDecimal.ZERO);
        wholesaleOrder.setProductAmount(BigDecimal.ZERO);
        wholesaleOrder.setProductDischargeAmount(BigDecimal.ZERO);
        wholesaleOrder.setFixFee(BigDecimal.ZERO);
        wholesaleOrder.setPayState(1);
        wholesaleOrder.setRefundState(0);
        wholesaleOrder.setShipState(0);
        //订单类型  0=普通订单，1=excel导入 2:补发订单
        wholesaleOrder.setOrderType(2);
        wholesaleOrder.setCreateTime(new Date());
        wholesaleOrder.setUpdateTime(new Date());
        //默认待审核
        wholesaleOrder.setOrderStatus(1);//审核状态  正常 = 0,待审查 = 1,作废 = 2

        //补发次数
        int reshipTimes = wholesaleOrderDao.reshipOrderTimesByOriginal(request.getOriginalOrderId());
        WholesaleReshipInfo orderReshipInfo = new WholesaleReshipInfo();
        orderReshipInfo.setOrderId(orderId);
        orderReshipInfo.setOriginalOrderId(request.getOriginalOrderId());
        orderReshipInfo.setReason(request.getReason());
        orderReshipInfo.setReshipTimes(reshipTimes + 1);

        //新增订单项
        BigDecimal totalWeight = BigDecimal.ZERO;
        for (WholesaleOrderItem orderItem : orderItems) {
            Long id = IdGenerate.nextId();
            orderItem.setId(id);
            orderItem.setOrderId(orderId);
            //补发订单 item
            BigDecimal w = weightMap.get(orderItem.getAdminVariantId());
            totalWeight = totalWeight.add(w.multiply(new BigDecimal(orderItem.getQuantity())));
        }
        wholesaleOrder.setTotalWeight(totalWeight);
        wholesaleOrderAddress.setId(IdGenerate.nextId());
        wholesaleOrderAddress.setOrderId(orderId);

        wholesaleOrderItemDao.insertByBatch(orderItems);
        wholesaleReshipInfoDao.insert(orderReshipInfo);
        wholesaleOrderAddressDao.insert(wholesaleOrderAddress);
        wholesaleOrderDao.insert(wholesaleOrder);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);

    }

    /**
     * 批发订单补发启用
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public WholesaleOrderUpdateResponse confirmPendingOrder(UpdatePendingOrderRequest request, Session session) {
        String userCode = String.valueOf(session.getId());
        //正常 = 0,待审查 = 1,作废 = 2
        int res = wholesaleOrderDao.updatePendingOrderStatus(request.getIds(), 0);
        return new WholesaleOrderUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 批发订单补发作废
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public WholesaleOrderUpdateResponse cancelPendingOrder(UpdatePendingOrderRequest request, Session session) {
        String userCode = String.valueOf(session.getId());
        //正常 = 0,待审查 = 1,作废 = 2
        int res = wholesaleOrderDao.updatePendingOrderStatus(request.getIds(), 2);
        if (res > 0) {
            wholesaleReshipInfoDao.updateReshipTimes(request.getIds(), 0);
        }
        return new WholesaleOrderUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 批发订单补发审核列表
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    public WholesaleOrderListResponse pendingList(WholesaleOrderListRequest request, Session session) {
        if (request.getT() == null) {
            request.setT(new WholesaleOrder());
        }
        WholesaleOrder o = request.getT();
   /*     o.setPayState(1);
        o.setRefundState(0);*/
        //订单类型
        o.setOrderType(2);
        //  正常 = 0,待审查 = 1,作废 = 2
        o.setOrderStatus(1);
        List<WholesaleOrder> results = this.select(request);
        Long total = this.count(request);
        request.setTotal(total);

        List<WholesaleOrderVo> orderListVos;
        try {
            orderListVos = loadWholesaleOrderInfo(results);
        } catch (Exception e) {
            e.printStackTrace();
            return new WholesaleOrderListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        List<WholesalePendingVo> wholesalePendingVos = new ArrayList<>();
        orderListVos.forEach(order -> {
            WholesalePendingVo orderPendingVo = new WholesalePendingVo();
            BeanUtils.copyProperties(order, orderPendingVo);
            WholesaleReshipInfo orderReshipInfo = wholesaleReshipInfoDao.selectByPrimaryKey(orderPendingVo.getId());
            orderPendingVo.setOriginalOrderId(orderReshipInfo.getOriginalOrderId());
            orderPendingVo.setReason(orderReshipInfo.getReason());
            orderPendingVo.setReshipTimes(orderReshipInfo.getReshipTimes());
            wholesalePendingVos.add(orderPendingVo);
        });
        return new WholesaleOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, wholesalePendingVos, request);
    }

    /**
     * 导入订单到赛盒
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public boolean importOrderToSaihe(Long id) {
        /**
         * 获取 已支付，未发货，订单状态正常，未退款
         * 并且没有赛盒orderCode的赛盒订单信息
         */
        SaiheOrder saiheOrder = wholesaleOrderDao.querySaiheOrder(id);
        if (saiheOrder == null){
            return false;
        }
        /**
         * 该订单是否在赛盒已经上传了，不用再回传
         * orderCode在数据库中未有，保存信息
         */
        Boolean isUpload = orderCommonService.checkAndSaveOrderCodeFromSaihe(saiheOrder.getClientOrderCode(), OrderType.WHOLESALE);
        if (isUpload) {
            return true;
        }
        /**
         * 查询该订单的订单产品
         */
        List<SaiheOrderItem> orderItemList = wholesaleOrderItemDao.listSaiheOrderItemByOrderId(id);
        saiheOrder.setOrderItemList(orderItemList);
//        Boolean bealUpdate = orderCommonService.importOrderToSaihe(saiheOrder);
//        if (!bealUpdate){
//            return false;
//        }
        /**
         * 上传订单信息实体封装 并且上传赛盒
         */
        return orderCommonService.upLoadOrderToSaiHe(saiheOrder, OrderType.WHOLESALE);
    }


    /**
     * 获取赛盒物流信息 标记订单已发货
     *
     * @param id
     * @return
     */
    @Override
 /*   public boolean getTrackingFromSaihe(Long id) {
        WholesaleOrder a=wholesaleOrderDao.selectByPrimaryKey(id);
        if(a==null||a.getSaiheOrderCode()==null||a.getShipMethodId()==null){
            return false;
        }
        if(a.getPayState()==null||a.getPayState()!=1){
            return false;
        }
        if(a.getShipPrice()==null||a.getShipState()!=0){
            return false;
        }
        //从赛盒获取订单号
        ApiGetOrderResponse apiGetOrderResponse=  SaiheService.getOrderByCode(a.getSaiheOrderCode());
        if(apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")){
            List<ApiOrderInfo> l=apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
            if(l!=null&&l.size()>0){
                String trackNumbers=l.get(0).getTrackNumbers();
                String orderCode=l.get(0).getOrderCode();
                String logisticsOrderNo=l.get(0).getLogisticsOrderNo();
                Integer transportId=l.get(0).getTransportID();//获取运输id
                //logger.debug("orderCode:{},trackNumbers:{},logisticsOrderNo:{}",orderCode,trackNumbers,logisticsOrderNo);
                Long shippingMethodId = a.getShipMethodId();
                ShippingMethodVo shippingMethod=null;
                //优先根据赛盒运输id查询Admin运输方式
                ShippingMethodVo paramVo=new ShippingMethodVo();
                paramVo.setId(shippingMethodId);
                paramVo.setSaiheTransportId(transportId);
                BaseResponse response=tmsFeignClient.getShippingMethodByTransportId(paramVo);
                if(response.getCode()==ResultCode.SUCCESS_CODE&&response.getData()!=null){
                    shippingMethod= (ShippingMethodVo) response.getData();
                }
                //获取admin的运输方式
                String shippingMethodName = "";
                if(shippingMethod!=null&&shippingMethod.getTrackType()!=null
                        &&!StringUtils.isBlank(orderCode)&&orderCode.equals(a.getSaiheOrderCode())){
                    //有发货信息
                    if(!StringUtils.isBlank(logisticsOrderNo)||!StringUtils.isBlank(trackNumbers)) {

                        shippingMethodName = shippingMethod.getName();
                        WholesaleTracking wholesaleTracking=null;
                        //回传运输商单号
                        wholesaleTracking = new WholesaleTracking();
                        wholesaleTracking.setOrderId(a.getId());
                        if(shippingMethod.getTrackType()==1&&!StringUtils.isBlank(logisticsOrderNo)){

                            wholesaleTracking.setTrackingCode(logisticsOrderNo);
                            wholesaleTracking.setTrackType(1);
                            wholesaleTracking.setUpdateTime(new Date());

                            wholesaleTracking.setShippingMethodName(shippingMethodName);
                            wholesaleTracking.setTransportId(transportId);

                        }
                        //回传真实追踪号
                        if(shippingMethod.getTrackType()==0&&!StringUtils.isBlank(trackNumbers)) {
                            wholesaleTracking.setTrackingCode(trackNumbers);
                            wholesaleTracking.setTrackType(0);
                            wholesaleTracking.setUpdateTime(new Date());
                            wholesaleTracking.setShippingMethodName(shippingMethodName);
                            wholesaleTracking.setTransportId(transportId);
                        }

                        if(wholesaleTracking!=null) {
                            wholesaleTracking.setCreateTime(new Date());
                            //标记订单为发货
                            wholesaleOrderDao.updateOrderAsTracked(id);
                            wholesaleTrackingDao.insert(wholesaleTracking);
                            //处于待回传状态，继续更新运输信息
                            return true;
                        }
                    }

                }

            }
        }
        return false;
    }*/
    public boolean getTrackingFromSaihe(Long id) {
        return orderCommonService.getTrackingFromSaihe(id, OrderType.WHOLESALE);
    }


    @Override
    public List<WholesaleOrder> selectWholesaleOrderByPaymentId(Long paymentId) {
        return wholesaleOrderDao.selectWholesaleOrderByPaymentId(paymentId);
    }

    @Override
    public BaseResponse getWholeSaleOrderAmountByManagerCodeSet(AllOrderAmountVo allOrderAmountVo) {
        BigDecimal bigDecimal = wholesaleOrderDao.getWholeSaleOrderAmountByManagerCodeSet(allOrderAmountVo);
        return BaseResponse.success(bigDecimal);
    }

    @Override
    public BaseResponse getWholeSaleOrderCount(AllOrderAmountVo allOrderAmountVo) {

        Set<String> wholeSaleOrderCount = wholesaleOrderDao.getWholeSaleOrderCount(allOrderAmountVo);
        return BaseResponse.success(wholeSaleOrderCount);

    }

    @Override
    public int updateOrderShipMethod(Long id) {
        return wholesaleOrderDao.updateOrderShipMethod(id);
    }

    @Override
    public List<WholesaleOrder> selectPage(Page<WholesaleOrder> page) {
        page.initFromNum();
        return wholesaleOrderDao.selectPage(page);
    }

    @Override
    public void delOrderShipInfoByProductId(Long productId) {
        wholesaleOrderDao.delOrderShipInfoByProductId(productId);
    }

    @Override
    public void delOrderShipInfoByVariantId(Long variantId) {
        wholesaleOrderDao.delOrderShipInfoByVariantId(variantId);
    }

    @Override
    public void matchingShipInfoByProductId(List<WholesaleOrderItem> list) {
        for (WholesaleOrderItem wholesaleOrderItem : list) {
            orderInitShipDetail(wholesaleOrderItem.getOrderId());
        }
    }

    @Override
    public ShipDetail orderInitShipDetail(Long orderId) {
        WholesaleOrder order = wholesaleOrderDao.selectByPrimaryKey(orderId);
        if (null == order
                || order.getPayState() != 0
                || order.getOrderType() == 1) {
            return null;
        }

        List<ShipDetail> shipDetails = orderShipMethods(orderId, order.getToAreaId());
        if (CollectionUtils.isEmpty(shipDetails)) {
            return null;
        }
        wholesaleOrderDao.updateShipDetailById(shipDetails.get(0),orderId);
//        orderShippingUnitService.updateOrderShipUnit(orderId,shipDetails.get(0).getShippingUtilId());
        // 删除原来的unit  并插入新的冗余
        orderShippingUnitService.delByOrderId(orderId, OrderType.WHOLESALE);
        if (CollectionUtils.isNotEmpty(shipDetails) && shipDetails.get(0).getShippingUtilId() != null) {
            BaseResponse shippingUnitResponse = tmsFeignClient.unitInfo(shipDetails.get(0).getShippingUtilId());
            if (shippingUnitResponse.getCode() == ResultCode.SUCCESS_CODE && shippingUnitResponse.getData() != null) {
                ShippingUnit shippingUnit = JSON.parseObject(JSON.toJSONString(shippingUnitResponse.getData()), ShippingUnit.class);
                OrderShippingUnit orderShippingUnit = new OrderShippingUnit();
                BeanUtils.copyProperties(shippingUnit, orderShippingUnit);
                orderShippingUnit.setOrderId(orderId);
                orderShippingUnit.setOrderType(OrderType.WHOLESALE);
                orderShippingUnit.setId(IdGenerate.nextId());
                orderShippingUnit.setShipUnitId(shippingUnit.getId());
                orderShippingUnit.setCreateTime(new Date());
                orderShippingUnitService.insert(orderShippingUnit);
            }
        }
        return shipDetails.get(0);
    }

    @Override
    public List<PaymentDetail> selectUploadSaiheAndUms(int wholesale) {

        return wholesaleOrderDao.selectUploadSaiheAndUmsBywholesale();
    }

    @Override
    public PaymentDetail selectUploadSaiheAndUmsOne(Long paymentId) {

        return wholesaleOrderDao.selectUploadSaiheAndUmsOne(paymentId);
    }

    @Override
    public void matchingShipInfoByVariantId(List<WholesaleOrderItem> list) {
        for (WholesaleOrderItem wholesaleOrderItem : list) {
            orderInitShipDetail(wholesaleOrderItem.getOrderId());
        }
    }

}