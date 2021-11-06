package com.upedge.common.model.user.request;

import lombok.Data;

import java.util.List;

@Data
public class CustomerByIdsRequest {

    private List<Long> customerIds;

}
