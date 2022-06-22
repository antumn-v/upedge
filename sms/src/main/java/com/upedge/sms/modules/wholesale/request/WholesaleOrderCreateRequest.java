package com.upedge.sms.modules.wholesale.request;

import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class WholesaleOrderCreateRequest {


    @Size(min = 1,message = "Select at least one product!")
    List<Long> cartIds;

    @NotNull(message = "Address can't be null")
    WholesaleOrderAddress address;

    @Size(min = 1,message = "Select at least one logistic!")
    List<Integer> logistics;

    @NotNull(message = "Title can't be null")
    String serviceTitle;

    String remark;
}
