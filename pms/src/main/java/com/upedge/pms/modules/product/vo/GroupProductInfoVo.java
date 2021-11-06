package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupProductInfoVo {

    /**
     * 商品标题
     */
    private String productTitle;

   private List<GroupVariantInfoVo> groupVariantInfoVoList=new ArrayList<>();
}
