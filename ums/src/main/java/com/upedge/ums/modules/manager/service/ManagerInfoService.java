package com.upedge.ums.modules.manager.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.request.*;
import com.upedge.ums.modules.manager.vo.ManagerInfoContainsUserInfoVo;

import java.util.List;

/**
 * @author author
 */
public interface ManagerInfoService{


    /**
     * 根据四字码（最右边）查询客户经理
     * @param code
     * @return
     */
    ManagerInfo selectManagerByCode(String code);

    /**
     * 下单排名
     * @param request
     * @param session
     * @return
     */
    BaseResponse managerOrderSort(ManagerOrderSortRequest request, Session session);

    /**
     * 客户经理客户下单排名
     * @param request
     * @param session
     * @return
     */
    BaseResponse managerCustomerOrderSort(ManagerCustomerOrderSortRequest request, Session session);

    /**
     * 发货排名
     * @param request
     * @param session
     * @return
     */
    BaseResponse managerPackageSort(ManagerPackageSortRequest request, Session session);

    ManagerInfoVo selectSessionManager(String token, Session session);

    ManagerInfo selectByCustomerId(Long customerId);

    ManagerInfoVo managerDetail(String managerCode);

    BaseResponse managerCreate(ManagerCreateRequest request, Session session) throws CustomerException;

    List<ManagerInfoVo> selectAllManagerInfos();

    ManagerInfo selectByPrimaryKey(String managerCode);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ManagerInfo record);

    int updateByPrimaryKeySelective(ManagerInfo record) throws CustomerException;

    int insert(ManagerInfo record);

    int insertSelective(ManagerInfo record);

    List<ManagerInfo> select(Page<ManagerInfo> record);

    long count(Page<ManagerInfo> record);

    /**
     * 获取list
     * @param managerInfo
     * @return
     */
    List<ManagerInfo> getManagerList(ManagerInfo managerInfo);

    /**
     * 根据 orderSourceId 获取客户经理 code
     * @param orderSourceId
     * @return
     */
    String getManagerByOrderSourceId(Long orderSourceId);

    void updateUserInfo(ManagerInfoUpdateRequest entity);

    List<ManagerInfoContainsUserInfoVo> selectContainsUserInfoPage(Page<ManagerInfo> record);
}

