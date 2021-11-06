package com.upedge.pms.modules.product.vo;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class AliexpressProduct {


    String productTitle;
    String mainImg;
    List<AliexpressProductImg> imgList=new ArrayList<>();
    String productDetail;
    List<AliexpressProductVariant> productVariantList=new ArrayList<>();
    HashMap<String, String> attrImgMap=new HashMap<>();
    Set<String> aliAttr1List=new HashSet<>();
    Set<String> aliAttr2List=new HashSet<>();
    Set<String> aliAttr3List=new HashSet<>();

    String attrName1="无";
    String attrName2="无";
    String attrName3="无";

    public String getAttrName1() {
        return attrName1;
    }

    public void setAttrName1(String attrName1) {
        this.attrName1 = attrName1;
    }

    public HashMap<String, String> getAttrImgMap() {
        return attrImgMap;
    }

    public void setAttrImgMap(HashMap<String, String> attrImgMap) {
        this.attrImgMap = attrImgMap;
    }

    public String getAttrName2() {
        return attrName2;
    }

    public void setAttrName2(String attrName2) {
        this.attrName2 = attrName2;
    }

    public String getAttrName3() {
        return attrName3;
    }

    public void setAttrName3(String attrName3) {
        this.attrName3 = attrName3;
    }

    public Set<String> getAliAttr3List() {
        return aliAttr3List;
    }

    public void setAliAttr3List(Set<String> aliAttr3List) {
        this.aliAttr3List = aliAttr3List;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public Set<String> getAliAttr1List() {
        return aliAttr1List;
    }

    public void setAliAttr1List(Set<String> aliAttr1List) {
        this.aliAttr1List = aliAttr1List;
    }

    public Set<String> getAliAttr2List() {
        return aliAttr2List;
    }

    public void setAliAttr2List(Set<String> aliAttr2List) {
        this.aliAttr2List = aliAttr2List;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public List<AliexpressProductImg> getImgList() {
        return imgList;
    }

    public void setImgList(List<AliexpressProductImg> imgList) {
        this.imgList = imgList;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public List<AliexpressProductVariant> getProductVariantList() {
        return productVariantList;
    }

    public void setProductVariantList(List<AliexpressProductVariant> productVariantList) {
        this.productVariantList = productVariantList;
    }

    public void setAliAttrList(List<AliexpressProductVariant> productVariantList){
        for(AliexpressProductVariant variant:productVariantList){
            String a=variant.getaValue();
            String b=variant.getbValue();
            String c=variant.getcValue();
            if(!StringUtils.isBlank(a)) {
                this.getAliAttr1List().add(a);
            }
            if(!StringUtils.isBlank(b)) {
                this.getAliAttr2List().add(b);
            }
            if(!StringUtils.isBlank(c)) {
                this.getAliAttr3List().add(c);
            }
        }
    }

}
