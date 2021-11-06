package com.upedge.ums.modules.manager.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.common.model.user.request.ManagerCustomerListRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import com.upedge.ums.modules.manager.request.ManagerAssignCustomersRequest;

import java.util.List;

/**
 * @author author
 */
public interface CustomerManagerService{

    BaseResponse  selectManagerCustomers(ManagerCustomerListRequest request, Session session);

    BaseResponse managerAssignCustomers(ManagerAssignCustomersRequest request, Session session);

    ManagerInfoVo getCustomerManager(Long customerId);

    BaseResponse customerConnectManager(String code, Long customerId);

    CustomerManager selectByPrimaryKey(Integer id);

    int updateByCustomerIdSelective(CustomerManager record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(CustomerManager record);

    int updateByPrimaryKeySelective(CustomerManager record);

    int insert(CustomerManager record);

    int insertSelective(CustomerManager record);

    List<CustomerManager> select(Page<CustomerManager> record);

    long count(Page<CustomerManager> record);

    int updateRemark(Long customerId, String remark);

    CustomerManager selectByCustomerId(Long customerId);

    /**
     * 客户信息列表
     * @param request
     * @param session
     * @return
     */
    BaseResponse customerInfoList(ManagerCustomerListRequest request, Session session);
}

