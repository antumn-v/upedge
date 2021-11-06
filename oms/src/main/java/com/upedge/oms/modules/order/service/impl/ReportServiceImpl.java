package com.upedge.oms.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderProfitDao;
import com.upedge.oms.modules.order.dao.StoreOrderDao;
import com.upedge.oms.modules.order.request.OrderCogsSelectRequest;
import com.upedge.oms.modules.order.request.OrderInsightSelectRequest;
import com.upedge.oms.modules.order.request.StoreOrderSaleSelectRequest;
import com.upedge.oms.modules.order.service.ReportService;
import com.upedge.oms.modules.order.vo.OrderCogsVo;
import com.upedge.oms.modules.order.vo.OrderInsightsVo;
import com.upedge.oms.modules.order.vo.StoreOrderSaleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    StoreOrderDao storeOrderDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderProfitDao orderProfitDao;

    @Autowired
    private UmsFeignClient umsFeignClient;

    @Override
    public BaseResponse storeOrderSales(Session session, StoreOrderSaleSelectRequest request) {
        if(session.getUserType() == BaseCode.USER_ROLE_NORMAL
                && ListUtils.isEmpty(request.getOrgIds())){
            request.setOrgIds(session.getOrgIds());
        }
        List<StoreOrderSaleVo> saleVoList = storeOrderDao.selectCustomerStoreOrderSales(session.getCustomerId(),request);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,saleVoList,request);
    }

    @Override
    public BaseResponse customerOrderCogs(Session session, OrderCogsSelectRequest request) {
        if(session.getUserType() == BaseCode.USER_ROLE_NORMAL
                && ListUtils.isEmpty(request.getOrgIds())){
            request.setOrgIds(session.getOrgIds());
        }
        List<OrderCogsVo> cogsVos = orderDao.selectCustomerOrderCogs(session.getCustomerId(),request);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,cogsVos,request);
    }

    @Override
    public BaseResponse customerOrderInsights(Session session, OrderInsightSelectRequest request) {
        if(session.getUserType() == BaseCode.USER_ROLE_NORMAL
                && ListUtils.isEmpty(request.getOrgIds())){
            request.setOrgIds(session.getOrgIds());
        }
        List<OrderInsightsVo> insightsVos = orderProfitDao.selectCustomerOrderInsights(session.getCustomerId(),request);
        for (OrderInsightsVo insightsVo : insightsVos) {
            BaseResponse baseResponse = umsFeignClient.storeInfo(insightsVo.getStoreId());
            if (baseResponse != null && baseResponse.getCode() == ResultCode.SUCCESS_CODE){
                StoreVo storeVo = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), StoreVo.class);
                if (storeVo != null){
                    insightsVo.setStoreName(storeVo.getStoreName());
                }
            }
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,insightsVos,request);
    }
}
