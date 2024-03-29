package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.Customer;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CustomerAddRequest{

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

    public Customer toCustomer(){
        Customer customer=new Customer();
        customer.setCname(cname);
        customer.setCstatus(cstatus);
        customer.setCreateTime(createTime);
        customer.setCustomerSignupUserId(customerSignupUserId);
        return customer;
    }

}
