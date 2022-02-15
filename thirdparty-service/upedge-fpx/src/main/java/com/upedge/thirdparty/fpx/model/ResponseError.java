package com.upedge.thirdparty.fpx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseError {
    String errorCode;
    String errorMsg;


}