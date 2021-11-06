package com.upedge.oms.modules.fulfillment.vo;

import lombok.Data;

@Data
public class WoocommerceASTracking {

   private String tracking_provider;
   private String tracking_number;
   private String tracking_link;
   private String date_shipped;
   private Integer status_shipped;
   private String tracking_id;

}
