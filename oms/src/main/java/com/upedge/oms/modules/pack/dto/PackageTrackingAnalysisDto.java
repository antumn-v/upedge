package com.upedge.oms.modules.pack.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class PackageTrackingAnalysisDto {

    private Integer orderSourceId;

    private Long customerId;
    private String destination;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private String shipDateEnd;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private String shipDateStart;
}
