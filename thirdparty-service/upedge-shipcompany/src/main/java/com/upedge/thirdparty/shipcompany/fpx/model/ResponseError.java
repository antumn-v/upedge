package com.upedge.thirdparty.shipcompany.fpx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseError {
    String errorCode;
    String errorMsg;


}