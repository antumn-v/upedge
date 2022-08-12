package com.upedge.thirdparty.fpx.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderPackageGetLabelRequest {

    private String platId;

    private List<String> platIds;
}
