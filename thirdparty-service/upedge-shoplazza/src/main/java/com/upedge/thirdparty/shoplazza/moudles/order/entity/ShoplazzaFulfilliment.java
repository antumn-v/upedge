package com.upedge.thirdparty.shoplazza.moudles.order.entity;

import lombok.Data;

import java.util.List;
@Data
public class ShoplazzaFulfilliment {

    private String id;
    private String order_id;
    private String status;
    private String created_at;
    private String updated_at;
    private String tracking_company;
    private String tracking_number;
    private String tracking_company_code;
    private List<ShoplazzaOrder.ShoplazzaLineItems> line_items;
}
