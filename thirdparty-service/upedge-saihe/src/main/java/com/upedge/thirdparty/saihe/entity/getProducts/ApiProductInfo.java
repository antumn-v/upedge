package com.upedge.thirdparty.saihe.entity.getProducts;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by cjq on 2019/1/25.
 */
public class ApiProductInfo {

    String SKU;//系统SKU
    String ClientSKU;//自定义SKU
    String ProductName;//产品英文名
    String FeatureList;//产品特殊，亮点
    String ProductColor;//产品颜色属性
    String ProductSize;//产品尺码属性
    BigDecimal NetWeight;//产品净重(g)
    BigDecimal GrossWeight;//产品毛重(g)
    BigDecimal LastSupplierPrice;//最新供货价(RMB)
    BigDecimal SalePrice;//销售价(USD)
    String ProductDescription;//产品描述
    String PageTitle;//B2C页面Title
    String MateDescription;//B2C页面关键词描述
    String MateKeyword;//B2C页面关键词
    String SearchKeyword;//搜索关键词
    String DeclarationName;//产品报关名
    String DeclarationNameCN;//产品报关中文名
    String DeclarationMaterial;//产品报关材质
    BigDecimal DeclarationPriceRate;//产品报关价(USD)
    String PackingList;//包装清单
    String OnlineStatus;//上架状态
    BigDecimal LastBuyPrice	;//最新的采购价(RMB)
    BigDecimal UnitShipFee;//单价采购运费(RMB)
    BigDecimal Length;//产品长度(cm)
    BigDecimal Width;//产品宽度(cm)
    BigDecimal Height;//产品高度(cm)
    BigDecimal Pack_Length;//包装后长度(cm)
    BigDecimal Pack_Width;//包装后宽度(cm)
    BigDecimal Pack_Height;//包装后高度(cm)
    String ProductProperty;//产品侵权风险
    String WithBattery;//产品物流属性
    Integer GoodNum;//库存数量
    Date UpdateTime;//更新时间
    Date UpdateImageDateTime;//产品图片更新时间
    String ProductGroupSKU;//母体ID
    String FullClassNameEn;//完整英文分类
    Integer ClassID1;//一级分类ID
    Integer ClassID2;//二级分类ID
    Integer LastClassID	;//最后一级分类ID
    List<ApiProductImage> ImageList	;//产品图片列表
    String ProductNameCN;//产品中文名
    Date OnlineTime;//上架时间
    Date AddTime;//添加时间
    String DevelopAdminName	;//开发人员
    String BuyerName;//采购人员
    String SupplierName	;//供应商
    String SmallImageUrl;//SKU封面缩略图
    String ComeSource;//产品来源
    String DevelopType;//开发类型
    String PictureSource;//图片来源
    BigDecimal Carton_Length;//外箱长
    BigDecimal Carton_Width;//外箱宽
    BigDecimal Carton_Height;//外箱高
    Integer Carton_PcsNum;//每箱PCS数量
    BigDecimal Carton_GrossWeight;//整箱产品毛重
    BigDecimal Carton_NetWeight	;//整箱产品净重
    String ProductState;//产品状态

    @XmlElement(name="SKU")
    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    @XmlElement(name="ClientSKU")
    public String getClientSKU() {
        return ClientSKU;
    }

    public void setClientSKU(String clientSKU) {
        ClientSKU = clientSKU;
    }

    @XmlElement(name="ProductName")
    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    @XmlElement(name="FeatureList")
    public String getFeatureList() {
        return FeatureList;
    }

    public void setFeatureList(String featureList) {
        FeatureList = featureList;
    }

    @XmlElement(name="ProductColor")
    public String getProductColor() {
        return ProductColor;
    }

    public void setProductColor(String productColor) {
        ProductColor = productColor;
    }

    @XmlElement(name="ProductSize")
    public String getProductSize() {
        return ProductSize;
    }

    public void setProductSize(String productSize) {
        ProductSize = productSize;
    }

    @XmlElement(name="NetWeight")
    public BigDecimal getNetWeight() {
        return NetWeight;
    }

    public void setNetWeight(BigDecimal netWeight) {
        NetWeight = netWeight;
    }

    @XmlElement(name="GrossWeight")
    public BigDecimal getGrossWeight() {
        return GrossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        GrossWeight = grossWeight;
    }

    @XmlElement(name="LastSupplierPrice")
    public BigDecimal getLastSupplierPrice() {
        return LastSupplierPrice;
    }

    public void setLastSupplierPrice(BigDecimal lastSupplierPrice) {
        LastSupplierPrice = lastSupplierPrice;
    }

