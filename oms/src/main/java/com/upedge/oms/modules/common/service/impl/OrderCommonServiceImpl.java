package com.upedge.oms.modules.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.config.RocketMqConfig;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.mq.ChangeManagerVo;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.ship.vo.ShippingMethodVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.enums.UpdateOmsManagerEnum;
import com.upedge.oms.modules.common.dao.SaiheOrderRecordDao;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.common.vo.OrderCommonReshipInfoVo;
import com.upedge.oms.modules.common.vo.OrderTrackingCommerVo;
import com.upedge.oms.modules.common.vo.RefundVo;
import com.upedge.oms.modules.fulfillment.service.OrderFulfillmentService;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderRefundDao;
import com.upedge.oms.modules.order.dao.OrderReshipInfoDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderReshipInfo;
import com.upedge.oms.modules.order.entity.OrderTracking;
import com.upedge.oms.modules.order.service.OrderRefundService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import com.upedge.oms.modules.redis.OmsRedisService;
import com.upedge.oms.modules.stock.service.StockOrderRefundService;
import com.upedge.oms.modules.stock.service.StockOrderService;
import com.upedge.oms.modules.tickets.service.SupportTicketsService;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleRefundDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleReshipInfoDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.entity.WholesaleReshipInfo;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import com.upedge.oms.modules.wholesale.service.WholesaleRefundService;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.SaiheOrder;
import com.upedge.thirdparty.saihe.entity.SaiheOrderItem;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiOrderInfo;
import com.upedge.thirdparty.saihe.entity.getProducts.ApiGetProductResponse;
import com.upedge.thirdparty.saihe.entity.getProducts.ProductInfoList;
import com.upedge.thirdparty.saihe.entity.uploadOrder.ApiUploadOrderInfo;
import com.upedge.thirdparty.saihe.entity.uploadOrder.ApiUploadOrderList;
import com.upedge.thirdparty.saihe.entity.uploadOrder.OrderItemList;
import com.upedge.thirdparty.saihe.entity.uploadOrder.UpLoadOrderV2Response;
import com.upedge.thirdparty.saihe.service.SaiheService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class OrderCommonServiceImpl implements OrderCommonService {

    @Autowired
    private OmsRedisService omsRedisService;

    @Autowired
    private PmsFeignClient pmsFeignClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private WholesaleOrderDao wholesaleOrderDao;

    @Autowired
    private TmsFeignClient tmsFeignClient;

    @Autowired
    private UmsFeignClient umsFeignClient;

    @Autowired
    private WholesaleReshipInfoDao wholesaleReshipInfoDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderReshipInfoDao orderReshipInfoDao;

    @Autowired
    SaiheOrderRecordDao saiheOrderRecordDao;

    @Autowired
    private WholesaleRefundDao wholesaleRefundDao;

    @Autowired
    private OrderRefundDao orderRefundDao;

    @Autowired
    private OrderTrackingService orderTrackingService;

    @Autowired
    private  WholesaleOrderService wholesaleOrderService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRefundService orderRefundService;

    @Autowired
    private StockOrderService stockOrderService;

    @Autowired
    private StockOrderRefundService stockOrderRefundService;

    @Autowired
    private SupportTicketsService supportTicketsService;

    @Autowired
    private WholesaleRefundService wholesaleRefundService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    OrderFulfillmentService orderFulfillmentService;


    /**
     * 订单导入赛盒前saiheOrder的处理和是否满足上传条件的判断
     */
    @Override
    public SaiheOrderRecord importOrderToSaihe(SaiheOrder saiheOrder) {
        //获取客户订单->对应客户经理的订单来源渠道
        //saiheOrder.getCustomerId();

        ManagerInfoVo managerInfoVo = omsRedisService.getCustomerManager(saiheOrder.getManagerCode(), saiheOrder.getCustomerId());
        saiheOrder.setManagerCode(managerInfoVo.getManagerCode());
        saiheOrder.setManagerCode(managerInfoVo.getManagerCode());
        //默认订单来源渠道
        Integer orderSourceId = SaiheConfig.SOURCINBOX_DEFAULT_ORDER_SOURCE_ID;
        if (managerInfoVo != null && managerInfoVo.getOrderSourceId() != null) {
            orderSourceId = managerInfoVo.getOrderSourceId();
        }
        saiheOrder.setOrderSourceID(orderSourceId);
        SaiheOrderRecord saiheOrderRecord = new SaiheOrderRecord();
        BeanUtils.copyProperties(saiheOrder,saiheOrderRecord);
        saiheOrderRecord.setId(IdGenerate.nextId());
        //客户id是（测试账号）不上传
        if (saiheOrder.getCustomerId().equals(SaiheConfig.DEFAULT_ACCOUNT)) {
            saiheOrderRecord.setState(0);
            saiheOrderRecord.setFailReason("测试数据");
            return saiheOrderRecord;
        }
        //   检查产品是否导入赛盒 否则不上传
        if (!uploadSaihe(saiheOrder.getOrderItemList())) {
            saiheOrderRecord.setState(0);
            saiheOrderRecord.setFailReason("产品未导入赛盒");
            return saiheOrderRecord;
        }
        //根据运输方式获取赛盒运输信息
        BaseResponse rsShipMethod = tmsFeignClient.shippingMethodInfo(saiheOrder.getShipMethodId());
        if (rsShipMethod.getCode() != 1 || rsShipMethod.getData() == null) {
            saiheOrderRecord.setState(0);
            saiheOrderRecord.setFailReason("没有找到匹配的赛盒运输方式");
            return saiheOrderRecord;
        }
//        ShippingMethodVo shippingMethodVo = (ShippingMethodVo) rsShipMethod.getData();
        ShippingMethodVo shippingMethodVo = JSON.parseObject(JSON.toJSONString(rsShipMethod.getData()), ShippingMethodVo.class);
        saiheOrder.setTransportID(shippingMethodVo.getSaiheTransportId());
        saiheOrderRecord.setTransportId(shippingMethodVo.getSaiheTransportId());
        //没有找到匹配的赛盒运输方式
        if (saiheOrder.getTransportID() == null || saiheOrder.getTransportID() == -1) {
            saiheOrderRecord.setState(0);
            saiheOrderRecord.setFailReason("没有找到匹配的赛盒运输方式");
            return saiheOrderRecord;
        }
        saiheOrderRecord.setState(1);
        return saiheOrderRecord;
    }


    //检查产品是否导入赛盒 否则不上传
    public Boolean uploadSaihe(List<SaiheOrderItem> orderItemList) {
        for (SaiheOrderItem saiheOrderItem : orderItemList) {
            ApiGetProductResponse apiGetProductResponse = SaiheService.getProductsByClientSKUs(saiheOrderItem.getSellerSku(), null);
            ProductInfoList pInfoList = apiGetProductResponse.getGetProductsResult().getProductInfoList();
            if (apiGetProductResponse.getGetProductsResult().getStatus().equals("OK")
                    && pInfoList != null && pInfoList.getProductInfoList() != null
                    && pInfoList.getProductInfoList().size() > 0) {
                continue;
            } else {
                //加入缓存 产品未导入产品列表
                BaseResponse rs = pmsFeignClient.userInfo(saiheOrderItem.getAdminProductId());
                String userId = (String) rs.getData();
                redisTemplate.opsForHash().put(RedisKey.HASH_BAD_WHOLESALE_ORDER_PRODUCT, String.valueOf(saiheOrderItem.getAdminProductId()), userId);
                return false;
            }
        }
        return true;
    }

    /**
     * 该订单是否在赛盒已经上传了，不用再回传
     * orderCode在数据库中未有，保存信息
     *
     * @return
     */
    @Override
    public Boolean checkAndSaveOrderCodeFromSaihe(String orderId, Integer orderType) {
        ApiGetOrderResponse apiGetOrderResponse2 = SaiheService.getOrderByClientOrderCode(orderId);
        if (apiGetOrderResponse2.getGetOrdersResult().getStatus().equals("OK")) {
            List<ApiOrderInfo> list = apiGetOrderResponse2.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
            if (list != null && list.size() > 0) {
                //获取赛盒单号
                String orderCode = apiGetOrderResponse2.getGetOrdersResult().getOrderInfoList().
                        getOrderInfoList().get(0).getOrderCode();
                String clientOrderCode = apiGetOrderResponse2.getGetOrdersResult().getOrderInfoList().
                        getOrderInfoList().get(0).getClientOrderCode();
                if (!StringUtils.isBlank(orderCode) && !StringUtils.isBlank(clientOrderCode)) {
                    if (clientOrderCode.equals(orderId)) {
                        //更新赛盒单号
                        if(orderType == OrderType.NORMAL){
                            orderDao.updateSaiheOrderCode(orderId,orderCode);
                        }
                        if(orderType == OrderType.WHOLESALE){

                            wholesaleOrderDao.updateSaiheOrderCode(orderId, orderCode);
                        }
                        return true;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 上传订单信息实体封装
     *
     * @param saiheOrder
     * @return
     */
    @Override
    public Boolean upLoadOrderToSaiHe(SaiheOrder saiheOrder, Integer orderType) {
        SaiheOrderRecord record = saiheOrderRecordDao.selectByClientOrderCode(saiheOrder.getClientOrderCode());
        if (null != record && 1 == record.getState()){
            return true;
        }

        SaiheOrderRecord saiheOrderRecord = importOrderToSaihe(saiheOrder);
        saiheOrderRecord.setImportTime(new Date());
        if (record != null){
            saiheOrderRecord.setId(record.getId());
            saiheOrderRecordDao.updateByPrimaryKey(saiheOrderRecord);
        } else{
            saiheOrderRecord.setId(IdGenerate.nextId());
        }
        if (saiheOrderRecord.getState() == 0){
            if (record != null){
                saiheOrderRecordDao.updateByPrimaryKey(saiheOrderRecord);
            } else{
                saiheOrderRecordDao.insert(saiheOrderRecord);
            }
            return false;
        }
        ApiUploadOrderInfo apiUploadOrderInfo = new ApiUploadOrderInfo();
        apiUploadOrderInfo.setCustomerID(SaiheConfig.CUSTOMER_ID);

        //获取客户经理的渠道ID 默认128
        Integer orderSourceId = saiheOrder.getOrderSourceID() == null ? SaiheConfig.SOURCINBOX_DEFAULT_ORDER_SOURCE_ID : saiheOrder.getOrderSourceID();
        //来源渠道ID(需在ERP系统中创建)
        apiUploadOrderInfo.setOrderSourceID(orderSourceId);
        //支付时间 payTime
        apiUploadOrderInfo.setPayTime(saiheOrder.getPayTime());
        //客户订单号 ClientOrderCode
        apiUploadOrderInfo.setClientOrderCode(saiheOrder.getClientOrderCode());
        //邮箱 Email
        apiUploadOrderInfo.setEmail(StringUtils.isBlank(saiheOrder.getEmail()) ? "" : saiheOrder.getEmail());

        //交易号
        apiUploadOrderInfo.setTransactionId(saiheOrder.getTransactionId());
        //币种 Currency
        apiUploadOrderInfo.setCurrency("USD");
        //订单总金额
        apiUploadOrderInfo.setTotalPrice(saiheOrder.getTotalPrice());

        //订单优惠金额 DiscountAmount 使用了返点支付
      //  BaseResponse response = umsFeignClient.accountLogPayInfo(saiheOrder.getClientOrderCode());
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setOrderId(Long.parseLong(saiheOrder.getClientOrderCode()));
        transactionDetail.setOrderType(orderType);
        BaseResponse response = umsFeignClient.accountLogPayInfoByTransactionDetail(transactionDetail);
        if (response.getCode() != 1 || response.getData() == null) {
            return false;
        }

        LinkedHashMap linkedHashMap = (LinkedHashMap) response.getData();
        if (linkedHashMap != null){
            Double d = (Double) linkedHashMap.get("rebate");
            BigDecimal rebate = new BigDecimal(d.toString());
            //使用了返点支付
            if (rebate != null
                    && rebate.abs().

                    compareTo(BigDecimal.ZERO) > 0) {
                apiUploadOrderInfo.setPromotionDiscountAmount(rebate.abs());
            }
        }

        //运费收入 TransportPay+vat税费
        BigDecimal vatAmount = saiheOrder.getVatAmount() == null ? BigDecimal.ZERO : saiheOrder.getVatAmount();
        BigDecimal transportPay = saiheOrder.getTransportPay().add(vatAmount);
        String vatInfo = "";
        if (vatAmount.compareTo(BigDecimal.ZERO) > 0) {
            vatInfo = " vatAmount:" + vatAmount;
        }
        apiUploadOrderInfo.setTransportPay(transportPay);
        //买家留言 OrderDescription
        String appUserId = saiheOrder.getCustomerId() == null ? "" : String.valueOf(saiheOrder.getCustomerId());
        String orderInfo = "";
        if(orderType == OrderType.WHOLESALE){
            orderInfo = " 批发订单";
        }
        //补发订单
        String againInfo = "";
        if (saiheOrder.getOrderType() == 1) {
            OrderCommonReshipInfoVo orderReshipInfo = new OrderCommonReshipInfoVo();
            if(orderType == OrderType.NORMAL){
                OrderReshipInfo reshipInfo= orderReshipInfoDao.selectByPrimaryKey(Long.parseLong(saiheOrder.getClientOrderCode()));
                BeanUtils.copyProperties(reshipInfo,orderReshipInfo);
            }
            if(orderType == OrderType.WHOLESALE){
                WholesaleReshipInfo reshipInfo = wholesaleReshipInfoDao.selectByPrimaryKey(Long.parseLong(saiheOrder.getClientOrderCode()));
                BeanUtils.copyProperties(reshipInfo,orderReshipInfo);
            }
            if (orderReshipInfo != null && orderReshipInfo.getReshipTimes() > 0) {
                againInfo = againInfo + " " + orderReshipInfo.getOriginalOrderId() + "-" + orderReshipInfo.getReshipTimes();
            }
        }
        apiUploadOrderInfo.setClientUserAccount(appUserId);


        if (!StringUtils.isBlank(saiheOrder.getAddrName())) {
            //买家姓氏 FirstName
            apiUploadOrderInfo.setFirstName(saiheOrder.getAddrName());
        } else {
            //买家姓氏 FirstName
            apiUploadOrderInfo.setFirstName(saiheOrder.getFirstName());
            //买家名字 LastName
            apiUploadOrderInfo.setLastName(saiheOrder.getLastName());
        }
        //收货国家 CountryCode 代码
        apiUploadOrderInfo.setCountry(saiheOrder.getCountryCode());
        //收货省份 Province
        String province = saiheOrder.getProvince();
        //省份为空 使用省份代码
        if (province == null) {
            province = saiheOrder.getProvinceCode();
        }
        apiUploadOrderInfo.setProvince(province);
        //收货城市 City
        apiUploadOrderInfo.setCity(saiheOrder.getCity());
        //邮编 PostCode
        apiUploadOrderInfo.setPostCode(saiheOrder.getPostalCode());
        //电话 Telephone
        apiUploadOrderInfo.setTelephone(saiheOrder.getTelephone());
        //收货地址一 Address1
        apiUploadOrderInfo.setAddress1(saiheOrder.getAddress1());
        //收货地址二 Address2
        apiUploadOrderInfo.setAddress2(saiheOrder.getAddress2());

        //订单产品列表
        List<ApiUploadOrderList> list = new ArrayList<>();
        Map<String, Set<String>> mapSet = new HashMap<>();
        for (
                SaiheOrderItem saiheOrderItem : saiheOrder.getOrderItemList()) {
            ApiUploadOrderList apiUploadOrderList = new ApiUploadOrderList();
            //卖家SKUs
            apiUploadOrderList.setSellerSKU(saiheOrderItem.getSellerSku());
            //订单产品数量
            apiUploadOrderList.setProductNum(saiheOrderItem.getProductNum());
            //订单产品销售单价
            apiUploadOrderList.setProductPrice(saiheOrderItem.getSalePrice());
            //OrderItemId
            apiUploadOrderList.setOrderItemId(saiheOrderItem.getOrderItemId());
            list.add(apiUploadOrderList);

            if (!StringUtils.isBlank(saiheOrderItem.getOrderName())
                    && !StringUtils.isBlank(saiheOrderItem.getStoreId())) {
                String storeId = saiheOrderItem.getStoreId();
                Set set = mapSet.get(storeId);
                if (set == null) {
                    set = new HashSet<>();
                    mapSet.put(storeId, set);
                }
                if (!set.contains(saiheOrderItem.getOrderName())) {
                    set.add(saiheOrderItem.getOrderName());
                }
            }
        }
        OrderItemList orderItemList = new OrderItemList();
        orderItemList.setOrderItemList(list);
        apiUploadOrderInfo.setOrderItemList(list);
        if(orderType == OrderType.NORMAL){
            //存放备注信息
            for(Map.Entry<String, Set<String>> entry:mapSet.entrySet()){
                String storeId=entry.getKey();
                Set<String> nameSet=entry.getValue();
                orderInfo=orderInfo+storeId+nameSet.toString();
                orderInfo=orderInfo+" ";
            }
        }
        apiUploadOrderInfo.setOrderDescription(orderInfo + againInfo + vatInfo);

        //发货仓库ID 默认仓库
        apiUploadOrderInfo.setWareHouseID(SaiheConfig.SOURCINBOX_DEFAULT_WAREHOURSE_ID);

        //运输方式ID ShippingService
        apiUploadOrderInfo.setTransportID(saiheOrder.getTransportID());

        //是否执行订单策略(默认为:true；如果为false，则必须传入WareHouseID和TransportID参数)
        apiUploadOrderInfo.setIsOperateMatch(false);
//        log.error("订单上传赛盒:{}",apiUploadOrderInfo);
        //回传
        UpLoadOrderV2Response upLoadOrderV2Response = SaiheService.upLoadOrderV2(apiUploadOrderInfo);
        log.error("订单上传赛盒：{}",saiheOrder);
        if(upLoadOrderV2Response.getUpLoadOrderV2Result().getStatus().equals("OK")){
            String orderCode=upLoadOrderV2Response.getUpLoadOrderV2Result().getOrderCode();
            //回传成功 保存赛盒code
            if(orderType == OrderType.NORMAL){
                orderDao.updateSaiheOrderCode(saiheOrder.getClientOrderCode(),orderCode);
            }
            if(orderType == OrderType.WHOLESALE){
                wholesaleOrderDao.updateSaiheOrderCode(saiheOrder.getClientOrderCode(),orderCode);
            }
            saiheOrderRecord.setSaiheOrderCode(orderCode);
            saiheOrderRecordDao.insert(saiheOrderRecord);
            return true;
        }
        saiheOrderRecord.setState(0);
        if(!StringUtils.isBlank(upLoadOrderV2Response.getUpLoadOrderV2Result().getMsg())){
            String orderId=saiheOrder.getClientOrderCode()==null?"":saiheOrder.getClientOrderCode();
            log.debug("error orderId:{}, msg:{}",orderId,upLoadOrderV2Response.getUpLoadOrderV2Result().getMsg());
            saiheOrderRecord.setFailReason(upLoadOrderV2Response.getUpLoadOrderV2Result().getMsg());
        }else {
            saiheOrderRecord.setFailReason("赛盒异常");
        }
        saiheOrderRecordDao.insert(saiheOrderRecord);
        return false;
    }

    /**
     * 刷新赛盒发货状态
     * @param id
     * @param orderType
     * @return
     * @throws CustomerException
     */
    @Override
    public boolean synRefundTrackingState(String id, Integer orderType) throws CustomerException {
            RefundVo refund = null;
            if(orderType == OrderType.NORMAL){
                refund = orderDao.queryConfirmAppRefundById(id);
            }else if(orderType == OrderType.WHOLESALE){
                refund = wholesaleOrderDao.queryConfirmAppRefundById(id);
            }else {
                return false;
            }
            if (refund == null || refund.getSaiheOrderCode() == null){
                throw new CustomerException(CustomerExceptionEnum.DATA_OF_THE_EXCEPTION);
            }

        try {
            this.updateConfirmAppRefundTrackingState(refund,orderType);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    //更新赛盒状态
    private void updateConfirmAppRefundTrackingState(RefundVo appRefund, Integer orderType) {
        //获取订单的赛盒code
        if (appRefund != null && !StringUtils.isBlank(appRefund.getSaiheOrderCode())) {
            ApiGetOrderResponse apiGetOrderResponse = SaiheService.getOrderByCode( appRefund.getSaiheOrderCode());
            if (apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")) {
                List<ApiOrderInfo> list = apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
                if (list != null && list.size() > 0) {
                    if (list != null && list.size() > 0) {
                        //赛盒发货状态  订单发货状态(未发货 = 0,部分发货 = 1,全部发货 = 2,妥投 = 3)
                        Integer orderState = list.get(0).getOrderState();
                        Integer orderSourceId=list.get(0).getOrderSourceID();
                        if (orderState > 0){
                            orderState = 1;
                        }else {
                            orderState = 0;
                        }
                        if(orderType == OrderType.NORMAL){
                            orderRefundDao.updateRefundTrackingState(appRefund.getId(), orderState,orderSourceId);
                        }
                        if(orderType == OrderType.WHOLESALE){
                            wholesaleRefundDao.updateRefundTrackingState(appRefund.getId(), orderState,orderSourceId);
                        }
                        }
                    }
                }
            }
        }


    /**
     * 获取/更新赛盒物流信息 标记订单已发货
     * 现将普通订单  批发订单的信息都放入order_tracking表
     * 后期同步数据时要注意  order_tracking_type  2 普通订单  3 批发订单
     * @param id
     * @return
     */
    @Override
    public boolean getTrackingFromSaihe(Long id, Integer orderType) {
        OrderTrackingCommerVo a = new OrderTrackingCommerVo();

        if(orderType == OrderType.NORMAL){
            Order order=orderService.selectByPrimaryKey(id);
            a = a.NormalOrderTrackingCommerVo(order,a);
        }
        if(orderType == OrderType.WHOLESALE){
            WholesaleOrder wholesaleOrder= wholesaleOrderService.selectByPrimaryKey(id);
            a = a.wholesaleOrderTrackingCommerVo(wholesaleOrder,a);

            if(a.getShipPrice()==null||a.getShipState()!=0){
                return false;
            }
        }

        if(a==null||a.getSaiheOrderCode()==null||a.getShipMethodId()==null){
            return false;
        }
        if(a.getPayState()==null||a.getPayState()!=1){
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
                if(response.getCode()== ResultCode.SUCCESS_CODE&&response.getData()!=null){
                     shippingMethod = JSON.parseObject(JSON.toJSONString(response.getData()), ShippingMethodVo.class);
                }
                //获取admin的运输方式
                String shippingMethodName = "";
                if(shippingMethod!=null&&shippingMethod.getTrackType()!=null
                        &&!StringUtils.isBlank(orderCode)&&orderCode.equals(a.getSaiheOrderCode())){
                    shippingMethodName = shippingMethod.getName();
                    //有运输商单号或真实追踪号
                    if(!StringUtils.isBlank(logisticsOrderNo)||!StringUtils.isBlank(trackNumbers)){
                        OrderTracking orderTracking = new OrderTracking();
                        orderTracking.setOrderId(a.getId());
                        orderTracking.setUpdateTime(new Date());
                        orderTracking.setShippingMethodName(shippingMethodName);
                        orderTracking.setTransportId(transportId);
                        orderTracking.setTrackNumbers(trackNumbers);
                        orderTracking.setLogisticsOrderNo(logisticsOrderNo);
                        orderTracking.setTrackType(shippingMethod.getTrackType());
                        orderTracking.setTrackingCode(logisticsOrderNo);
                        // 根据orderId和 order_tracking_type查询订单
                        OrderTracking old=orderTrackingService.queryOrderTrackingByOrderId(a.getId(),orderType);
                        if(orderType == OrderType.NORMAL){
                            //标记该记录为普通订单
                            orderTracking.setOrderTrackingType(0);

                            if(old==null){
                                orderTracking.setState(0);
                                orderTracking.setId(IdGenerate.nextId());
                                orderTracking.setCreateTime(new Date());
                                //标记订单为发货
                                orderDao.updateOrderAsTracked(id);
                                orderTrackingService.insert(orderTracking);
                                //处于待回传状态，继续更新运输信息
                                orderFulfillmentService.orderFulfillment(id);
                            }else {
                                orderTrackingService.updateOrderTracking(orderTracking);
                            }
                        }
                        if(orderType == OrderType.WHOLESALE){
                            //标记该记录为批发订单
                            orderTracking.setOrderTrackingType(1);
                            if (old == null){
                                orderTracking.setCreateTime(new Date());
                                //标记订单为发货
                                wholesaleOrderDao.updateOrderAsTracked(id);
                                orderTrackingService.insert(orderTracking);
                            }else {
                                //处于待回传状态，继续更新运输信息
                                orderTrackingService.updateOrderTracking(orderTracking);

                            }

                        }



                        return true;
                    }
                }

            }
        }
        return false;
    }

    /**
     * 修改订单模块业务数据的客户经理Code
     * @param changeManagerVos
     * @return
     */
    @SneakyThrows
    @Override
    public void updateOmsManagerCodeByChanagerManager(List<ChangeManagerVo> changeManagerVos)  {
        for (ChangeManagerVo changeManagerVo : changeManagerVos) {
            updateOmsManagerCode(changeManagerVo);
        }
    }


    // 处理 order  wholesale order 没有赛盒订单号的订单
    @Override
    public void uploadSaiheAndUms() {
        List<PaymentDetail> resultList  =  orderService.selectUploadSaiheAndUms(OrderType.NORMAL);

        for (PaymentDetail paymentDetail : resultList) {
            paymentDetail.setOrderType(OrderType.NORMAL);
            sendSaveTransactionRecordMessage(paymentDetail);
        }
        List<PaymentDetail> resultList1   =  wholesaleOrderService.selectUploadSaiheAndUms(OrderType.WHOLESALE);

        for (PaymentDetail paymentDetail : resultList1) {
            paymentDetail.setOrderType(OrderType.WHOLESALE);
            sendSaveTransactionRecordMessage(paymentDetail);
        }
    }

    @Override
    public void sendOneSaveTransactionRecordMessage(PaymentDetail detail) {
        if (detail.getOrderType() == OrderType.NORMAL){
            detail  =  orderService.selectUploadSaiheAndUmsOne(detail.getPaymentId());
        }

        if (detail.getOrderType() == OrderType.WHOLESALE){
            detail = wholesaleOrderService.selectUploadSaiheAndUmsOne(detail.getPaymentId());
        }
        sendSaveTransactionRecordMessage(detail);
    }

    public void sendSaveTransactionRecordMessage(PaymentDetail detail) {

        Message message = new Message(RocketMqConfig.TOPIC_ORDER_TRANSACTION, "order_order", "order:order:transaction:" + detail.getPaymentId(), JSON.toJSONBytes(detail));

        BaseResponse baseResponse = umsFeignClient.customerInfo(detail.getCustomerId());
        if (baseResponse.getCode() == ResultCode.SUCCESS_CODE && baseResponse.getData() != null){
            CustomerVo customerVo =   JSON.parseObject(JSON.toJSONString( baseResponse.getData()),CustomerVo.class);
            if (customerVo == null){
                log.error("selectUploadSaiheAndUms==>:{}",detail);
                return;
            }
            detail.setUserId(customerVo.getId());
        }

        if (detail.getOrderType() == OrderType.NORMAL){
            BigDecimal payAmount  = orderDao.selectPayAmountByPaymentId(detail.getPaymentId());
            List<TransactionDetail> transactionDetails = orderDao.selectTransactionDetailByPaymentId(detail.getPaymentId());
            if (ListUtils.isEmpty(transactionDetails)) {
                return;
            }
            detail.setPayAmount(payAmount);
            detail.setOrderTransactions(transactionDetails);
            message = new Message(RocketMqConfig.TOPIC_ORDER_TRANSACTION, "normal_order", "normal:order:transaction:" + detail.getPaymentId(), JSON.toJSONBytes(detail));
        }

        if (detail.getOrderType() == OrderType.WHOLESALE){
            BigDecimal payAmount  = wholesaleOrderDao.selectPayAmountByPaymentId(detail.getPaymentId());
            List<TransactionDetail> transactionDetails = wholesaleOrderDao.selectTransactionDetailByPaymentId(detail.getPaymentId());
            if (ListUtils.isEmpty(transactionDetails)) {
                return;
            }
            detail.setPayAmount(payAmount);
            detail.setOrderTransactions(transactionDetails);
            message = new Message("order_transaction", "wholesale_order", "wholesale:order:transaction:" + detail.getPaymentId(), JSON.toJSONBytes(detail));
        }
        message.setDelayTimeLevel(1);
        umsFeignClient.sendMessage(message);
    }


    /**
     * 通过枚举修改各表的managerCode
     * @param changeManagerVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateOmsManagerCode(ChangeManagerVo changeManagerVo) {

        for (UpdateOmsManagerEnum value : UpdateOmsManagerEnum.values()) {
            orderService.updateManagerCode(changeManagerVo,value.getTableName());
        }
        log.info("给客户分配客户经理，该客户CustomerId为：{}，该客户原经理为：{}，该客户现经理为：{}",changeManagerVo.getCustomerId(),changeManagerVo.getOldManager(),changeManagerVo.getNewManager());
        return true;
    }


}


