package com.upedge.oms.modules.pack.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.order.OrderItemQuantityDto;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.AliYunOssService;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.service.OrderAddressService;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderPayService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.pack.dao.OrderPackageDao;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.request.OrderPackRevokeRequest;
import com.upedge.oms.modules.pack.request.OrderPackageInfoGetRequest;
import com.upedge.oms.modules.pack.request.PackagePreUploadStoreRequest;
import com.upedge.oms.modules.pack.service.OrderLabelPrintLogService;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;
import com.upedge.oms.modules.pick.service.OrderPickService;
import com.upedge.thirdparty.shipcompany.cne.api.CneApi;
import com.upedge.thirdparty.shipcompany.cne.dto.CneCreateOrderRequest;
import com.upedge.thirdparty.shipcompany.cne.dto.CneOrderDto;
import com.upedge.thirdparty.shipcompany.fpx.api.FpxOrderApi;
import com.upedge.thirdparty.shipcompany.fpx.dto.FpxOrderCreateDto;
import com.upedge.thirdparty.shipcompany.fpx.dto.FpxOrderCreateDto.ParcelListDTO;
import com.upedge.thirdparty.shipcompany.fpx.dto.FpxOrderCreateDto.RecipientInfoDTO;
import com.upedge.thirdparty.shipcompany.fpx.dto.FpxOrderDto;
import com.upedge.thirdparty.shipcompany.fpx.request.OrderPackageGetLabelRequest;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxCreateOrderSuccessVo;
import com.upedge.thirdparty.shipcompany.yanwen.api.YanwenApi;
import com.upedge.thirdparty.shipcompany.yanwen.dto.YanwenCreateExpressResponse;
import com.upedge.thirdparty.shipcompany.yanwen.dto.YanwenExpressDto;
import com.upedge.thirdparty.shipcompany.yunexpress.api.YunexpressApi;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.WayBillCreateDto;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.YunExpressGetTrackNumDto;
import com.upedge.thirdparty.shipcompany.yunexpress.request.WayBillCreateRequest;
import com.upedge.thirdparty.shipcompany.yunexpress.response.WayBillCreateResponse;
import com.upedge.thirdparty.shipcompany.yunexpress.vo.WayBillItemVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

@Service
public class OrderPackageServiceImpl implements OrderPackageService {

    @Autowired
    OrderPackageDao orderPackageDao;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderAddressService orderAddressService;

    @Autowired
    OrderPickService orderPickService;

    @Autowired
    OrderLabelPrintLogService orderLabelPrintLogService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    OrderFulfillmentService orderFulfillmentService;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    OrderPayService orderPayService;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Value("${files.pdf.local}")
    private String pdfLocalPath;
    @Value("${files.pdf.prefix}")
    private String pdfUrlPrefix;

    @Override
    public void saveAllLabelUrl() {
        Page<OrderPackage> page = new Page<>();
        page.setPageSize(-1);
        List<OrderPackage> orderPackages = select(page);
        for (OrderPackage aPackage : orderPackages) {
            try {
                savePrintLabel(aPackage);
            } catch (CustomerException e) {

            }
        }
    }

    @Override
    public BaseResponse preUploadStore(PackagePreUploadStoreRequest request, Session session) {
        List<Long> orderIds = request.getOrderIds();
        List<OrderPackage> orderPackages = orderPackageDao.selectByOrderIds(orderIds);
        for (OrderPackage orderPackage : orderPackages) {
            orderFulfillmentService.orderFulfillment(orderPackage,true);
        }
        return BaseResponse.success();
    }

    @Override
    public List<OrderPackage> selectUploadStoreFailedPackages() {
        return orderPackageDao.selectUploadStoreFailedPackages();
    }

    @Override
    public List<OrderPackage> selectByOrderIds(List<Long> orderIds) {

        if (ListUtils.isEmpty(orderIds)) {
            return new ArrayList<>();
        }
        return orderPackageDao.selectByOrderIds(orderIds);
    }

