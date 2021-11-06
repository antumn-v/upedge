package com.upedge.ums.modules.manager.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.request.ManagerCustomerListRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.ums.modules.manager.request.ManagerAssignCustomersRequest;
import com.upedge.ums.modules.manager.service.CustomerManagerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManagerServiceImpl implements CustomerManagerService {
    @Override
    public BaseResponse selectManagerCustomers(ManagerCustomerListRequest request, Session session) {
        return null;
    }

    @Override
    public BaseResponse managerAssignCustomers(ManagerAssignCustomersRequest request, Session session) {
        return null;
    }

    @Override
    public ManagerInfoVo getCustomerManager(Long customerId) {
        return null;
    }

    @Override
    public BaseResponse customerConnectManager(String code, Long customerId) {
        return null;
    }

    @Override
    public CustomerManager selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByCustomerIdSelective(CustomerManager record) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(CustomerManager record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(CustomerManager record) {
        return 0;
    }

    @Override
    public int insert(CustomerManager record) {
        return 0;
    }

    @Override
    public int insertSelective(CustomerManager record) {
        return 0;
    }

    @Override
    public List<CustomerManager> select(Page<CustomerManager> record) {
        return null;
    }

    @Override
    public long count(Page<CustomerManager> record) {
        return 0;
    }

    @Override
    public int updateRemark(Long customerId, String remark) {
        return 0;
    }

    @Override
    public CustomerManager selectByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public BaseResponse customerInfoList(ManagerCustomerListRequest request, Session session) {
        return null;
    }
}
