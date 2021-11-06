package com.upedge.ums.modules.manager.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.request.ManagerCustomerListRequest;
import com.upedge.common.model.user.vo.ManagerCustomerVo;
import com.upedge.ums.modules.manager.entity.CustomerManager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface CustomerManagerDao{

    /**
     * 客户经理的个人客户列表
     * @return
     */
    List<ManagerCustomerVo> selectManagerCustomers(ManagerCustomerListRequest request);

    Long selectManagerCustomersCount(ManagerCustomerListRequest request);

    CustomerManager selectByCustomerId(Long customerId);

    int updateManagerCodeByCustomerIds(@Param("managerCode") String managerCode,
                                       @Param("customerIds") List<Long> customerIds);

    CustomerManager selectByPrimaryKey(CustomerManager record);

    int deleteByPrimaryKey(CustomerManager record);

    int updateByPrimaryKey(CustomerManager record);

    int updateByPrimaryKeySelective(CustomerManager record);

    /**
     * 根据客户ID修改
     * @param record
     * @return
     */
    int updateByCustomerIdSelective(CustomerManager record);

    int insert(CustomerManager record);

    int insertSelective(CustomerManager record);

    int insertByBatch(List<CustomerManager> list);

    List<CustomerManager> select(Page<CustomerManager> record);

    long count(Page<CustomerManager> record);


    int updateRemark(@Param("customerId") Long customerId, @Param("remark") String remark);

    List<ManagerCustomerVo> customerInfoListPage(ManagerCustomerListRequest request);

    Long customerInfoListCount(ManagerCustomerListRequest request);
}
