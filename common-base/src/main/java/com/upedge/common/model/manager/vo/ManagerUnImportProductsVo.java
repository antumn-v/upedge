package com.upedge.common.model.manager.vo;

import lombok.Data;

import java.util.List;

@Data
public class ManagerUnImportProductsVo {

    private String managerCode;

    private List<Long> productIds;
}
