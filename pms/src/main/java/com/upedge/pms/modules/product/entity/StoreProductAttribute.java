package com.upedge.pms.modules.product.entity;

import lombok.Data;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreProductAttribute {

    /**
     *
     */
    private Long id;
    /**
     * woocommerce产品ID
     */
    private String platProductId;
    /**
     * 商品店铺标题
     */
    private String title;
    /**
     * 商品店铺图片
     */
    private String image;
    /**
     * 商品店铺供应商
     */
    private String vendor;
    /**
     * 店铺ID
     */
    private Long storeId;

    private Long orgId;

    private String orgPath;
    /**
     *
     */
    private String adminProductId;
    /**
     * 0:SIB ,1:Ali, 2:其他，3：自定义
     */
    private Integer source;
    /**
     * 0:未关联 1 已关联  2部分关联
     */
    private Integer relateState;
    /**
     * 店铺名称
     */
    private String storeName;
    /**
     * 商品价格
     */
    private String price;
    /**
     * 重名链接
     */
    private String handle;


    private Integer state;
    /**
     *
     */
    private Date createAt;
    /**
     *
     */
    private Date updateAt;
    /**
     * 导入到系统的时间
     */
    private Date importTime;
    /**
     *0:未转换  1:已转换
     */
    private Integer transformState;

    public Long customerId;

    private String customerName;

    private String managerCode;

    public StoreProductAttribute() {


    }

    public Product toProduct(Long managerId){
        if(null == managerId){
            managerId = 0L;
        }
        Product product = new Product();
        product.setProductSku(null);
        product.setProductTitle(this.title);
        product.setProductImage(this.image);
        product.setOriginalId(this.id.toString());
        product.setProductSource(4);
        product.setState(1);
        product.setOriginalTitle(this.title);
        product.setSaiheState(0);
        product.setProductType(1);
        product.setReplaceState(0);
        product.setCateType(0);
        product.setUserId(managerId);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        product.setPriceRange(this.price);
        return product;
    }

    public static void main(String[] args) {
        StoreProductAttribute storeProductAttribute = new StoreProductAttribute();
        Field field = ReflectionUtils.findField(StoreProductAttribute.class,"customerId");
        System.out.println(field);
    }
}
