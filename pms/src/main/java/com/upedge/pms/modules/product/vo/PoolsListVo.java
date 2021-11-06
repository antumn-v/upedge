package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PoolsListVo {
    /**
     *
     */
    private Long id;
    /**
     * 商品sku
     */
    private String productSku;
    /**
     * 标题
     */
    private String productTitle;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品主图
     */
    private List<String> image=new ArrayList<>();

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 产品类别
     */
    private Integer cateType;

    /**
     * 供应商id
     */
    private Long supplierId;

    /**
     * 赛盒上传状态 0:未上传 1:已上传
     */
    private Integer saiheState;

    /**
     * 0:1688 1:个人添加 2:复制产品3:捆绑产品
     */
    private Integer productSource;
    /**
     * 0:公有产品 1:私有产品
     */
    private Integer productType;

    /**
     * 状态
     */
    private Integer state;

    /**
     *运输模板id
     */
    private Long shippingId;

    private String managerCode;
}
