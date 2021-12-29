package com.upedge.oms.modules.wholesale.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.request.ReturnOrderPayAmountToAccountRequest;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.request.WholesaleOrderAppListRequest;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.service.WholesaleShipReviewService;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class WholesaleShipReviewServiceImpl implements WholesaleShipReviewService {


    @Autowired
    WholesaleOrderDao wholesaleOrderDao;

    @Autowired
    WholesaleOrderItemDao wholesaleOrderItemDao;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    CustomerProductStockService customerProductStockService;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<WholesaleOrderAppVo> reviewList(WholesaleOrderAppListRequest request) {

        List<WholesaleOrderAppVo> wholesaleOrderAppVos = wholesaleOrderDao.selectOrderAppList(request);

        return wholesaleOrderAppVos;
    }

    @Override
    public BaseResponse confirm(Long id) {
        WholesaleOrder wholesaleOrder = wholesaleOrderDao.selectByPrimaryKey(id);
        if (null == wholesaleOrder){
            return BaseResponse.failed("订单不存在");
        }
        if (1 != wholesaleOrder.getPayState()
        || 2 != wholesaleOrder.getFreightReview()){
            return BaseResponse.failed("订单未支付或运费已审核");
        }
        wholesaleOrder = new WholesaleOrder();
        wholesaleOrder.setId(id);
        wholesaleOrder.setFreightReview(3);
        wholesaleOrder.setUpdateTime(new Date());
        wholesaleOrderDao.updateByPrimaryKeySelective(wholesaleOrder);
        return BaseResponse.success();
    }

    @GlobalTransactional
    @Override
    public BaseResponse reject(Long id) {
        WholesaleOrder wholesaleOrder = wholesaleOrderDao.selectByPrimaryKey(id);
        if (null == wholesaleOrder){
            return BaseResponse.failed("订单不存在");
        }
        if (wholesaleOrder.getPayState() != OrderConstant.PAY_STATE_PAID
        || wholesaleOrder.getFreightReview() != 2){
            return BaseResponse.failed("订单未支付或运费已审核");
        }
        BigDecimal payAmount = wholesaleOrder.getPayAmount();
        ReturnOrderPayAmountToAccountRequest request = new ReturnOrderPayAmountToAccountRequest();
        request.setOrderId(id);
        request.setPayAmount(payAmount);
        request.setPaymentId(wholesaleOrder.getPaymentId());
        BaseResponse response = umsFeignClient.returnOrderPayAmount(request);
        if (response.getCode() != ResultCode.FAIL_CODE){
            return response;
        }
        List<ItemDischargeQuantityVo> itemDischargeQuantityVos = wholesaleOrderItemDao.selectDischargeQuantityByOrderId(id);
        if (ListUtils.isNotEmpty(itemDischargeQuantityVos)){
            customerProductStockService.increaseFromLockStock(wholesaleOrder.getCustomerId(), itemDischargeQuantityVos);
        }
        wholesaleOrder = new WholesaleOrder();
        wholesaleOrder.setId(id);
        wholesaleOrder.setPayState(0);
        wholesaleOrder.setShipPrice(BigDecimal.ZERO);
        wholesaleOrder.setShipMethodName(null);
        wholesaleOrder.setFreightReview(0);
        wholesaleOrder.setUpdateTime(new Date());
        wholesaleOrderDao.updateByPrimaryKeySelective(wholesaleOrder);
        return BaseResponse.success();
    }
}
