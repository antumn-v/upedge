package com.upedge.oms.modules.pack.request;

import lombok.Data;

import java.util.List;

@Data
public class PackageTrackingRefreshRequest {

    List<String> trackingNumbers;

    String dateDay;

    boolean statusIsNull = false;
}
