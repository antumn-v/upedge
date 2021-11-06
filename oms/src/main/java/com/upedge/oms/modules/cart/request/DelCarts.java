package com.upedge.oms.modules.cart.request;


import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class DelCarts {

    @Size(min = 1)
    private List<Long> cartIds;
}
