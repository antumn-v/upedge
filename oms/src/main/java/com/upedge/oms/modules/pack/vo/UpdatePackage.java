package com.upedge.oms.modules.pack.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UpdatePackage {

    private String updateToken;
    private Date shipDate;
    private Date dateStart;
    private Date dateEnd;
    private Integer packageCount=0;

    public UpdatePackage(Integer packageCount) {
        this.packageCount = packageCount;
    }

}
