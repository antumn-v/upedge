package com.upedge.ums.modules.statistics.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Applications;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateTools;
import com.upedge.common.web.util.UserUtil;

import com.upedge.ums.modules.account.dao.AccountMapper;

import com.upedge.ums.modules.account.dao.RechargeRequestAttrMapper;

import com.upedge.ums.modules.account.dao.RechargeRequestLogMapper;
import com.upedge.ums.modules.account.entity.RechargeRequestAttr;
import com.upedge.ums.modules.account.vo.AccountStatisticsVo;
import com.upedge.ums.modules.statistics.dto.CustomerCharts;
import com.upedge.ums.modules.statistics.dto.RechargeData;
import com.upedge.ums.modules.statistics.service.StatisticsService;
import com.upedge.ums.modules.store.dao.StoreDao;
import com.upedge.ums.modules.user.dao.CustomerDao;
import com.upedge.ums.modules.user.dao.UserInfoDao;
import com.upedge.ums.modules.user.dto.UserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {


    @Autowired
    CustomerDao customerDao;
    @Autowired
    StoreDao storeDao;
    @Autowired
    RechargeRequestLogMapper rechargeRequestLogDao;
    @Autowired
    AccountMapper accountDao;
    @Autowired
    RechargeRequestAttrMapper rechargeRequestAttrDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    /**
     * 仪表盘数据统计
     * 用户数、授权用户数、近30天充值、客户剩余金额、开放信用额度、已用信用额度
     * @param session
     * @return
     */
    @Override
    public BaseResponse dashboardCustomerData(Session session) {

        return BaseResponse.success();
    }

    /**
     * 仪表盘财务数据
     * @param session
     * @return
     */
    @Override
    public BaseResponse dashboardFinanceData(Session session ) {
        return BaseResponse.success();
    }

    /**
     * 客户分析 新店铺 新客户 数据
     * @param session
     * @return
     */
    @Override
    public BaseResponse customerChartsData(Session session) {
        return BaseResponse.success();
    }

}
