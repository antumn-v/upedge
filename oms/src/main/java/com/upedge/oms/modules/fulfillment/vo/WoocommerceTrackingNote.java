package com.upedge.oms.modules.fulfillment.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WoocommerceTrackingNote {

    private Integer id;
    private String author;
    private Date date_created;
    private String note;
    private Boolean customer_note = true;
    private Boolean added_by_user;

}
