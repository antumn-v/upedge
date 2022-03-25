package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.Customer;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class CustomerUpdateRequest{

    /**
     * 
     */
    private String cname;
    /**
     * 0 正常 1 未激活 2 锁定 3注销
     */
    private Integer cstatus;
    /**
     * 
     */
    private Date createTime;
    /**
     * 客户注册后创建的默认用户ID
     */
    private Long customerSignupUserId;

    private String remark;

    public Customer toCustomer(Long id){
        Customer customer=new Customer();
        customer.setId(id);
        customer.setCname(cname);
        customer.setCstatus(cstatus);
        customer.setCreateTime(createTime);
        customer.setCustomerSignupUserId(customerSignupUserId);
        customer.setRemark(remark);
        return customer;
    }

}
