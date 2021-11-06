package com.upedge.ums.modules.statistics.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;

public interface StatisticsService {
    /**
     * 仪表盘数据统计
     * @param session
     * @return
     */
    BaseResponse dashboardCustomerData(Session session);

    /**
     * 仪表盘财务数据
     * @param session
     * @return
     */
    BaseResponse dashboardFinanceData(Session session);

    /**
     * 客户分析 新店铺 新客户 数据
     * @param session
     * @return
     */
    BaseResponse customerChartsData(Session session);
}
