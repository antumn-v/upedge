package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class OrderPackageExStockRequest {

    @NotBlank
   private String scanNo;
}
