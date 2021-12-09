package com.upedge.ums.modules.user.request;

import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.ums.modules.user.entity.CustomerIoss;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class CustomerIossAddRequest{


    /**
    * 
    */
    @NotNull
    private String iossNum;


    public CustomerIoss toCustomerIoss(Session session){
        CustomerIoss customerIoss=new CustomerIoss();
        customerIoss.setId(IdGenerate.nextId());
        customerIoss.setCustomerId(session.getCustomerId());
        customerIoss.setIossNum(iossNum);
        customerIoss.setState(1);
        customerIoss.setCreateTime(new Date());
        customerIoss.setUpdateTime(new Date());
        return customerIoss;
    }

}
