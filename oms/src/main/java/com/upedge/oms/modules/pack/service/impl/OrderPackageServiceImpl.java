package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.CustomerIossVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.service.OrderAddressService;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.pack.dao.OrderPackageDao;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.request.OrderPackRevokeRequest;
import com.upedge.oms.modules.pack.request.OrderPackageInfoGetRequest;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Value("${files.pdf.local}")
    private String pdfLocalPath;
    @Value("${files.pdf.prefix}")
    private String pdfUrlPrefix;

    @Override
    public void packageRefreshTrackCode(OrderPackage orderPackage) {
        if (StringUtils.isNotBlank(orderPackage.getTrackingCode())){
            return;
        }
        Long packNo = orderPackage.getId();
        String trackCode = null;
        switch (orderPackage.getTrackingCompany()){
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
        if (StringUtils.isNotBlank(trackCode)){
            orderPackage = new OrderPackage();
            orderPackage.setId(packNo);
            orderPackage.setTrackingCode(trackCode);
            updateByPrimaryKeySelective(orderPackage);
        }
        if (!orderPackage.getIsUploadStore()){
            try {
                packageExStock(packNo,null);
            } catch (CustomerException e) {
                e.printStackTrace();
            }
        }
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse packageExStock(Long packNo, Session session) throws CustomerException {
        OrderPackage orderPackage = selectByPrimaryKey(packNo);
        if (orderPackage.getPackageState() != 0){
            return BaseResponse.failed("包裹异常");
        }
        Long orderId = orderPackage.getOrderId();
        OrderItemQuantityVo orderItemQuantityVo=  orderService.selectOrderItemQuantitiesByOrderId(orderId);
        if (null == orderItemQuantityVo){
            return BaseResponse.failed("订单异常");
        }

        BaseResponse response = pmsFeignClient.packageEx(orderItemQuantityVo);
        if (response.getCode() != ResultCode.SUCCESS_CODE){
            return response;
        }

        orderPackage = new OrderPackage();
        orderPackage.setId(orderPackage.getId());
        orderPackage.setPackageState(1);
        orderPackage.setSendTime(new Date());
        updateByPrimaryKeySelective(orderPackage);

        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse orderRevokePackage(OrderPackRevokeRequest request, Session session) {
        OrderPackage orderPackage = selectByPrimaryKey(request.getPackNo(), request.getOrderId(), request.getTrackingCode());
        if (null == orderPackage){
            return BaseResponse.failed();
        }
        String result = "";
        switch (orderPackage.getTrackingCompany()){
            case "4PX":
                result = FpxOrderApi.cancelPack(orderPackage.getOrderId());
                break;
            case "YunExpress":
                result = YunexpressApi.cancelYunExpressPack(orderPackage.getOrderId());
                break;
            default:
                break;
        }
        if (!result.equals("success")){
            return BaseResponse.failed(result);
        }
        String reason = request.getReason();
        if (StringUtils.isNotBlank(reason)){
            if (StringUtils.isNotBlank(orderPackage.getRemark())){
                reason = orderPackage.getRemark() + "," + reason;
            }
        }

        orderService.updateOrderPackInfo(orderPackage.getOrderId(),-1, orderPackage.getPackageNo());

        orderPackageDao.revokePackageById(orderPackage.getId(),reason);
        return BaseResponse.success();
    }

    @Override
    public List<OrderLabelPrintLog> packLabelPrintLog(Long packNo) {
        return orderLabelPrintLogService.selectByPackNo(packNo);
    }

    @Override
    public BaseResponse printPackLabel(OrderPackageGetLabelRequest request, Session session) {
        String labelUrl = "";
        OrderPackage orderPackage = selectByPrimaryKey(request.getPackNo());
        if (null == orderPackage){
            return BaseResponse.failed("包裹不存在");
        }
        Map<String,String> map = new HashMap<>();
        map.put("company",orderPackage.getTrackingCompany());
        OrderLabelPrintLog orderLabelPrintLog = orderLabelPrintLogService.selectTheLatestPackLabel(request.getPackNo());
        if (null != orderLabelPrintLog){
            map.put("labelUrl",orderLabelPrintLog.getLabelUrl());
            return BaseResponse.success(map);
        }
        try {
            switch (orderPackage.getTrackingCompany()){
                case "4PX":
                    labelUrl = FpxOrderApi.getSinglePackageLabel(orderPackage.getPlatId());
                    break;
                case "YunExpress":
                    labelUrl = YunexpressApi.getSinglePackageLabel(orderPackage.getPlatId());
                    break;
                case "CNE":
                    labelUrl = CneApi.getLabel(orderPackage.getLogisticsOrderNo());
                    break;
                case "Yanwen":
                    labelUrl = YanwenApi.getTrackLabel(orderPackage.getLogisticsOrderNo(),pdfLocalPath);
                    if (StringUtils.isNotBlank(labelUrl)){
                        labelUrl = pdfUrlPrefix + labelUrl;
                    }
                    break;
            }
        }catch (Exception e){

        }

        map.put("labelUrl",labelUrl);

        orderLabelPrintLog = new OrderLabelPrintLog();
        orderLabelPrintLog.setOrderId(orderPackage.getOrderId());
        orderLabelPrintLog.setLabelUrl(labelUrl);
        orderLabelPrintLog.setCreateTime(new Date());
        orderLabelPrintLog.setPackNo(orderPackage.getPackageNo());
        orderLabelPrintLog.setOperatorId(session.getId());
        orderLabelPrintLogService.insert(orderLabelPrintLog);

        Order order = new Order();
        order.setId(orderPackage.getId());
        order.setIsPrintLabel(true);
        orderService.updateByPrimaryKeySelective(order);

        return BaseResponse.success(map);
    }

    @Override
    public OrderPackageInfoVo packageInfo(OrderPackageInfoGetRequest request) {
        OrderPackage orderPackage = selectByPrimaryKey(request.getPackNo(), request.getOrderId(), request.getTrackingCode());
        if (null == orderPackage){
            return null;
        }
        OrderPackageInfoVo orderPackageInfoVo = new OrderPackageInfoVo();
        BeanUtils.copyProperties(orderPackage,orderPackageInfoVo);
        AppOrderVo appOrderVo = orderService.appOrderDetail(orderPackage.getOrderId());
        orderPackageInfoVo.setOrderVo(appOrderVo);
        return orderPackageInfoVo;
    }

    @Override
    public BaseResponse createPackage(Long orderId) {
        Order order = orderService.selectByPrimaryKey(orderId);

        if (order.getPayState() != 1
        || order.getPackState() == 1
        || order.getRefundState() != 0){
            return BaseResponse.failed("订单未支付或已生成包裹或退款中");
        }

        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,order.getShipMethodId().toString());
        String shipCompany = shippingMethodRedis.getTrackingCompany();
        if (StringUtils.isBlank(shipCompany) || StringUtils.isBlank(shippingMethodRedis.getMethodCode())){
            return BaseResponse.failed("请完善运输方式公司信息");
        }

        switch (shipCompany){
            case "4PX":
                 return createFpxPackage(order,shippingMethodRedis);
            case "YunExpress":
                 return createYunExpressPackage(order,shippingMethodRedis);
            case "CNE":
                return createCnePackage(order,shippingMethodRedis);
            case "Yanwen":
                return createYanwenPackage(order,shippingMethodRedis);
            default:
                return BaseResponse.failed("物流公司未对接");
        }
    }

    public BaseResponse createYanwenPackage(Order order,ShippingMethodRedis shippingMethodRedis){
        Long orderId = order.getId();

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
        receiverDTO.setPhone("8956232659");
        receiverDTO.setCity(orderAddress.getCity());

        int i= 0;
        YanwenExpressDto.YanwenGoodsNameDTO goodsName = new YanwenExpressDto.YanwenGoodsNameDTO();
        for (OrderItem orderItem : orderItems) {
            goodsName.setNameCh("宠物服装");
            goodsName.setNameEn("pet clothes");
            goodsName.setDeclaredCurrency("USD");
            goodsName.setDeclaredValue(orderItem.getQuantity() * orderItem.getUsdPrice().doubleValue());
            goodsName.setWeight(orderItem.getQuantity() * orderItem.getAdminVariantWeight().intValue());
            i += orderItem.getQuantity();
            break;
        }

        yanwenExpressDto.setGoodsName(goodsName);
        yanwenExpressDto.setSendDate(new Date());
        yanwenExpressDto.setChannel(shippingMethodRedis.getMethodCode());
        yanwenExpressDto.setUserOrderNumber(orderId.toString());
        yanwenExpressDto.setQuantity(i);

        YanwenCreateExpressResponse.CreatedExpressDTO createdExpressDTO = null;
        try {
            createdExpressDTO = YanwenApi.createExpress(yanwenExpressDto);
        } catch (CustomerException e) {
            orderService.updateOrderPackInfo(orderId,2,null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString(),e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }

        Long packageNo = getPackageNo();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setId(packageNo);
        orderPackage.setPackageNo(packageNo);
        orderPackage.setOrderId(orderId);
        orderPackage.setPackageState(0);
        orderPackage.setCreateTime(new Date());
        orderPackage.setLogisticsOrderNo(createdExpressDTO.getEpcode());
        orderPackage.setTrackingCode(createdExpressDTO.getReferenceNo());
        orderPackage.setPlatId(createdExpressDTO.getEpcode());
        orderPackage.setStoreId(order.getStoreId());
        orderPackage.setCustomerId(order.getCustomerId());
        orderPackage.setTrackingMethodName(shippingMethodRedis.getName());
        orderPackage.setTrackingMethodCode(shippingMethodRedis.getMethodCode());
        orderPackage.setTrackingCompany(shippingMethodRedis.getTrackingCompany());
        insert(orderPackage);

        orderService.updateOrderPackInfo(orderId,1,packageNo);
        redisTemplate.opsForHash().delete(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString());
        return BaseResponse.success(orderPackage);
    }

    public BaseResponse createCnePackage(Order order,ShippingMethodRedis shippingMethodRedis){
        Long orderId = order.getId();
        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        CneCreateOrderRequest request = new CneCreateOrderRequest();
        CneCreateOrderRequest.RecListDTO recListDTO = new CneCreateOrderRequest.RecListDTO();
        recListDTO.setCEmsKind(shippingMethodRedis.getMethodCode());
        recListDTO.setCRNo(orderId.toString());
        recListDTO.setCDes(orderAddress.getCountryCode());
        recListDTO.setCReceiver(orderAddress.getName());
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
        orderItems.forEach(orderItem -> {
            CneCreateOrderRequest.RecListDTO.CneGoodsListDTO cneGoodsListDTO = new CneCreateOrderRequest.RecListDTO.CneGoodsListDTO();
            cneGoodsListDTO.setCxGoods("宠物服装");
            cneGoodsListDTO.setCxGoodsA("pet clothes");
            cneGoodsListDTO.setIxQuantity(orderItem.getQuantity());
            cneGoodsListDTO.setFxPrice(orderItem.getUsdPrice());
            cneGoodsListDTOS.add(cneGoodsListDTO);
        });
        recListDTO.setGoodsList(cneGoodsListDTOS);
        request.getRecList().add(recListDTO);
        CneOrderDto cneOrderDto = null;
        try {
            cneOrderDto = CneApi.createCneOrder(request);
        } catch (Exception e) {
            orderService.updateOrderPackInfo(orderId,2,null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString(),e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }

        Long packageNo = getPackageNo();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setId(packageNo);
        orderPackage.setPackageNo(packageNo);
        orderPackage.setOrderId(orderId);
        orderPackage.setPackageState(0);
        orderPackage.setCreateTime(new Date());
        orderPackage.setLogisticsOrderNo(cneOrderDto.getCPNo());
        orderPackage.setTrackingCode(cneOrderDto.getCNo());
        orderPackage.setPlatId(cneOrderDto.getIID());
        orderPackage.setStoreId(order.getStoreId());
        orderPackage.setCustomerId(order.getCustomerId());
        orderPackage.setTrackingMethodName(shippingMethodRedis.getName());
        orderPackage.setTrackingMethodCode(shippingMethodRedis.getMethodCode());
        orderPackage.setTrackingCompany(shippingMethodRedis.getTrackingCompany());
        insert(orderPackage);

        orderService.updateOrderPackInfo(orderId,1,packageNo);
        redisTemplate.opsForHash().delete(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString());
        return BaseResponse.success(orderPackage);

    }

    private BaseResponse createFpxPackage(Order order,ShippingMethodRedis shippingMethodRedis){
        Long orderId = order.getId();
        FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO fpxCreateOrderDataDTO = null;
        try {
            fpxCreateOrderDataDTO = createFpxPackage(order);
        } catch (Exception e) {
            orderService.updateOrderPackInfo(orderId,2,null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString(),e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }

        Long packageNo = getPackageNo();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setId(packageNo);
        orderPackage.setPackageNo(packageNo);
        orderPackage.setOrderId(orderId);
        orderPackage.setPackageState(0);
        orderPackage.setCreateTime(new Date());
        orderPackage.setLogisticsOrderNo(fpxCreateOrderDataDTO.getLogisticsChannelNo());
        orderPackage.setTrackingCode(fpxCreateOrderDataDTO.getFpxTrackingNo());
        orderPackage.setPlatId(fpxCreateOrderDataDTO.getLabelBarcode());
        orderPackage.setStoreId(order.getStoreId());
        orderPackage.setCustomerId(order.getCustomerId());
        orderPackage.setTrackingMethodName(shippingMethodRedis.getName());
        orderPackage.setTrackingMethodCode(shippingMethodRedis.getMethodCode());
        orderPackage.setTrackingCompany(shippingMethodRedis.getTrackingCompany());
        insert(orderPackage);

        orderService.updateOrderPackInfo(orderId,1,packageNo);
        redisTemplate.opsForHash().delete(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString());
        return BaseResponse.success(orderPackage);
    }

    private BaseResponse createYunExpressPackage(Order order,ShippingMethodRedis shippingMethodRedis){
        Long orderId = order.getId();
        WayBillCreateDto wayBillCreateDto = new WayBillCreateDto();
        WayBillCreateDto.ReceiverDTO receiverDTO = new WayBillCreateDto.ReceiverDTO();
        List<WayBillCreateDto.ParcelsDTO> parcels = new ArrayList<>();
        List<WayBillCreateDto.OrderExtraDTO> orderExtraDTOs = new ArrayList<>();
        List<WayBillCreateDto.ChildOrdersDTO> childOrdersDTOS = new ArrayList<>();

        List<WayBillCreateDto.ChildOrdersDTO.ChildDetailsDTO> childDetailsDTOS = new ArrayList<>();

        List<OrderItem> orderItems = orderItemService.selectByOrderId(orderId);
        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        receiverDTO.setFirstName("测");
        receiverDTO.setLastName("试");
        receiverDTO.setZip(orderAddress.getZip());
        receiverDTO.setHouseNumber(orderAddress.getAddress2());
        receiverDTO.setStreet(orderAddress.getAddress1());
        receiverDTO.setState(orderAddress.getProvince());
        receiverDTO.setCountryCode(orderAddress.getCountryCode());
        receiverDTO.setPhone("8956232659");
        receiverDTO.setCity(orderAddress.getCity());

        for (OrderItem orderItem : orderItems) {
            WayBillCreateDto.ChildOrdersDTO.ChildDetailsDTO childDetailsDTO = new WayBillCreateDto.ChildOrdersDTO.ChildDetailsDTO();
            childDetailsDTO.setSku(orderItem.getAdminVariantSku());
            childDetailsDTO.setQuantity(orderItem.getQuantity());
            childDetailsDTOS.add(childDetailsDTO);
            WayBillCreateDto.ParcelsDTO parcelsDTO = new WayBillCreateDto.ParcelsDTO();
            parcelsDTO.setEName("pet clothes");
            parcelsDTO.setCName("宠物服装");
            parcelsDTO.setCurrencyCode("USD");
            parcelsDTO.setSKU(orderItem.getAdminVariantSku());
            parcelsDTO.setQuantity(orderItem.getQuantity());
            parcelsDTO.setUnitPrice(orderItem.getUsdPrice());
            parcelsDTO.setUnitWeight(orderItem.getAdminVariantWeight().divide(new BigDecimal("1000"),2,BigDecimal.ROUND_UP));
            parcels.add(parcelsDTO);
            break;
        }

        WayBillCreateDto.ChildOrdersDTO childOrdersDTO = new WayBillCreateDto.ChildOrdersDTO();
        childOrdersDTO.setChildDetails(childDetailsDTOS);
        childOrdersDTO.setBoxNumber(orderId.toString());
        childOrdersDTO.setBoxWeight(BigDecimal.ONE);
        childOrdersDTOS.add(childOrdersDTO);

        WayBillCreateDto.OrderExtraDTO orderExtraDTO = new WayBillCreateDto.OrderExtraDTO();
        orderExtraDTO.setExtraCode("V1");
        orderExtraDTO.setExtraName("云涂预缴");
        orderExtraDTOs.add(orderExtraDTO);

        wayBillCreateDto.setChildOrders(childOrdersDTOS);
        wayBillCreateDto.setCustomerOrderNumber(orderId.toString());
        wayBillCreateDto.setShippingMethodCode(shippingMethodRedis.getMethodCode());
        wayBillCreateDto.setPackageCount(1);
        wayBillCreateDto.setWeight(order.getTotalWeight().divide(new BigDecimal("1000"),2,BigDecimal.ROUND_UP));
        wayBillCreateDto.setIossCode("IOSS0690112210251452600");
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

            if (wayBillItemVo.getSuccess() != 1){
                orderService.updateOrderPackInfo(orderId,2,null);
                redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString(),wayBillItemVo.getRemark());
                return BaseResponse.failed(wayBillCreateResponse.getMessage());
            }
        } catch (Exception e) {
            orderService.updateOrderPackInfo(orderId,2,null);
            redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString(),e.getMessage());
            return BaseResponse.failed(e.getMessage());
        }

        Long packageNo = getPackageNo();
        OrderPackage orderPackage = new OrderPackage();
        orderPackage.setId(packageNo);
        orderPackage.setPackageNo(packageNo);
        orderPackage.setOrderId(orderId);
        orderPackage.setPackageState(0);
        orderPackage.setCreateTime(new Date());
        orderPackage.setLogisticsOrderNo(wayBillItemVo.getWayBillNumber());
        orderPackage.setTrackingCode(wayBillItemVo.getTrackingNumber());
        orderPackage.setPlatId(wayBillItemVo.getWayBillNumber());
        orderPackage.setStoreId(order.getStoreId());
        orderPackage.setCustomerId(order.getCustomerId());
        orderPackage.setTrackingMethodName(shippingMethodRedis.getName());
        orderPackage.setTrackingMethodCode(shippingMethodRedis.getMethodCode());
        orderPackage.setTrackingCompany(shippingMethodRedis.getTrackingCompany());
        insert(orderPackage);

        orderService.updateOrderPackInfo(orderId,1,packageNo);
        redisTemplate.opsForHash().delete(RedisKey.HASH_ORDER_CREATE_PACKAGE_FAILED_REASON,orderId.toString());
        return BaseResponse.success(orderPackage);
    }

    public FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO createFpxPackage(Order order) throws CustomerException {
       Long orderId = order.getId();

        List<OrderItem> orderItems = orderItemService.selectByOrderId(orderId);

        UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,order.getCustomerId().toString());

        CustomerIossVo customerIossVo = (CustomerIossVo) redisTemplate.opsForValue().get(RedisKey.STRING_CUSTOMER_IOSS + order.getCustomerId());

        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,order.getShipMethodId().toString());

        OrderAddress orderAddress = orderService.getOrderAddress(orderId);

        FpxOrderCreateDto fpxOrderCreateDto = new FpxOrderCreateDto();
        fpxOrderCreateDto.setRefNo(order.getId().toString());
        fpxOrderCreateDto.setBuyerId(userVo.getUsername());
        if (customerIossVo != null){
            fpxOrderCreateDto.setVatNo(customerIossVo.getIossNum());
        }

        List<ParcelListDTO> parcelListDTOS = new ArrayList<>();

        List<ParcelListDTO.DeclareProductInfoDTO> declareProductInfoDTOS = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            ParcelListDTO.DeclareProductInfoDTO declareProductInfoDTO = new ParcelListDTO.DeclareProductInfoDTO();
            declareProductInfoDTO.setDeclareProductNameEn("pet clothes");
            declareProductInfoDTO.setDeclareProductNameCn("宠物服装");
            declareProductInfoDTO.setDeclareProductCodeQty(orderItem.getQuantity());
            declareProductInfoDTO.setDeclareUnitPriceExport(orderItem.getUsdPrice());
            declareProductInfoDTO.setDeclareUnitPriceImport(orderItem.getUsdPrice());
            declareProductInfoDTOS.add(declareProductInfoDTO);
        });

        ParcelListDTO parcelListDTO = new ParcelListDTO();
        parcelListDTO.setWeight(order.getTotalWeight().intValue());
        parcelListDTO.setParcelValue(order.getProductAmount().doubleValue());
        parcelListDTO.setCurrency("USD");
        parcelListDTOS.add(parcelListDTO);
        parcelListDTO.setDeclareProductInfo(declareProductInfoDTOS);
        fpxOrderCreateDto.setParcelList(parcelListDTOS);

        RecipientInfoDTO recipientInfo = fpxOrderCreateDto.getRecipientInfo();
        BeanUtils.copyProperties(orderAddress,recipientInfo);
        recipientInfo.setFirstName("测");
        recipientInfo.setLastName("试");
        recipientInfo.setPostCode(orderAddress.getZip());
        recipientInfo.setDistrict(orderAddress.getAddress2());
        recipientInfo.setStreet(orderAddress.getAddress1());
        recipientInfo.setState(orderAddress.getProvince());
        recipientInfo.setCountry(orderAddress.getCountryCode());
        recipientInfo.setPhone("8956232659");
        recipientInfo.setPhone2("18562356856");

        fpxOrderCreateDto.getLogisticsServiceInfo().setLogisticsProductCode(shippingMethodRedis.getMethodCode());

        try {
            FpxCreateOrderSuccessVo.FpxCreateOrderDataDTO fpxCreateOrderDataDTO = FpxOrderApi.createFpxOrder(fpxOrderCreateDto);
            return fpxCreateOrderDataDTO;
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }


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
    public OrderPackage selectByPrimaryKey(Long id,Long orderId,String trackingCode){
        if(null != id){
            return orderPackageDao.selectByPrimaryKey(id);
        }
        if (null != orderId){
            return orderPackageDao.selectByOrderId(orderId);
        }
        if (null != trackingCode){
            return orderPackageDao.selectByTrackingCode(trackingCode);
        }
        return null;
    }

    public OrderPackage selectByPrimaryKey(Long id){
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
    public List<OrderPackage> select(Page<OrderPackage> record){
        record.initFromNum();
        return orderPackageDao.select(record);
    }

    /**
     *
     */
    public long count(Page<OrderPackage> record){
        return orderPackageDao.count(record);
    }

    public Long getPackageNo(){
        String lockKey = "order:package:no:lock";

        boolean b = RedisUtil.lock(redisTemplate,lockKey,5L,10L);
        if (!b){
            return null;
        }
        String key = "order:package:no";
        String date = DateUtils.getDate("yyyyMMdd");
        b = redisTemplate.opsForHash().hasKey(key,date);
        Long no = null;
        if (!b){
            no = Long.parseLong(date + "00001");
        }else {
            no = (Long) redisTemplate.opsForHash().get(key,date);
            if (null != no){
                no = no + 1;
            }else {
                no = Long.parseLong(date + "00001");
            }
        }
        redisTemplate.opsForHash().put(key,date,no);
        RedisUtil.unLock(redisTemplate,lockKey);
        return no;
    }



}
