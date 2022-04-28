package com.upedge.thirdparty.ali1688.entity.product;

/**
 * Created by guoxing on 2020/6/15.
 */
public class AlibabaCrossProductInfoResult {

    private ProductInfo productInfo;

    //调用信息
    /**
     * 错误码: 没有该商品的查询权限
     * 错误描述: 没有该商品的查询权限
     * 解决方案: 没有建立铺货关系，在商品页面点击一键铺货或者调用铺货同步接口
     */
    private String message;

    //是否成功
    private Boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }



}
