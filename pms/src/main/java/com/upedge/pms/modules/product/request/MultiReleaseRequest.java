package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MultiReleaseRequest {

    @Size(min = 1)
    List<Long> ids;

}
