package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.CustomerIoss;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class CustomerIossUpdateRequest{

    @NotNull
    private String iossNum;



    public CustomerIoss toCustomerIoss(Long id){
        CustomerIoss customerIoss=new CustomerIoss();
        customerIoss.setId(id);
        customerIoss.setIossNum(iossNum);
        customerIoss.setUpdateTime(new Date());
        return customerIoss;
    }

}
