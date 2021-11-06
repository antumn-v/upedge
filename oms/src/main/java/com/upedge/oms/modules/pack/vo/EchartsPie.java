package com.upedge.oms.modules.pack.vo;

import lombok.Data;

@Data
public class EchartsPie {

    private String name;
    private Integer value;

    public EchartsPie() {
    }

    public EchartsPie(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