    @XmlElement(name="SalePrice")
    public BigDecimal getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        SalePrice = salePrice;
    }

    @XmlElement(name="ProductDescription")
    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    @XmlElement(name="PageTitle")
    public String getPageTitle() {
        return PageTitle;
    }

    public void setPageTitle(String pageTitle) {
        PageTitle = pageTitle;
    }

    @XmlElement(name="MateDescription")
    public String getMateDescription() {
        return MateDescription;
    }

    public void setMateDescription(String mateDescription) {
        MateDescription = mateDescription;
    }

    @XmlElement(name="MateKeyword")
    public String getMateKeyword() {
        return MateKeyword;
    }

    public void setMateKeyword(String mateKeyword) {
        MateKeyword = mateKeyword;
    }

    @XmlElement(name="SearchKeyword")
    public String getSearchKeyword() {
        return SearchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        SearchKeyword = searchKeyword;
    }

    @XmlElement(name="DeclarationName")
    public String getDeclarationName() {
        return DeclarationName;
    }

    public void setDeclarationName(String declarationName) {
        DeclarationName = declarationName;
    }

    @XmlElement(name="DeclarationNameCN")
    public String getDeclarationNameCN() {
        return DeclarationNameCN;
    }

    public void setDeclarationNameCN(String declarationNameCN) {
        DeclarationNameCN = declarationNameCN;
    }

    @XmlElement(name="DeclarationMaterial")
    public String getDeclarationMaterial() {
        return DeclarationMaterial;
    }

    public void setDeclarationMaterial(String declarationMaterial) {
        DeclarationMaterial = declarationMaterial;
    }

    @XmlElement(name="DeclarationPriceRate")
    public BigDecimal getDeclarationPriceRate() {
        return DeclarationPriceRate;
    }

    public void setDeclarationPriceRate(BigDecimal declarationPriceRate) {
        DeclarationPriceRate = declarationPriceRate;
    }

    @XmlElement(name="PackingList")
    public String getPackingList() {
        return PackingList;
    }

    public void setPackingList(String packingList) {
        PackingList = packingList;
    }

    @XmlElement(name="OnlineStatus")
    public String getOnlineStatus() {
        return OnlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        OnlineStatus = onlineStatus;
    }

    @XmlElement(name="LastBuyPrice")
    public BigDecimal getLastBuyPrice() {
        return LastBuyPrice;
    }

    public void setLastBuyPrice(BigDecimal lastBuyPrice) {
        LastBuyPrice = lastBuyPrice;
    }

    @XmlElement(name="UnitShipFee")
    public BigDecimal getUnitShipFee() {
        return UnitShipFee;
    }

    public void setUnitShipFee(BigDecimal unitShipFee) {
        UnitShipFee = unitShipFee;
    }

    @XmlElement(name="Length")
    public BigDecimal getLength() {
        return Length;
    }

    public void setLength(BigDecimal length) {
        Length = length;
    }

    @XmlElement(name="Width")
    public BigDecimal getWidth() {
        return Width;
    }

    public void setWidth(BigDecimal width) {
        Width = width;
    }

    @XmlElement(name="Height")
    public BigDecimal getHeight() {
        return Height;
    }

    public void setHeight(BigDecimal height) {
        Height = height;
    }

    @XmlElement(name="Pack_Length")
    public BigDecimal getPack_Length() {
        return Pack_Length;
    }

    public void setPack_Length(BigDecimal pack_Length) {
        Pack_Length = pack_Length;
    }

    @XmlElement(name="Pack_Width")
    public BigDecimal getPack_Width() {
        return Pack_Width;
    }

    public void setPack_Width(BigDecimal pack_Width) {
        Pack_Width = pack_Width;
    }

    @XmlElement(name="Pack_Height")
    public BigDecimal getPack_Height() {
        return Pack_Height;
    }

    public void setPack_Height(BigDecimal pack_Height) {
        Pack_Height = pack_Height;
    }

    @XmlElement(name="ProductProperty")
    public String getProductProperty() {
        return ProductProperty;
    }

    public void setProductProperty(String productProperty) {
        ProductProperty = productProperty;
    }

    @XmlElement(name="WithBattery")
    public String getWithBattery() {
        return WithBattery;
    }

    public void setWithBattery(String withBattery) {
        WithBattery = withBattery;
    }

    @XmlElement(name="GoodNum")
    public Integer getGoodNum() {
        return GoodNum;
    }

    public void setGoodNum(Integer goodNum) {
        GoodNum = goodNum;
    }

    @XmlElement(name="UpdateTime")
    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }

    @XmlElement(name="UpdateImageDateTime")
    public Date getUpdateImageDateTime() {
        return UpdateImageDateTime;
    }

    public void setUpdateImageDateTime(Date updateImageDateTime) {
        UpdateImageDateTime = updateImageDateTime;
    }

    @XmlElement(name="ProductGroupSKU")
    public String getProductGroupSKU() {
        return ProductGroupSKU;
    }

    public void setProductGroupSKU(String productGroupSKU) {
        ProductGroupSKU = productGroupSKU;
    }

    @XmlElement(name="FullClassNameEn")
    public String getFullClassNameEn() {
        return FullClassNameEn;
    }

    public void setFullClassNameEn(String fullClassNameEn) {
        FullClassNameEn = fullClassNameEn;
    }

    @XmlElement(name="ClassID1")
    public Integer getClassID1() {
        return ClassID1;
    }

    public void setClassID1(Integer classID1) {
        ClassID1 = classID1;
    }

    @XmlElement(name="ClassID2")
    public Integer getClassID2() {
        return ClassID2;
    }

    public void setClassID2(Integer classID2) {
        ClassID2 = classID2;
    }

    @XmlElement(name="LastClassID")
    public Integer getLastClassID() {
        return LastClassID;
    }

    public void setLastClassID(Integer lastClassID) {
        LastClassID = lastClassID;
    }

    @XmlElement(name="ImageList")
    public List<ApiProductImage> getImageList() {
        return ImageList;
    }

    public void setImageList(List<ApiProductImage> imageList) {
        ImageList = imageList;
    }

    @XmlElement(name="ProductNameCN")
    public String getProductNameCN() {
        return ProductNameCN;
    }

    public void setProductNameCN(String productNameCN) {
        ProductNameCN = productNameCN;
    }

    @XmlElement(name="OnlineTime")
    public Date getOnlineTime() {
        return OnlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        OnlineTime = onlineTime;
    }

    @XmlElement(name="AddTime")
    public Date getAddTime() {
        return AddTime;
    }

    public void setAddTime(Date addTime) {
        AddTime = addTime;
    }

    @XmlElement(name="DevelopAdminName")
    public String getDevelopAdminName() {
        return DevelopAdminName;
    }

    public void setDevelopAdminName(String developAdminName) {
        DevelopAdminName = developAdminName;
    }

    @XmlElement(name="BuyerName")
    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    @XmlElement(name="SupplierName")
    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    @XmlElement(name="SmallImageUrl")
    public String getSmallImageUrl() {
        return SmallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        SmallImageUrl = smallImageUrl;
    }

    @XmlElement(name="ComeSource")
    public String getComeSource() {
        return ComeSource;
    }

    public void setComeSource(String comeSource) {
        ComeSource = comeSource;
    }

    @XmlElement(name="DevelopType")
    public String getDevelopType() {
        return DevelopType;
    }

    public void setDevelopType(String developType) {
        DevelopType = developType;
    }

    @XmlElement(name="PictureSource")
    public String getPictureSource() {
        return PictureSource;
    }

    public void setPictureSource(String pictureSource) {
        PictureSource = pictureSource;
    }

    @XmlElement(name="Carton_Length")
    public BigDecimal getCarton_Length() {
        return Carton_Length;
    }

    public void setCarton_Length(BigDecimal carton_Length) {
        Carton_Length = carton_Length;
    }

    @XmlElement(name="Carton_Width")
    public BigDecimal getCarton_Width() {
        return Carton_Width;
    }

    public void setCarton_Width(BigDecimal carton_Width) {
        Carton_Width = carton_Width;
    }

    @XmlElement(name="Carton_Height")
    public BigDecimal getCarton_Height() {
        return Carton_Height;
    }

    public void setCarton_Height(BigDecimal carton_Height) {
        Carton_Height = carton_Height;
    }

    @XmlElement(name="Carton_PcsNum")
    public Integer getCarton_PcsNum() {
        return Carton_PcsNum;
    }

    public void setCarton_PcsNum(Integer carton_PcsNum) {
        Carton_PcsNum = carton_PcsNum;
    }

    @XmlElement(name="Carton_GrossWeight")
    public BigDecimal getCarton_GrossWeight() {
        return Carton_GrossWeight;
    }

    public void setCarton_GrossWeight(BigDecimal carton_GrossWeight) {
        Carton_GrossWeight = carton_GrossWeight;
    }

    @XmlElement(name="Carton_NetWeight")
    public BigDecimal getCarton_NetWeight() {
        return Carton_NetWeight;
    }

    public void setCarton_NetWeight(BigDecimal carton_NetWeight) {
        Carton_NetWeight = carton_NetWeight;
    }

    @XmlElement(name="ProductState")
    public String getProductState() {
        return ProductState;
    }

    public void setProductState(String productState) {
        ProductState = productState;
    }
}
