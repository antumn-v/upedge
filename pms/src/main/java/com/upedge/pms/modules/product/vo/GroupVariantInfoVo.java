package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupVariantInfoVo {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String enName;

    private List<GroupVariantInfosVo> variantInfosVoList=new ArrayList<>();
}
