package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author author
 */
@Data
public class PackageInfoQueryRequest{

    /**
    * 
    */
    @NotBlank
    private String shipTime;

}
