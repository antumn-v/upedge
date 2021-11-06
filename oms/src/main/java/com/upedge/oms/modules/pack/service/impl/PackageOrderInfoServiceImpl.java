package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.vo.ManagerPackageOrderCountVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.pack.dao.PackageOrderInfoDao;
import com.upedge.oms.modules.pack.entity.PackageOrderInfo;
import com.upedge.oms.modules.pack.request.OrderHandleTimeRequest;
import com.upedge.oms.modules.pack.request.PackageOrderInfoListRequest;
import com.upedge.oms.modules.pack.response.PackageOrderInfoListResponse;
import com.upedge.oms.modules.pack.service.PackageOrderInfoService;
import com.upedge.oms.modules.pack.vo.DailyOrderHandleVo;
import com.upedge.oms.modules.pack.vo.OrderHandleTimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class PackageOrderInfoServiceImpl implements PackageOrderInfoService {

    @Autowired
    private PackageOrderInfoDao packageOrderInfoDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseResponse orderHandleTimeCount(OrderHandleTimeRequest request, Session session) {

        String typeField = "handle_time";
        switch (request.getHandleType()){
            case 0:
                typeField = "handle_time";
                break;
            case 1:
                typeField = "pay_package_duration";
                break;
            case 2:
                typeField = "package_ship_duration";
                break;
            default:
                break;
        }
        request.setTypeField(typeField);
        OrderHandleTimeVo orderHandleTimeVo = new OrderHandleTimeVo();
        DailyOrderHandleVo dailyOrderHandleVo = packageOrderInfoDao.selectMaxHandleTimeAndOrderCountByDate(request);
        if (null == dailyOrderHandleVo){
            return null;
        }
        long totalCount = dailyOrderHandleVo.getOrderCount();
        request.setMaxHandleDay(dailyOrderHandleVo.getDay());
        orderHandleTimeVo.setAvgHandleTime(dailyOrderHandleVo.getTotalHandleTime(),dailyOrderHandleVo.getOrderCount());
        List<DailyOrderHandleVo> dailyOrderHandleVos = packageOrderInfoDao.countDailyHandleOrder(request);
        for (DailyOrderHandleVo orderHandleVo : dailyOrderHandleVos) {
            orderHandleVo.setOrderHandlePercentage(new BigDecimal(100*orderHandleVo.getOrderCount() /(totalCount*1.0)).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        orderHandleTimeVo.setDailyOrderHandleVos(dailyOrderHandleVos);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,orderHandleTimeVo,request);
    }

    @Override
    public List<ManagerPackageOrderCountVo> selectManagerPackageOrderCountByDate(ManagerPackageStatisticsRequest request) {
        return packageOrderInfoDao.selectManagerPackageOrderCountByDate(request);
    }

    /**
     * 包裹订单列表
     * @param request
     * @return
     */
    @Override
    public PackageOrderInfoListResponse adminList(PackageOrderInfoListRequest request) {
        request.initFromNum();
        List<PackageOrderInfo> results = packageOrderInfoDao.select(request);
        Long total = packageOrderInfoDao.count(request);
        request.setTotal(total);
        return new PackageOrderInfoListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    @Override
    public PackageOrderInfo selectPackageOrderInfoByClientOrderCode(Long orderId) {

        return packageOrderInfoDao.selectPackageOrderInfoByClientOrderCode(orderId.toString());
    }

}