    @Override
    public void packageRefreshTrackCode(OrderPackage orderPackage) {
        if (StringUtils.isNotBlank(orderPackage.getTrackingCode())) {
            return;
        }
        Long packNo = orderPackage.getId();
        String trackCode = orderPackage.getTrackingCode();
        if (StringUtils.isBlank(trackCode)){
            switch (orderPackage.getTrackingCompany()) {
                case "CNE":
                    trackCode = CneApi.getTrackNumber(orderPackage.getLogisticsOrderNo());
                    break;
                case "YunExpress":
                    YunExpressGetTrackNumDto yunExpressGetTrackNumDto = YunexpressApi.getTrackNum(orderPackage.getOrderId());
                    trackCode = yunExpressGetTrackNumDto.getTrackingNumber();
                    break;
                case "4PX":
                    try {
                        FpxOrderDto fpxOrderDto = FpxOrderApi.getFpxOrder(orderPackage.getLogisticsOrderNo());
                        trackCode = fpxOrderDto.getFpxTrackingNo();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Yanwen":
                    break;
                default:
                    break;
            }
        }

        if (StringUtils.isNotBlank(trackCode)) {
            orderPackage = new OrderPackage();
            orderPackage.setId(packNo);
            orderPackage.setTrackingCode(trackCode);
            updateByPrimaryKeySelective(orderPackage);
        }
        if (orderPackage.getPackageState() == 1 && !orderPackage.getIsUploadStore()) {
            sendPackageFulfillmentMessage(packNo);
        }
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse packageExStock(String scanNo, Session session) {
        OrderPackage orderPackage = orderPackageDao.selectByScanNo(scanNo);
        if (null == orderPackage){
            return BaseResponse.failed("包裹不存在");
        }
        if (orderPackage.getPackageState() != 0) {
            return BaseResponse.failed("包裹已出库或已搁置");
        }
        boolean isUploadStore = orderPackage.getIsUploadStore();
        String trackCode = orderPackage.getLogisticsOrderNo();

        Long packNo = orderPackage.getPackageNo();
        String key = "order:package:ex:" + packNo;
        boolean b = RedisUtil.lock(redisTemplate, key, 10L, 60 * 1000L);
        if (!b) {
            return BaseResponse.failed();
        }

        Long orderId = orderPackage.getOrderId();
        OrderItemQuantityVo orderItemQuantityVo = orderService.selectOrderItemQuantitiesByOrderId(orderId);
        if (null == orderItemQuantityVo) {
            RedisUtil.unLock(redisTemplate, key);
            return BaseResponse.failed("订单异常");
        }

        BaseResponse response = pmsFeignClient.packageEx(orderItemQuantityVo);
        if (response.getCode() != ResultCode.SUCCESS_CODE) {
            RedisUtil.unLock(redisTemplate, key);
            return response;
        }


        OrderPackageInfoVo orderPackageInfoVo = new OrderPackageInfoVo();
        BeanUtils.copyProperties(orderPackage, orderPackageInfoVo);

        orderPackage = new OrderPackage();
        orderPackage.setId(packNo);
        orderPackage.setPackageState(1);
        orderPackage.setSendTime(new Date());
        updateByPrimaryKeySelective(orderPackage);

        AppOrderVo appOrderVo = orderService.appOrderDetail(orderId);
        orderPackageInfoVo.setOrderVo(appOrderVo);
        orderPackageInfoVo.setPackageState(1);
        orderPackageInfoVo.setSendTime(new Date());
        RedisUtil.unLock(redisTemplate, key);
        //物流已上传店铺，直接修改订单发货状态
        if(isUploadStore){
            orderService.updateOrderAsTracked(orderId,trackCode);
        }else {
            sendPackageFulfillmentMessage(packNo);
        }

        return BaseResponse.success(orderPackageInfoVo);
    }

    @Override
    public BaseResponse orderRevokePackage(OrderPackRevokeRequest request, Session session) {
        List<Long> orderIds = request.getOrderIds();
        orderIds.forEach(orderId -> {
            revokePackage(orderId,request.getReason());
        });
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public void revokePackage(Long orderId, String reason){

        if (orderId == null){
            return;
        }
        Order order = orderService.selectByPrimaryKey(orderId);
        if (order.getStockState() == 1){
            OrderItemQuantityVo orderItemQuantityVo = orderService.selectOrderItemQuantitiesByOrderId(orderId);
            if (null == orderItemQuantityVo){
                return;
            }
            int i = pmsFeignClient.orderCancelShip(orderItemQuantityVo);
            if (i == 0){
                return;
            }
            orderService.updateStockState(orderId,0);
        }

        Long packNo = null;
        OrderPackage orderPackage = orderPackageDao.selectByOrderId(orderId);
        if (null != orderPackage) {
            if (StringUtils.isNotBlank(reason)) {
                if (StringUtils.isNotBlank(orderPackage.getRemark())) {
                    reason = orderPackage.getRemark() + "," + reason;
                }
            }
            orderPackageDao.revokePackageById(orderPackage.getId(), reason);
            packNo = orderPackage.getPackageNo();
        }

        orderService.updateOrderPackInfo(orderId, -1, packNo);
    }

    @Override
    public List<OrderLabelPrintLog> packLabelPrintLog(Long packNo) {
        return orderLabelPrintLogService.selectByPackNo(packNo);
    }

    @Override
    public BaseResponse printPackLabel(OrderPackageGetLabelRequest request, Session session) {
        String labelUrl = "";
        OrderPackage orderPackage = selectByPrimaryKey(request.getPackNo());
        if (null == orderPackage) {
            return BaseResponse.failed("包裹不存在");
        }
        Map<String, String> map = new HashMap<>();
        map.put("company", orderPackage.getTrackingCompany());

        OrderLabelPrintLog orderLabelPrintLog = orderLabelPrintLogService.selectTheLatestPackLabel(request.getPackNo());
        if (null != orderLabelPrintLog) {
            labelUrl = orderLabelPrintLog.getLabelUrl();
        }else {
            try {
                labelUrl = savePrintLabel(orderPackage);
            } catch (CustomerException e) {
                return BaseResponse.failed(e.getMessage());
            }
        }

        map.put("labelUrl", labelUrl);
        if (StringUtils.isNotBlank(labelUrl)){
            Order order = new Order();
            order.setId(orderPackage.getOrderId());
            order.setIsPrintLabel(true);
            orderService.updateByPrimaryKeySelective(order);
            return BaseResponse.success(map);
        }else {
            return BaseResponse.failed("包裹号：" + request.getPackNo() + " 获取面单失败");
        }
    }


    private String savePrintLabel(OrderPackage orderPackage) throws CustomerException {
        if (null == orderPackage){
            return null;
        }
        OrderLabelPrintLog orderLabelPrintLog = orderLabelPrintLogService.selectTheLatestPackLabel(orderPackage.getPackageNo());
        if (null != orderLabelPrintLog){
            return orderLabelPrintLog.getLabelUrl();
        }
        String labelUrl = null;
        try {
            switch (orderPackage.getTrackingCompany()) {
                case "4PX":
                    labelUrl = FpxOrderApi.getSinglePackageLabel(orderPackage.getLogisticsOrderNo());
                    labelUrl = AliYunOssService.uploadLabel(labelUrl,"4PX",orderPackage.getId() + ".pdf");
                    break;
                case "YunExpress":
                    labelUrl = YunexpressApi.getSinglePackageLabel(orderPackage.getPlatId());
                    labelUrl = AliYunOssService.uploadLabel(labelUrl,"YunExpress",orderPackage.getId() + ".pdf");
                    break;
                case "CNE":
                    labelUrl = CneApi.getLabel(orderPackage.getLogisticsOrderNo());
                    labelUrl = AliYunOssService.uploadLabel(labelUrl,"CNE",orderPackage.getId() + ".pdf");
                    break;
                case "Yanwen":
                    labelUrl = YanwenApi.getTrackLabel(orderPackage.getLogisticsOrderNo(), pdfLocalPath);
                    if (StringUtils.isNotBlank(labelUrl)) {
                        labelUrl = pdfUrlPrefix + labelUrl;
                        labelUrl = AliYunOssService.uploadLabel(labelUrl,"Yanwen",orderPackage.getId() + ".pdf");
                    }
                    break;
            }
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
        if (StringUtils.isNotBlank(labelUrl)) {
            orderLabelPrintLog = new OrderLabelPrintLog();
            orderLabelPrintLog.setOrderId(orderPackage.getOrderId());
            orderLabelPrintLog.setLabelUrl(labelUrl);
            orderLabelPrintLog.setCreateTime(new Date());
            orderLabelPrintLog.setPackNo(orderPackage.getPackageNo());
            orderLabelPrintLogService.insert(orderLabelPrintLog);
        }
        return labelUrl;
    }

    @Override
    public OrderPackageInfoVo packageInfo(OrderPackageInfoGetRequest request) {
        OrderPackage orderPackage = selectByPrimaryKey(request.getPackNo(), request.getOrderId(), request.getTrackingCode());
        if (null == orderPackage) {
            return null;
        }
        OrderPackageInfoVo orderPackageInfoVo = new OrderPackageInfoVo();
        BeanUtils.copyProperties(orderPackage, orderPackageInfoVo);
        AppOrderVo appOrderVo = orderService.appOrderDetail(orderPackage.getOrderId());
        orderPackageInfoVo.setOrderVo(appOrderVo);
        return orderPackageInfoVo;
    }

    @Override
    public BaseResponse createPackage(Long orderId) {
        Order order = orderService.selectByPrimaryKey(orderId);

        if (order.getPayState() != 1
                || order.getPackState() !=0
                || order.getRefundState() != 0) {
            return BaseResponse.failed("订单未支付或已生成包裹或退款中");
        }

        OrderPackage orderPackage = orderPackageDao.selectByOrderId(orderId);
        if (orderPackage != null){
            return BaseResponse.failed("订单已生成包裹");
        }

        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, order.getActualShipMethodId().toString());
        String shipCompany = shippingMethodRedis.getTrackingCompany();
        if (StringUtils.isBlank(shipCompany) || StringUtils.isBlank(shippingMethodRedis.getMethodCode())) {
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), "请完善运输方式公司信息");
            return BaseResponse.failed("请完善运输方式公司信息");
        }

        try {
            switch (shipCompany) {
                case "4PX":
                    return createFpxPackage(order, shippingMethodRedis);
                case "YunExpress":
                    return createYunExpressPackage(order, shippingMethodRedis);
                case "CNE":
                    return createCnePackage(order, shippingMethodRedis);
                case "Yanwen":
                    return createYanwenPackage(order, shippingMethodRedis);
                default:
                    return BaseResponse.failed("物流公司未对接");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.failed(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void reCreatePackage(Long orderId) {
        OrderPackage orderPackage = orderPackageDao.selectByOrderId(orderId);
        if (null != orderPackage){
            Order order = orderService.selectByPrimaryKey(orderId);
            if (order.getStockState() == 1){
                OrderItemQuantityVo orderItemQuantityVo = orderService.selectOrderItemQuantitiesByOrderId(orderId);
                if (null == orderItemQuantityVo){
                    return;
                }
                int i = pmsFeignClient.orderCancelShip(orderItemQuantityVo);
                if (i == 0){
                    return;
                }
                orderService.updateStockState(orderId,0);
            }

            deleteByPrimaryKey(orderPackage.getId());
        }
        createPackage(orderId);
    }

    public BaseResponse createYanwenPackage(Order order, ShippingMethodRedis shippingMethodRedis) throws CustomerException {
        Long orderId = order.getId();
        Long packNo = getPackageNo();
        order.setPackNo(packNo);
        YanwenExpressDto yanwenExpressDto = new YanwenExpressDto();

        List<OrderItem> orderItems = orderItemService.selectByOrderId(orderId);
        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        YanwenExpressDto.YanwenReceiverDTO receiverDTO = yanwenExpressDto.getReceiver();
        receiverDTO.setName(orderAddress.getFirstName() + " " + orderAddress.getLastName());
        receiverDTO.setEmail(orderAddress.getEmail());
        receiverDTO.setPostcode(orderAddress.getZip());
        receiverDTO.setAddress2(orderAddress.getAddress2());
        receiverDTO.setAddress1(orderAddress.getAddress1());
        receiverDTO.setState(orderAddress.getProvince());
        receiverDTO.setCountry(orderAddress.getCountry());
        receiverDTO.setPhone(orderAddress.getPhone());
        receiverDTO.setCity(orderAddress.getCity());

        int i = 0;
        YanwenExpressDto.YanwenGoodsNameDTO goodsName = new YanwenExpressDto.YanwenGoodsNameDTO();
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getQuantity() < 1){
                continue;
            }
            String entryCName = getProductEntryName(orderItem.getAdminProductId(), "cn");
            String entryEName = getProductEntryName(orderItem.getAdminProductId(), "en");
            if (StringUtils.isBlank(entryCName) || StringUtils.isBlank(entryEName)) {
                orderService.updateOrderPackInfo(orderId, 0, null);
                redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), "产品缺少报关信息");
                return BaseResponse.failed("产品缺少报关信息");
            }
            goodsName.setMoreGoodsName(orderItem.getBarcode());
            goodsName.setNameCh(entryCName);
            goodsName.setNameEn(entryEName);
            goodsName.setDeclaredCurrency("USD");
            goodsName.setDeclaredValue(orderItem.getQuantity() * orderItem.getUsdPrice().doubleValue());
            goodsName.setWeight(orderItem.getQuantity() * orderItem.getAdminVariantWeight().intValue());
            i += orderItem.getQuantity();
            break;
        }

        yanwenExpressDto.setGoodsName(goodsName);
        yanwenExpressDto.setSendDate(new Date());
        yanwenExpressDto.setChannel(shippingMethodRedis.getMethodCode());
        yanwenExpressDto.setUserOrderNumber(order.getPackNo().toString());
        yanwenExpressDto.setQuantity(i);

        YanwenCreateExpressResponse.CreatedExpressDTO createdExpressDTO = null;
        try {
            createdExpressDTO = YanwenApi.createExpress(yanwenExpressDto);
        } catch (CustomerException e) {
            orderService.updateOrderPackInfo(orderId, 0, null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }

        OrderPackage orderPackage = savePackage(order, shippingMethodRedis, createdExpressDTO.getEpcode(), createdExpressDTO.getReferenceNo(), createdExpressDTO.getEpcode());

        return BaseResponse.success(orderPackage);
    }

    public BaseResponse createCnePackage(Order order, ShippingMethodRedis shippingMethodRedis) throws CustomerException {
        Long orderId = order.getId();
        Long packNo = getPackageNo();
        order.setPackNo(packNo);
        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        CneCreateOrderRequest request = new CneCreateOrderRequest();
        CneCreateOrderRequest.RecListDTO recListDTO = new CneCreateOrderRequest.RecListDTO();
        recListDTO.setCEmsKind(shippingMethodRedis.getMethodCode());
        recListDTO.setCRNo(order.getPackNo().toString());
        recListDTO.setCDes(orderAddress.getCountryCode());
        recListDTO.setCReceiver(orderAddress.getFirstName() + " " + orderAddress.getLastName());
        recListDTO.setCRUnit(orderAddress.getCompany());
        recListDTO.setCRAddr(orderAddress.getAddress1() + " " + orderAddress.getAddress2());
        recListDTO.setCRCity(orderAddress.getCity());
        recListDTO.setCRProvince(orderAddress.getProvince());
        recListDTO.setCRPostcode(orderAddress.getZip());
        recListDTO.setCRCountry(orderAddress.getCountry());
        recListDTO.setCRPhone(orderAddress.getPhone());
        recListDTO.setCRSms(orderAddress.getPhone());
        recListDTO.setCREMail(orderAddress.getEmail());
        recListDTO.setFWeight(order.getTotalWeight().divide(new BigDecimal("1000")).doubleValue());

        List<OrderItem> orderItems = orderItemService.selectByOrderId(orderId);
        List<CneCreateOrderRequest.RecListDTO.CneGoodsListDTO> cneGoodsListDTOS = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getQuantity() < 1){
                continue;
            }
            String entryCName = getProductEntryName(orderItem.getAdminProductId(), "cn");
            String entryEName = getProductEntryName(orderItem.getAdminProductId(), "en");
            if (StringUtils.isBlank(entryCName) || StringUtils.isBlank(entryEName)) {
                orderService.updateOrderPackInfo(orderId, 0, null);
                redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), "产品缺少报关信息");
                return BaseResponse.failed("产品缺少报关信息");
            }
            CneCreateOrderRequest.RecListDTO.CneGoodsListDTO cneGoodsListDTO = new CneCreateOrderRequest.RecListDTO.CneGoodsListDTO();
            cneGoodsListDTO.setCxGoods(entryCName);
            cneGoodsListDTO.setCxGoodsA(entryEName);
            cneGoodsListDTO.setIxQuantity(orderItem.getQuantity());
            cneGoodsListDTO.setFxPrice(orderItem.getUsdPrice());
            cneGoodsListDTOS.add(cneGoodsListDTO);
        }
        recListDTO.setGoodsList(cneGoodsListDTOS);
        request.getRecList().add(recListDTO);
        CneOrderDto cneOrderDto = null;
        try {
            cneOrderDto = CneApi.createCneOrder(request);
        } catch (Exception e) {
            orderService.updateOrderPackInfo(orderId, 0, null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }

        OrderPackage orderPackage = savePackage(order, shippingMethodRedis, cneOrderDto.getCPNo(), cneOrderDto.getCNo(), cneOrderDto.getIID());
        return BaseResponse.success(orderPackage);

    }

    private BaseResponse createFpxPackage(Order order, ShippingMethodRedis shippingMethodRedis) {
        Long orderId = order.getId();
        FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO fpxCreateOrderDataDTO = null;
        try {
            fpxCreateOrderDataDTO = createFpxPackage(order);
        } catch (Exception e) {
            orderService.updateOrderPackInfo(orderId, 0, null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }


        OrderPackage orderPackage = savePackage(order, shippingMethodRedis, fpxCreateOrderDataDTO.getFpxTrackingNo(), fpxCreateOrderDataDTO.getLogisticsChannelNo(), fpxCreateOrderDataDTO.getLabelBarcode());
        return BaseResponse.success(orderPackage);
    }

    private BaseResponse createYunExpressPackage(Order order, ShippingMethodRedis shippingMethodRedis) throws CustomerException {
        Long orderId = order.getId();
        Long packNo = getPackageNo();
        order.setPackNo(packNo);
        WayBillCreateDto wayBillCreateDto = new WayBillCreateDto();
        WayBillCreateDto.ReceiverDTO receiverDTO = new WayBillCreateDto.ReceiverDTO();
        List<WayBillCreateDto.ParcelsDTO> parcels = new ArrayList<>();
        List<WayBillCreateDto.OrderExtraDTO> orderExtraDTOs = new ArrayList<>();
        List<WayBillCreateDto.ChildOrdersDTO> childOrdersDTOS = new ArrayList<>();

        CustomerIossVo customerIossVo = null;
        String key = RedisKey.STRING_CUSTOMER_IOSS + order.getCustomerId();
        if (redisTemplate.hasKey(key)) {
            customerIossVo = (CustomerIossVo) redisTemplate.opsForValue().get(key);
        }

        List<WayBillCreateDto.ChildOrdersDTO.ChildDetailsDTO> childDetailsDTOS = new ArrayList<>();

        List<OrderItem> orderItems = orderItemService.selectByOrderId(orderId);
        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        receiverDTO.setFirstName(orderAddress.getFirstName());
        receiverDTO.setLastName(orderAddress.getLastName());
        receiverDTO.setZip(orderAddress.getZip());
        receiverDTO.setHouseNumber(orderAddress.getAddress2());
        receiverDTO.setStreet(orderAddress.getAddress1());
        receiverDTO.setState(orderAddress.getProvince());
        receiverDTO.setCountryCode(orderAddress.getCountryCode());
        receiverDTO.setPhone(orderAddress.getPhone());
        receiverDTO.setCity(orderAddress.getCity());

        for (OrderItem orderItem : orderItems) {
            if(orderItem.getQuantity() < 1){
                continue;
            }
            String entryCName = getProductEntryName(orderItem.getAdminProductId(), "cn");
            String entryEName = getProductEntryName(orderItem.getAdminProductId(), "en");
            if (StringUtils.isBlank(entryCName) || StringUtils.isBlank(entryEName)) {
                orderService.updateOrderPackInfo(orderId, 0, null);
                redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), "产品缺少报关信息");
                return BaseResponse.failed("产品缺少报关信息");
            }
            WayBillCreateDto.ChildOrdersDTO.ChildDetailsDTO childDetailsDTO = new WayBillCreateDto.ChildOrdersDTO.ChildDetailsDTO();
            childDetailsDTO.setSku(orderItem.getAdminVariantSku());
            childDetailsDTO.setQuantity(orderItem.getQuantity());
            childDetailsDTOS.add(childDetailsDTO);
            WayBillCreateDto.ParcelsDTO parcelsDTO = new WayBillCreateDto.ParcelsDTO();
            parcelsDTO.setEName(entryEName);
            parcelsDTO.setCName(entryCName);
            parcelsDTO.setCurrencyCode("USD");
            parcelsDTO.setHSCode(orderItem.getBarcode());
            parcelsDTO.setSKU(orderItem.getStoreVariantSku());
            parcelsDTO.setQuantity(orderItem.getQuantity());
            parcelsDTO.setUnitPrice(orderItem.getUsdPrice());
            parcelsDTO.setInvoiceRemark(orderItem.getBarcode());
            parcelsDTO.setUnitWeight(orderItem.getAdminVariantWeight().divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_UP));
            parcels.add(parcelsDTO);
        }

        WayBillCreateDto.ChildOrdersDTO childOrdersDTO = new WayBillCreateDto.ChildOrdersDTO();
        childOrdersDTO.setChildDetails(childDetailsDTOS);
        childOrdersDTO.setBoxNumber(order.getPackNo().toString());
        childOrdersDTO.setBoxWeight(BigDecimal.ONE);
        childOrdersDTOS.add(childOrdersDTO);

        wayBillCreateDto.setChildOrders(childOrdersDTOS);
        wayBillCreateDto.setCustomerOrderNumber(packNo.toString());
        wayBillCreateDto.setShippingMethodCode(shippingMethodRedis.getMethodCode());
        wayBillCreateDto.setPackageCount(1);
        wayBillCreateDto.setWeight(order.getTotalWeight().divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_UP));
        if (customerIossVo != null){
            wayBillCreateDto.setIossCode(customerIossVo.getIossNum());
        }else {
            WayBillCreateDto.OrderExtraDTO extraDTO = new WayBillCreateDto.OrderExtraDTO();
            extraDTO.setExtraCode("V1");
            extraDTO.setExtraName("云涂预缴");
            orderExtraDTOs.add(extraDTO);
        }
        wayBillCreateDto.setReturnOption(0);
        wayBillCreateDto.setTariffPrepay(0);
        wayBillCreateDto.setInsuranceOption(0);
        wayBillCreateDto.setSourceCode("API");


        wayBillCreateDto.setReceiver(receiverDTO);
        wayBillCreateDto.setParcels(parcels);
        wayBillCreateDto.setOrderExtra(orderExtraDTOs);

        WayBillCreateRequest wayBillCreateRequest = new WayBillCreateRequest();
        wayBillCreateRequest.getWayBillCreateDtos().add(wayBillCreateDto);
        WayBillItemVo wayBillItemVo = null;
        WayBillCreateResponse wayBillCreateResponse = null;
        try {
            wayBillCreateResponse = YunexpressApi.createWayBill(wayBillCreateRequest);
            wayBillItemVo = wayBillCreateResponse.getItemVos().get(0);

            if (wayBillItemVo.getSuccess() != 1) {
                orderService.updateOrderPackInfo(orderId, 0, null);
                redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), wayBillItemVo.getRemark());
                return BaseResponse.failed(wayBillItemVo.getRemark());
            }
        } catch (Exception e) {
            orderService.updateOrderPackInfo(orderId, 0, null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString(), e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }

        OrderPackage orderPackage = savePackage(order, shippingMethodRedis, wayBillItemVo.getWayBillNumber(), wayBillItemVo.getTrackingNumber(), wayBillItemVo.getWayBillNumber());

        return BaseResponse.success(orderPackage);
    }

    public FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO createFpxPackage(Order order) throws CustomerException {
        Long orderId = order.getId();
        Long packNo = getPackageNo();
        order.setPackNo(packNo);
        List<OrderItem> orderItems = orderItemService.selectByOrderId(orderId);

        UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO, order.getCustomerId().toString());

        CustomerIossVo customerIossVo = null;
        String key = RedisKey.STRING_CUSTOMER_IOSS + order.getCustomerId();
        if (redisTemplate.hasKey(key)) {
            customerIossVo = (CustomerIossVo) redisTemplate.opsForValue().get(key);
        }

        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, order.getShipMethodId().toString());

        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        FpxOrderCreateDto fpxOrderCreateDto = new FpxOrderCreateDto();
        fpxOrderCreateDto.setRefNo(order.getPackNo().toString());
        fpxOrderCreateDto.setBuyerId(userVo.getUsername());
        if (customerIossVo != null) {
            fpxOrderCreateDto.setVatNo(customerIossVo.getIossNum());
        }

        List<ParcelListDTO> parcelListDTOS = new ArrayList<>();

        List<ParcelListDTO.DeclareProductInfoDTO> declareProductInfoDTOS = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if(orderItem.getQuantity() < 1){
                continue;
            }
            String entryCName = getProductEntryName(orderItem.getAdminProductId(), "cn");
            String entryEName = getProductEntryName(orderItem.getAdminProductId(), "en");
            if (StringUtils.isBlank(entryCName) || StringUtils.isBlank(entryEName)) {
                throw new CustomerException("产品缺少报关信息");
            }
            ParcelListDTO.DeclareProductInfoDTO declareProductInfoDTO = new ParcelListDTO.DeclareProductInfoDTO();
            declareProductInfoDTO.setDeclareProductNameEn(entryEName);
            declareProductInfoDTO.setDeclareProductNameCn(entryCName);
            declareProductInfoDTO.setDeclareProductCode(orderItem.getBarcode());
            declareProductInfoDTO.setDeclareProductCodeQty(orderItem.getQuantity());
            declareProductInfoDTO.setDeclareUnitPriceExport(orderItem.getUsdPrice());
            declareProductInfoDTO.setDeclareUnitPriceImport(orderItem.getUsdPrice());
            declareProductInfoDTOS.add(declareProductInfoDTO);
        }

        ParcelListDTO parcelListDTO = new ParcelListDTO();
        parcelListDTO.setWeight(order.getTotalWeight().intValue());
        parcelListDTO.setParcelValue(order.getProductAmount().doubleValue());
        parcelListDTO.setCurrency("USD");
        parcelListDTOS.add(parcelListDTO);
        parcelListDTO.setDeclareProductInfo(declareProductInfoDTOS);
        fpxOrderCreateDto.setParcelList(parcelListDTOS);

        RecipientInfoDTO recipientInfo = fpxOrderCreateDto.getRecipientInfo();
        BeanUtils.copyProperties(orderAddress, recipientInfo);
        recipientInfo.setFirstName(orderAddress.getFirstName());
        recipientInfo.setLastName(orderAddress.getLastName());
        recipientInfo.setPostCode(orderAddress.getZip());
        recipientInfo.setDistrict(orderAddress.getAddress2());
        recipientInfo.setStreet(orderAddress.getAddress1());
        recipientInfo.setState(orderAddress.getProvince());
        recipientInfo.setCountry(orderAddress.getCountryCode());
        recipientInfo.setPhone(orderAddress.getPhone());
        recipientInfo.setPhone2(orderAddress.getPhone());

        fpxOrderCreateDto.getLogisticsServiceInfo().setLogisticsProductCode(shippingMethodRedis.getMethodCode());

        try {
            FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO fpxCreateOrderDataDTO = FpxOrderApi.createFpxOrder(fpxOrderCreateDto);
            return fpxCreateOrderDataDTO;
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }


    }


    public OrderPackage savePackage(Order order, ShippingMethodRedis shippingMethodRedis, String logisticsOrderNo, String trackCode, String platId) {
        Long orderId = order.getId();
        Long packageNo = order.getPackNo();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setId(packageNo);
        orderPackage.setPackageNo(packageNo);
        orderPackage.setOrderId(orderId);
        orderPackage.setPackageState(0);
        orderPackage.setCreateTime(new Date());
        orderPackage.setLogisticsOrderNo(logisticsOrderNo);
        orderPackage.setTrackingCode(trackCode);
        orderPackage.setPlatId(platId);
        orderPackage.setStoreId(order.getStoreId());
        orderPackage.setCustomerId(order.getCustomerId());
        orderPackage.setTrackingMethodName(shippingMethodRedis.getName());
        orderPackage.setTrackingMethodCode(shippingMethodRedis.getMethodCode());
        orderPackage.setTrackingCompany(shippingMethodRedis.getTrackingCompany());
        insert(orderPackage);
        orderService.updateOrderPackInfo(orderId, 1, packageNo);
        redisTemplate.opsForHash().delete(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON, orderId.toString());

        try {
            savePrintLabel(orderPackage);
        } catch (CustomerException e) {

        }

        OrderItemQuantityDto orderItemQuantityDto = new OrderItemQuantityDto();
        orderItemQuantityDto.setOrderId(orderId);
        orderPayService.sendCheckOrderStockMessage(orderItemQuantityDto);
        return orderPackage;
    }


    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderPackage record = new OrderPackage();
        record.setId(id);
        return orderPackageDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderPackage record) {
        return orderPackageDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderPackage record) {
        return orderPackageDao.insert(record);
    }

    /**
     *
     */
    public OrderPackage selectByPrimaryKey(Long id, Long orderId, String trackingCode) {
        if (null != id) {
            return orderPackageDao.selectByPrimaryKey(id);
        }
        if (null != orderId) {
            return orderPackageDao.selectByOrderId(orderId);
        }
        if (null != trackingCode) {
            return orderPackageDao.selectByTrackingCode(trackingCode);
        }
        return null;
    }

    public OrderPackage selectByPrimaryKey(Long id) {
        return orderPackageDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(OrderPackage record) {
        return orderPackageDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(OrderPackage record) {
        return orderPackageDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<OrderPackage> select(Page<OrderPackage> record) {
        record.initFromNum();
        return orderPackageDao.select(record);
    }

    /**
     *
     */
    public long count(Page<OrderPackage> record) {
        return orderPackageDao.count(record);
    }

    public Long getPackageNo() throws CustomerException {
        String lockKey = "order:package:no:lock";

        boolean b = RedisUtil.lock(redisTemplate, lockKey, 9L, 10L);
        if (!b) {
            throw new CustomerException("获取包裹号失败，请重试");
        }
        String key = "order:package:no";
        String date = DateUtils.getDate("yyyyMMdd");
        b = redisTemplate.opsForHash().hasKey(key, date);
        Long no = null;
        if (!b) {
            no = Long.parseLong(date + "00001");
        } else {
            no = (Long) redisTemplate.opsForHash().get(key, date);
            if (null != no) {
                no = no + 1;
            } else {
                no = Long.parseLong(date + "00001");
            }
        }
        redisTemplate.opsForHash().put(key, date, no);
        RedisUtil.unLock(redisTemplate, lockKey);
        return no;
    }

    private String getProductEntryName(Long productId, String entryType) {
        String entryName = (String) redisTemplate.opsForHash().get(RedisKey.HASH_PRODUCT_CUSTOMS_INFO, productId + ":" + entryType);
        if (StringUtils.isBlank(entryName)) {
            pmsFeignClient.customsInfo(productId);
            entryName = (String) redisTemplate.opsForHash().get(RedisKey.HASH_PRODUCT_CUSTOMS_INFO, productId + ":" + entryType);
        }
        return entryName;
    }

    private void sendPackageFulfillmentMessage(Long packageNo) {
        Message message = new Message(RocketMqConfig.TOPIC_ORDER_PACKAGE_EX_FULFILLMENT, "", packageNo.toString(), JSON.toJSONBytes(packageNo));
        try {
            defaultMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String uploadPackageLabelPdf(String labelUrl,Long packageNo){
        if (StringUtils.isBlank(labelUrl)){
            return null;
        }
        try {
            String fileName = packageNo + ".pdf";
            URL url = new URL(labelUrl);
            String localPath = pdfLocalPath + fileName;
            FileUtils.copyURLToFile(url, new File(localPath));
            return pdfUrlPrefix + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return labelUrl;
    }


}
