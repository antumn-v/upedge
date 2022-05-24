package com.upedge.oms.modules.wholesale.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountOrderRefundedRequest;
import com.upedge.common.model.user.vo.CustomerAffiliateVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.request.ConfirmRefundRequest;
import com.upedge.oms.modules.order.request.OrderRefundRejectRefundRequest;
import com.upedge.oms.modules.order.request.OrderRefundUpdateRemarkRequest;
import com.upedge.oms.modules.statistics.request.OrderRefundDailyCountRequest;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleRefundDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleRefundItemDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefund;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefundItem;
import com.upedge.oms.modules.wholesale.request.ApplyWholesaleOrderRefundRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundListRequest;
import com.upedge.oms.modules.wholesale.response.WholesaleRefundListResponse;
import com.upedge.oms.modules.wholesale.service.WholesaleRefundService;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderItemVo;
import com.upedge.oms.modules.wholesale.vo.WholesaleRefundVo;
import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.ApiCancelOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiOrderInfo;
import com.upedge.thirdparty.saihe.service.SaiheService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class WholesaleRefundServiceImpl implements WholesaleRefundService {

    @Autowired
    private WholesaleRefundDao wholesaleRefundDao;
    @Autowired
    private WholesaleOrderDao wholesaleOrderDao;
    @Autowired
    private WholesaleOrderItemDao wholesaleOrderItemDao;
    @Autowired
    private WholesaleRefundItemDao wholesaleRefundItemDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UmsFeignClient umsFeignClient;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleRefund record = new WholesaleRefund();
        record.setId(id);
        return wholesaleRefundDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleRefund record) {
        return wholesaleRefundDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleRefund record) {
        return wholesaleRefundDao.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse orderApplyRefund(ApplyWholesaleOrderRefundRequest request, Session session) {
        Long orderId = request.getOrderId();
        WholesaleOrder order = wholesaleOrderDao.selectByPrimaryKey(orderId);
        if (0 == order.getPayState() || 0 != order.getRefundState()) {
            return BaseResponse.failed();
        }
        if (request.getShippingPrice().compareTo(order.getShipPrice()) > 0
                || request.getVatAmount().compareTo(order.getVatAmount()) > 0) {
            return BaseResponse.failed("The refund amount cannot be greater than the actual payment amount");
        }
        Long refundId = IdGenerate.nextId();
        BigDecimal refundAmount = BigDecimal.ZERO;
        BigDecimal refundProductAmount = BigDecimal.ZERO;
        List<WholesaleRefundItem> refundItems = request.getRefundItemList();
        if (ListUtils.isNotEmpty(refundItems)) {
            List<WholesaleOrderItemVo> itemVos = wholesaleOrderItemDao.listWholesaleOrderItem(orderId);
            Map<Long, WholesaleOrderItemVo> itemMap = new HashMap<>();
            for (WholesaleOrderItemVo itemVo : itemVos) {
                itemMap.put(itemVo.getId(), itemVo);
            }
            for (WholesaleRefundItem refundItem : refundItems) {
                WholesaleOrderItemVo itemVo = itemMap.get(refundItem.getOrderItemId());
                if (null == itemVo || itemVo.getQuantity() < refundItem.getQuantity()) {
                    return BaseResponse.failed("The product refund quantity cannot be greater than the actual paid quantity");
                }
                refundProductAmount = new BigDecimal(refundItem.getQuantity()).multiply(itemVo.getUsdPrice()).add(refundProductAmount);
                refundItem.setOrderId(orderId);
                refundItem.setRefundId(refundId);
                refundItem.setPrice(itemVo.getUsdPrice());
                refundItem.setVariantImage(itemVo.getAdminVariantImg());
                refundItem.setVariantSku(itemVo.getAdminVariantSku());
            }
        }
        refundAmount = refundAmount.add(request.getShippingPrice()).add(request.getVatAmount()).add(refundProductAmount);
        BigDecimal payAmount = order.getShipPrice().add(order.getProductAmount()).add(order.getVatAmount());
        if (refundAmount.compareTo(payAmount) > 0) {
            return BaseResponse.failed("The refund amount cannot be greater than the actual payment amount");
        }

        WholesaleRefund appRefund = new WholesaleRefund();
        appRefund.setId(refundId);
        appRefund.setOrderId(orderId);
        appRefund.setCustomerId(session.getCustomerId());
        appRefund.setReason(request.getRefundReason());
        appRefund.setRemark(request.getRemark());
        appRefund.setUpdateTime(new Date());
        appRefund.setCreateTime(new Date());
        appRefund.setRefundAmount(refundAmount);
        appRefund.setState(0);//申请中
        appRefund.setSource(0);//来源app
        appRefund.setTrackingState(0);
        appRefund.setRefundShippingPrice(request.getShippingPrice());
        appRefund.setRefundVatAmount(request.getVatAmount());
        appRefund.setRefundProductAmount(refundProductAmount);
        //修改订单状态  已付款的改为 退款中
        int res = wholesaleOrderDao.updateOrderAsRefunding(orderId);
        if (0 == res) {
            return BaseResponse.failed();
        }
        //添加退款申请
        wholesaleRefundDao.insert(appRefund);
        if (refundItems.size() > 0) {
            //添加退款产品信息
            wholesaleRefundItemDao.insertByBatch(refundItems);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     *
     */
    public WholesaleRefund selectByPrimaryKey(Long id) {
        return wholesaleRefundDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleRefund record) {
        return wholesaleRefundDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(WholesaleRefund record) {
        return wholesaleRefundDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<WholesaleRefund> select(Page<WholesaleRefund> record) {
        record.initFromNum();
        return wholesaleRefundDao.select(record);
    }

    /**
     *
     */
    public long count(Page<WholesaleRefund> record) {
        return wholesaleRefundDao.count(record);
    }

    /**
     * 批发订单申请退款
     *
     * @throws CustomerException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public BaseResponse applyWholesaleOrder(ApplyWholesaleOrderRefundRequest request, Session session) throws CustomerException {
        Long orderId = request.getOrderId();
        String refundReason = request.getRefundReason();
        BigDecimal refundShippingPrice = request.getShippingPrice();
        BigDecimal refundVatAmount = request.getVatAmount();
        String remark = request.getRemark();
        List<WholesaleRefundItem> refundItemList = request.getRefundItemList();
        if (refundVatAmount == null || refundVatAmount.compareTo(BigDecimal.ZERO) < 0
                || refundShippingPrice == null || refundShippingPrice.compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
        }
        WholesaleOrder appPandaOrder = wholesaleOrderDao.selectByPrimaryKey(orderId);
        if (appPandaOrder == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单不存在!");
        }
        if (appPandaOrder.getPayState() != 1 || appPandaOrder.getRefundState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单状态不满足退款条件!");
        }
        if (appPandaOrder.getOrderType() == 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "补发订单不能退款!");
        }
        BigDecimal refundAmount = BigDecimal.ZERO;
        BigDecimal refundProductAmount = BigDecimal.ZERO;
        refundAmount = refundAmount.add(refundShippingPrice).add(refundVatAmount);
        //检查退款产品
        Long refundId = IdGenerate.nextId();
        for (WholesaleRefundItem refundItem : refundItemList) {
            if (refundItem.getQuantity() == null || refundItem.getQuantity() <= 0) {
                return new BaseResponse(ResultCode.FAIL_CODE, "数量异常!");
            }
            if (refundItem.getOrderItemId() == null) {
                return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
            }
            if (StringUtils.isBlank(refundItem.getVariantImage())
                    || StringUtils.isBlank(refundItem.getVariantSku())) {
                return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
            }
            refundItem.setOrderId(orderId);
            WholesaleOrderItem old = wholesaleOrderItemDao.queryWholesaleOrderItemByIdAndOrderId(refundItem.getOrderItemId(), orderId);
            if (old == null) {
                return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
            }
            if (refundItem.getQuantity() > old.getQuantity()) {
                return new BaseResponse(ResultCode.FAIL_CODE, "产品数量超过限制!");
            }
            refundProductAmount = refundProductAmount.add(old.getUsdPrice().
                    multiply(new BigDecimal(refundItem.getQuantity())));
            refundItem.setPrice(old.getUsdPrice());
            refundItem.setRefundId(refundId);
        }
        if (refundShippingPrice.compareTo(BigDecimal.ZERO) <= 0 && refundProductAmount.compareTo(BigDecimal.ZERO) <= 0
                && refundVatAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "无效退款!");
        }
        if (refundProductAmount.compareTo(appPandaOrder.getProductAmount()) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款产品金额超过限制!");
        }
        refundAmount = refundAmount.add(refundProductAmount);
        if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款总金额异常!");
        }
        BigDecimal vatAmount = appPandaOrder.getVatAmount() == null ? BigDecimal.ZERO : appPandaOrder.getVatAmount();
        if (refundVatAmount.compareTo(vatAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款VAT税费超过限制!");
        }
        if (refundShippingPrice.compareTo(appPandaOrder.getShipPrice()) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款运费超过限制!");
        }
        //可退最大金额
        BigDecimal maxAmount = appPandaOrder.getShipPrice().
                add(appPandaOrder.getProductAmount()).add(vatAmount);
        if (refundAmount.compareTo(maxAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "申请退款金额大于可退金额!");
        }
        //申请退款
       // String userCode = String.valueOf(session.getId());

        WholesaleRefund appRefund = new WholesaleRefund();
        appRefund.setId(refundId);
        appRefund.setOrderId(orderId);
        appRefund.setCustomerId(appPandaOrder.getCustomerId());
        appRefund.setManagerCode(String.valueOf(session.getId()));
        appRefund.setReason(refundReason);
        appRefund.setRemark(remark);
        appRefund.setUpdateTime(new Date());
        appRefund.setCreateTime(new Date());
        appRefund.setRefundAmount(refundAmount);
        appRefund.setState(0);//申请中
        appRefund.setSource(1);//来源admin
        appRefund.setTrackingState(0);
        appRefund.setRefundShippingPrice(refundShippingPrice);
        appRefund.setRefundVatAmount(refundVatAmount);
        appRefund.setRefundProductAmount(refundProductAmount);
        //修改订单状态  已付款的改为 退款中
        int res = wholesaleOrderDao.updateOrderAsRefunding(orderId);
        if (res == 0) {
            throw new CustomerException("订单状态过期,申请订单退款失败");
        }
        //添加退款申请
        wholesaleRefundDao.insert(appRefund);
        if (refundItemList.size() > 0) {
            //添加退款产品信息
            wholesaleRefundItemDao.insertByBatch(refundItemList);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, "申请退款成功!");

    }

    /**
     * 退款申请列表
     *
     * @param request
     * @return
     */
    @Override
    public WholesaleRefundListResponse refundOrderList(WholesaleRefundListRequest request) {
        if (request.getT() == null) {
            request.setT(new WholesaleRefund());
        }
        WholesaleRefund orderRefund = request.getT();
        //orderRefund.setState(0);
        request.initFromNum();
        List<WholesaleRefundVo> results = wholesaleRefundDao.refundOrderList(request);
        for (WholesaleRefundVo result : results) {
            String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE,result.getCustomerId());
            result.setManagerCode(managerCode);
            BaseResponse customerAffiliateResponse =  umsFeignClient.customerAffiliateInfo(result.getCustomerId());
            if (customerAffiliateResponse.getCode() == ResultCode.SUCCESS_CODE){
                CustomerAffiliateVo customerAffiliateVo =   JSON.parseObject(JSON.toJSONString( customerAffiliateResponse.getData()),CustomerAffiliateVo.class);
                if (customerAffiliateVo != null){
                   result.setCustomerAffiliateName(customerAffiliateVo.getAffiliateName());
                }
            }

            BaseResponse customerResponse = umsFeignClient.customerInfo(result.getCustomerId());
            if (customerResponse.getCode() == ResultCode.SUCCESS_CODE){
                CustomerVo customerVo =   JSON.parseObject(JSON.toJSONString( customerResponse.getData()),CustomerVo.class);
                if (customerVo != null){
                    result.setCustomerLoginName(customerVo.getLoginName());
                    result.setCustomerName(customerVo.getUsername());
                }
            }
        }
        Long total = wholesaleRefundDao.refundOrderCount(request);
        request.setTotal(total);
        return new WholesaleRefundListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }

    /**
     * 批发订单历史退款列表
     *
     * @param request
     * @return
     */
    @Override
    public WholesaleRefundListResponse refundOrderHistory(WholesaleRefundListRequest request) {
        if (request.getT() == null) {
            request.setT(new WholesaleRefund());
        }
        WholesaleRefund orderRefund = request.getT();
        //0:申请中 1:确认退款 2:已驳回
        orderRefund.setGteState(1);
        request.initFromNum();
        List<WholesaleRefundVo> results = wholesaleRefundDao.refundOrderList(request);
        Long total = wholesaleRefundDao.refundOrderCount(request);
        request.setTotal(total);
        return new WholesaleRefundListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);

    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public BaseResponse rejectRefund(OrderRefundRejectRefundRequest request, Session session) throws CustomerException {
        WholesaleRefund orderRefund = wholesaleRefundDao.selectByPrimaryKey(request.getId());
        if (orderRefund == null || orderRefund.getState() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "参数异常！");
        }
        //0:申请中 1:确认退款 2:已驳回
        if (orderRefund.getState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "状态过时请刷新！");
        }
        WholesaleOrder order = wholesaleOrderDao.selectByPrimaryKey(orderRefund.getOrderId());
        //pay_state 支付状态,待支付=0，已支付=1，取消订单=-1，支付中=2
        //refund_state 退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if (order == null || order.getPayState() == null || order.getPayState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单状态异常！");
        }
        if (order.getRefundState() == null || order.getRefundState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款状态异常！");
        }
        //String userCode = String.valueOf(session.getId());

        //0:申请中 1:确认退款 2:已驳回
        orderRefund.setState(2);
        orderRefund.setRejectInfo(request.getRejectInfo());
        orderRefund.setManagerCode(String.valueOf(session.getId()));

        orderRefund.setUpdateTime(new Date());
        int res = wholesaleRefundDao.rejectRefund(orderRefund);
        if (res == 0) {
            throw new CustomerException(ResultCode.FAIL_CODE, "驳回失败!");
        }
        //恢复订单退款状态
        int rs = wholesaleOrderDao.restoreOrderRefundState(order.getId());
        if (rs == 0) {
            throw new CustomerException(ResultCode.FAIL_CODE, "驳回失败!");
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, "驳回成功！");
    }

    /**
     * 更新备注
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public BaseResponse updateRemark(OrderRefundUpdateRemarkRequest request) {
        WholesaleRefund orderRefund = wholesaleRefundDao.selectByPrimaryKey(request.getId());
        if (orderRefund == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "更新备注失败！");
        }
        wholesaleRefundDao.updateRemark(request.getId(), request.getRemark());
        return new BaseResponse(ResultCode.SUCCESS_CODE, "更新备注成功！");
    }

    /**
     * 确认退款
     */
    @Override
    @GlobalTransactional//分布式事务注解
    public BaseResponse confirmRefund(ConfirmRefundRequest request, Session session) throws CustomerException {
        //确认退款
        WholesaleRefund orderRefund=wholesaleRefundDao.selectByPrimaryKey(request.getId());
        if(orderRefund==null||orderRefund.getState()==null
                ||orderRefund.getCustomerId()==null||orderRefund.getOrderId()==null||
                orderRefund.getRefundAmount()==null){
            return new BaseResponse(ResultCode.FAIL_CODE,"参数异常");
        }
        //退款状态，申请中=0，通过=1，驳回=2
        //订单状态为退款中
        if(orderRefund.getState()!=0){
            return new BaseResponse(ResultCode.FAIL_CODE,"状态过时请刷新");
        }

        //查询订单
        Long orderId=orderRefund.getOrderId();
        WholesaleOrder order=wholesaleOrderDao.selectByPrimaryKey(orderId);
        if(order==null){
            return new BaseResponse(ResultCode.FAIL_CODE,"找不到订单");
        }
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if(order.getPayState()==null||order.getPayState()!=1){
            return new BaseResponse(ResultCode.FAIL_CODE,"订单支付状态异常");
        }
        if(order.getRefundState()==null||order.getRefundState()!=1){
            return new BaseResponse(ResultCode.FAIL_CODE,"订单退款状态异常");
        }
        if(order.getPayAmount()==null||order.getPayAmount().compareTo(BigDecimal.ZERO)<=0){
            return new BaseResponse(ResultCode.FAIL_CODE,"订单金额异常");
        }

        //检查申请退款金额
        BigDecimal refundAmount=orderRefund.getRefundAmount();
        if(refundAmount==null||refundAmount.compareTo(BigDecimal.ZERO)<=0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款金额异常");
        }

        //检查申请退款金额  不能大于支付总金额  支付商品总金额/USD+运费/USD+VAT税费
        BigDecimal shippingPrice=order.getShipPrice()==null? BigDecimal.ZERO:order.getShipPrice();
        BigDecimal vatAmount=order.getVatAmount()==null? BigDecimal.ZERO:order.getVatAmount();
        BigDecimal productAmount=order.getProductAmount()==null? BigDecimal.ZERO:order.getProductAmount();
        BigDecimal totalAmount=shippingPrice.add(vatAmount).add(productAmount);
        if(refundAmount.compareTo(totalAmount)>0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款金额超过可退金额");
        }

        BigDecimal refundShippingPrice=orderRefund.getRefundShippingPrice();
        if(refundShippingPrice==null||refundShippingPrice.compareTo(BigDecimal.ZERO)<0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款运费金额异常");
        }
        if(refundShippingPrice.compareTo(shippingPrice)>0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款运费金额超出限制");
        }
        BigDecimal refundVatAmount=orderRefund.getRefundVatAmount();
        if(refundVatAmount==null||refundVatAmount.compareTo(BigDecimal.ZERO)<0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款VAT金额异常");
        }
        if(refundVatAmount.compareTo(vatAmount)>0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款VAT金额超出限制");
        }
        BigDecimal refundProductAmount=orderRefund.getRefundProductAmount();
        if(refundProductAmount==null||refundProductAmount.compareTo(BigDecimal.ZERO)<0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款产品金额异常");
        }
        if(refundProductAmount.compareTo(productAmount)>0){
            return new BaseResponse(ResultCode.FAIL_CODE,"退款产品金额超出限制");
        }

        Integer state= 2;//退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        //与可退金额做对比
        if(orderRefund.getRefundAmount().compareTo(totalAmount)==0){
            state=3;
        }

        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        //修改订单状态
        int res=wholesaleOrderDao.updateOrderAsRefund(order.getId(),state);
        if(res==0){
            throw new CustomerException(ResultCode.FAIL_CODE,"修改订单状态异常");
        }
        //更新状态 已确认
        orderRefund.setState(1);
      //  String userCode=String.valueOf(session.getId());
        orderRefund.setManagerCode(String.valueOf(session.getId()));
        orderRefund.setUpdateTime(new Date());
        int rs=wholesaleRefundDao.updateConfirmRefund(orderRefund);
        if(rs==0){
            throw new CustomerException(ResultCode.FAIL_CODE,"修改退款单状态异常");
        }

        //作废赛盒订单，并同步赛盒发货状态
        cancelSaiheOrderAndSynState(orderRefund.getId(),order.getSaiheOrderCode());

        // 统计退款信息
        OrderRefundDailyCountRequest orderRefundDailyCountRequest = new OrderRefundDailyCountRequest();
        orderRefundDailyCountRequest.setRefundId(request.getId());
        orderRefundDailyCountRequest.setOrderType(OrderType.WHOLESALE);
        orderRefundDailyCountRequest.setRefundTime(new Date());
        redisTemplate.opsForList().leftPush(RedisKey.LIST_CUSTOMER_ORDER_DAILY_REFUND_COUNT_UPDATE,orderRefundDailyCountRequest);


        AccountOrderRefundedRequest accountOrderRefundedRequest=new AccountOrderRefundedRequest();
        accountOrderRefundedRequest.setOrderId(order.getId());
        accountOrderRefundedRequest.setRefundId(request.getId());
        accountOrderRefundedRequest.setCustomerId(order.getCustomerId());
        //支付商品总金额/USD+运费/USD+VAT税费
        accountOrderRefundedRequest.setPayAmount(totalAmount);
        accountOrderRefundedRequest.setRefundAmount(orderRefund.getRefundAmount());
        accountOrderRefundedRequest.setOrderType(OrderType.WHOLESALE);
        //账户退款
        BaseResponse response=umsFeignClient.accountOrderRefunded(accountOrderRefundedRequest);
        if(response.getCode()!=1){
            throw new CustomerException(ResultCode.FAIL_CODE,response.getMsg());
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 取消赛盒订单，并同步发货状态
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public void cancelSaiheOrderAndSynState(Long refundId, String saiheOrderCode) throws CustomerException {
        Integer orderState=null;
        Integer orderSourceId=null;
        //赛盒未发货 取消订单
        //获取订单的赛盒code
        if (!StringUtils.isBlank(saiheOrderCode)) {
            ApiGetOrderResponse apiGetOrderResponse = SaiheService.getOrderByCode(saiheOrderCode);
            if (apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")) {
                List<ApiOrderInfo> l = apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
                if (l != null && l.size() > 0) {
                    if (l != null && l.size() > 0) {
                        //赛盒发货状态 orderState 订单发货状态(未发货 = 0,部分发货 = 1,全部发货 = 2,妥投 = 3)
                        //订单状态OrderStatus(正常 = 0,待审查 = 1,作废 = 2,锁定 = 3,只锁定发货 = 4,已完成 = 5)
                        orderState = l.get(0).getOrderState();
                        Integer orderStatus = l.get(0).getOrderStatus();
                        orderSourceId=l.get(0).getOrderSourceID();
                        //赛盒未发货
                        if (orderState==0) {
                            //订单已作废
                            if(orderStatus==2){
                                //message.append("赛盒未发货,订单已作废!</br> ");
                            }else {
                                //作废订单
                                //作废成功
                                ApiCancelOrderResponse apiCancelOrderResponse = SaiheService.cancelOrderInfo(saiheOrderCode);
                                if (apiCancelOrderResponse.getCancelOrderResult().getStatus().equals("OK") &&
                                        apiCancelOrderResponse.getCancelOrderResult().getSuccess()) {
                                    //message.append("赛盒未发货,订单作废成功!</br> ");
                                } else {
                                    //作废失败
                                    //throw newValidationException("赛盒未发货,订单作废失败!</br> ");
                                    throw new CustomerException(ResultCode.FAIL_CODE,"赛盒未发货,订单作废失败!");
                                }
                            }
                        }
                    }
                }
            }
        }

        //同步退款发货状态
        if(orderState!=null&&orderSourceId!=null) {
            if (orderState > 0) {
                wholesaleRefundDao.updateConfirmAppRefundTrackingState(refundId, 1, orderSourceId);
            } else {
                wholesaleRefundDao.updateConfirmAppRefundTrackingState(refundId, 0, orderSourceId);
            }
        }

    }

}