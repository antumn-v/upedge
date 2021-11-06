package com.upedge.pms.modules.product.vo;

public class AliexpressProductImg {

    String imgUrl;
    Integer state;

    public AliexpressProductImg(String imgUrl) {
        this.imgUrl = imgUrl;
        this.state=0;
    }

    public AliexpressProductImg() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
