package com.upedge.ums.modules.manager.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.request.*;
import com.upedge.ums.modules.manager.service.ManagerInfoService;
import com.upedge.ums.modules.manager.vo.ManagerInfoContainsUserInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerInfoServiceImpl implements ManagerInfoService {
    @Override
    public ManagerInfo selectManagerByCode(String code) {
        return null;
    }

    @Override
    public BaseResponse managerOrderSort(ManagerOrderSortRequest request, Session session) {
        return null;
    }

    @Override
    public BaseResponse managerCustomerOrderSort(ManagerCustomerOrderSortRequest request, Session session) {
        return null;
    }

    @Override
    public BaseResponse managerPackageSort(ManagerPackageSortRequest request, Session session) {
        return null;
    }

    @Override
    public ManagerInfoVo selectSessionManager(String token, Session session) {
        return null;
    }

    @Override
    public ManagerInfo selectByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public ManagerInfoVo managerDetail(String managerCode) {
        return null;
    }

    @Override
    public BaseResponse managerCreate(ManagerCreateRequest request, Session session) throws CustomerException {
        return null;
    }

    @Override
    public List<ManagerInfoVo> selectAllManagerInfos() {
        return null;
    }

    @Override
    public ManagerInfo selectByPrimaryKey(String managerCode) {
        return null;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ManagerInfo record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(ManagerInfo record) throws CustomerException {
        return 0;
    }

    @Override
    public int insert(ManagerInfo record) {
        return 0;
    }

    @Override
    public int insertSelective(ManagerInfo record) {
        return 0;
    }

    @Override
    public List<ManagerInfo> select(Page<ManagerInfo> record) {
        return null;
    }

    @Override
    public long count(Page<ManagerInfo> record) {
        return 0;
    }

    @Override
    public List<ManagerInfo> getManagerList(ManagerInfo managerInfo) {
        return null;
    }

    @Override
    public String getManagerByOrderSourceId(Long orderSourceId) {
        return null;
    }

    @Override
    public void updateUserInfo(ManagerInfoUpdateRequest entity) {

    }

    @Override
    public List<ManagerInfoContainsUserInfoVo> selectContainsUserInfoPage(Page<ManagerInfo> record) {
        return null;
    }
}
