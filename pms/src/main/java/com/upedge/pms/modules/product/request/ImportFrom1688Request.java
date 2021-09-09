package com.upedge.pms.modules.product.request;

import lombok.Data;

@Data
public class ImportFrom1688Request {

    String originalProductId;

    String url;

    //1:1688
    Integer sourceType=1;

}
