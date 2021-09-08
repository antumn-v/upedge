package com.upedge.thirdparty.saihe.entity.getOrderByCode;

import java.math.BigDecimal;

/**
 * Created by cjq on 2019/1/14.
 */
public class ApiOrderList {
    //	订单产品SKU
    String SKU;
    //	订单产品自定义SKU
    String ClientSKU;
    //	订单产品数量
    Integer ProductNum;
    //订单产品价格
    BigDecimal ProductPrice;
    //订单产品运费收入
    BigDecimal ShippingPrice;
    //渠道SKU
    String SellerSKU;
    //平台 ItemID
    String OrderItemId;
    //平台 Item SKU 如亚马逊的 ASIN
    String ASIN;
    //规格参数
    String ParameterValues;
    //	组合产品SKU
    String GroupSKU;
    //	组合产品自定义SKU
    String GroupClientSKU;
    //	组合产品数量
    Integer GroupProductNum;
    //	组合产品售价
    BigDecimal GroupProductPrice;
    //	是否生成包裹
    Boolean IsBuildPackage;
    //	订单产品重量
    BigDecimal ProductWeight;
    //	销售人员ID
    Integer BusinessAdminID;
    //	销售人员中文名
    String BusinessAdminName;
    //	销售人员英文名
    String BusinessAdminNameEn	;
    //	开发人员ID
    Integer DevelopAdminID;
    //	开发人员英文名
    String DevelopAdminNameEn;
    //	开发人员中文名
    String DevelopAdminName;
    //	产品链接
    String ProductLinks;
    //	头程运费(￥)
    BigDecimal FirstLegFee;
    //	进口关税(￥)
    BigDecimal TariffFee;
    //	最新采购价(￥)
    BigDecimal LastBuyPrice;
    //	供应商报价(￥)
//    BigDecimal LastSupplierPrice;
    //	产品长度(cm)
    BigDecimal ProductLength;
    //	产品宽度(cm)
    BigDecimal ProductWidth;
    //	产品高度(cm)
    BigDecimal ProductHeight;
    //	产品导入的最新成本
    BigDecimal ProductLatestCost;
    //	产品最新供货价
    BigDecimal LastSupplierPrice;
    //产品净重(g)
    BigDecimal NetWeight;
    //	订单产品税费
    BigDecimal ItemTax;

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getClientSKU() {
        return ClientSKU;
    }

    public void setClientSKU(String clientSKU) {
        ClientSKU = clientSKU;
    }

    public Integer getProductNum() {
        return ProductNum;
    }

    public void setProductNum(Integer productNum) {
        ProductNum = productNum;
    }

    public BigDecimal getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        ProductPrice = productPrice;
    }

    public BigDecimal getShippingPrice() {
        return ShippingPrice;
    }

    public void setShippingPrice(BigDecimal shippingPrice) {
        ShippingPrice = shippingPrice;
    }

    public String getSellerSKU() {
        return SellerSKU;
    }

    public void setSellerSKU(String sellerSKU) {
        SellerSKU = sellerSKU;
    }

    public String getOrderItemId() {
        return OrderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        OrderItemId = orderItemId;
    }

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    public String getParameterValues() {
        return ParameterValues;
    }

    public void setParameterValues(String parameterValues) {
        ParameterValues = parameterValues;
    }

    public String getGroupSKU() {
        return GroupSKU;
    }

    public void setGroupSKU(String groupSKU) {
        GroupSKU = groupSKU;
    }

    public String getGroupClientSKU() {
        return GroupClientSKU;
    }

    public void setGroupClientSKU(String groupClientSKU) {
        GroupClientSKU = groupClientSKU;
    }

    public Integer getGroupProductNum() {
        return GroupProductNum;
    }

    public void setGroupProductNum(Integer groupProductNum) {
        GroupProductNum = groupProductNum;
    }

    public BigDecimal getGroupProductPrice() {
        return GroupProductPrice;
    }

    public void setGroupProductPrice(BigDecimal groupProductPrice) {
        GroupProductPrice = groupProductPrice;
    }

    public Boolean getBuildPackage() {
        return IsBuildPackage;
    }

    public void setBuildPackage(Boolean buildPackage) {
        IsBuildPackage = buildPackage;
    }

    public BigDecimal getProductWeight() {
        return ProductWeight;
    }

    public void setProductWeight(BigDecimal productWeight) {
        ProductWeight = productWeight;
    }

    public Integer getBusinessAdminID() {
        return BusinessAdminID;
    }

    public void setBusinessAdminID(Integer businessAdminID) {
        BusinessAdminID = businessAdminID;
    }

    public String getBusinessAdminName() {
        return BusinessAdminName;
    }

    public void setBusinessAdminName(String businessAdminName) {
        BusinessAdminName = businessAdminName;
    }

    public String getBusinessAdminNameEn() {
        return BusinessAdminNameEn;
    }

    public void setBusinessAdminNameEn(String businessAdminNameEn) {
        BusinessAdminNameEn = businessAdminNameEn;
    }

    public Integer getDevelopAdminID() {
        return DevelopAdminID;
    }

    public void setDevelopAdminID(Integer developAdminID) {
        DevelopAdminID = developAdminID;
    }

    public String getDevelopAdminNameEn() {
        return DevelopAdminNameEn;
    }

    public void setDevelopAdminNameEn(String developAdminNameEn) {
        DevelopAdminNameEn = developAdminNameEn;
    }

    public String getDevelopAdminName() {
        return DevelopAdminName;
    }

    public void setDevelopAdminName(String developAdminName) {
        DevelopAdminName = developAdminName;
    }

    public String getProductLinks() {
        return ProductLinks;
    }

    public void setProductLinks(String productLinks) {
        ProductLinks = productLinks;
    }

    public BigDecimal getFirstLegFee() {
        return FirstLegFee;
    }

    public void setFirstLegFee(BigDecimal firstLegFee) {
        FirstLegFee = firstLegFee;
    }

    public BigDecimal getTariffFee() {
        return TariffFee;
    }

    public void setTariffFee(BigDecimal tariffFee) {
        TariffFee = tariffFee;
    }

    public BigDecimal getLastBuyPrice() {
        return LastBuyPrice;
    }

    public void setLastBuyPrice(BigDecimal lastBuyPrice) {
        LastBuyPrice = lastBuyPrice;
    }

    public BigDecimal getProductLength() {
        return ProductLength;
    }

    public void setProductLength(BigDecimal productLength) {
        ProductLength = productLength;
    }

    public BigDecimal getProductWidth() {
        return ProductWidth;
    }

    public void setProductWidth(BigDecimal productWidth) {
        ProductWidth = productWidth;
    }

    public BigDecimal getProductHeight() {
        return ProductHeight;
    }

    public void setProductHeight(BigDecimal productHeight) {
        ProductHeight = productHeight;
    }

    public BigDecimal getProductLatestCost() {
        return ProductLatestCost;
    }

    public void setProductLatestCost(BigDecimal productLatestCost) {
        ProductLatestCost = productLatestCost;
    }

    public BigDecimal getLastSupplierPrice() {
        return LastSupplierPrice;
    }

    public void setLastSupplierPrice(BigDecimal lastSupplierPrice) {
        LastSupplierPrice = lastSupplierPrice;
    }

    public BigDecimal getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        NetWeight = netWeight;
    }

    public BigDecimal getItemTax() {
        return ItemTax;
    }

    public void setItemTax(BigDecimal itemTax) {
        ItemTax = itemTax;
    }
}
