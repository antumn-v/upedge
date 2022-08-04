package com.upedge.oms.modules.pick.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderPickWaveVo {

    Integer pickType;


    List<ShipCompanyPickVo> shipCompanyPickVos = new ArrayList<>();

    @Data
    public static class ShipCompanyPickVo{
        private String company;



        private Integer total;

        List<OrderPickPreviewVo> orderPickPreviewVos = new ArrayList<>();
    }
}
