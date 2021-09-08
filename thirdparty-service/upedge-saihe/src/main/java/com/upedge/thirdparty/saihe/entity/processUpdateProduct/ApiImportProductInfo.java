package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * Created by cjq on 2019/1/23.
 */
public class ApiImportProductInfo {

    String SKU;//产品SKU
    String ClientSKU;//	自定义SKU
    String ProductColor;//产品颜色英文
    String ProductSize;//产品尺码
    Integer ComeSource;//产品来源（0系统采集 1 开发采集 2 新样品）
    String ProductClassNameEN;//产品类别英文名
    String ProductClassNameCN;//产品类别中文名
    String ProductName;//产品中文名
    String ProductNameCN;//产品中文名
    String MateDescription;//产品简要描述
    String ProductDescription;//产品详细描述
    BigDecimal Length;	//产品尺寸长（CM）
    BigDecimal Width;//	产品尺寸宽（CM）
    BigDecimal Height;//Decimal	产品尺寸高（CM）
    BigDecimal Pack_Length;//	产品包装尺寸长（CM）
    BigDecimal Pack_Width;//产品包装尺寸宽（CM）
    BigDecimal Pack_Height;//	产品包装尺寸高（CM）
    String PackingList;//产品包装清单
    BigDecimal SalePrice;//	售价（美元）
    BigDecimal LastSupplierPrice;//	供货价(人民币)
    BigDecimal NetWeight;//净重(克)
    BigDecimal GrossWeight;//毛重(克)
    BigDecimal PackWeight;//	单个产品包裹重量(克)
    String FeatureList;//产品特征亮点
    String BuyerName;//采购人员
    Integer DevelopType;//开发类型 (自主开发 = 1,供应商后台 =2,指定开发 = 3)
    Integer PictureSource;//图片来源 (采集图 = 1,供应商提供图 = 2,自己拍照图 = 3)
    BigDecimal Carton_Length;//	外箱长
    BigDecimal Carton_Width;//外箱宽
    BigDecimal Carton_Height;//外箱高
    BigDecimal Carton_PcsNum;//每箱PCS数量
    BigDecimal Carton_GrossWeight;//整箱产品毛重
    BigDecimal Carton_NetWeight;//整箱产品净重
    String ProductState;//产品状态
    String ProductGroupSKU;//母体ID
    String UnitName;//单位
    Integer WithBattery;//产品物流属性
    Integer DefaultLocalWarehouse;//默认本地发货仓库
    String ReceiptRemark;//采购收货备注


    ImagesList ImagesList;//产品图片(最多9张)
    ApiImportProductSupplier ProductSuppiler;//产品供应商
    ApiImportProductSupplierPrice ProductSupplierPrice;//产品供应商报价
    ApiImportProductDeclaration ProductDeclaration;//产品报关信息
    ApiImportProductAdmin ProductAdmin;//产品审核信息

    @XmlElement(name="ReceiptRemark")
    public String getReceiptRemark() {
        return ReceiptRemark;
    }

    public void setReceiptRemark(String receiptRemark) {
        ReceiptRemark = receiptRemark;
    }

    @XmlElement(name="ProductGroupSKU")
    public String getProductGroupSKU() {
        return ProductGroupSKU;
    }

    public void setProductGroupSKU(String productGroupSKU) {
        ProductGroupSKU = productGroupSKU;
    }

    @XmlElement(name="UnitName")
    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    @XmlElement(name="WithBattery")
    public Integer getWithBattery() {
        return WithBattery;
    }

    public void setWithBattery(Integer withBattery) {
        WithBattery = withBattery;
    }

    @XmlElement(name="DefaultLocalWarehouse")
    public Integer getDefaultLocalWarehouse() {
        return DefaultLocalWarehouse;
    }

    public void setDefaultLocalWarehouse(Integer defaultLocalWarehouse) {
        DefaultLocalWarehouse = defaultLocalWarehouse;
    }

    @XmlElement(name="ProductAdmin")
    public ApiImportProductAdmin getProductAdmin() {
        return ProductAdmin;
    }

    public void setProductAdmin(ApiImportProductAdmin productAdmin) {
        ProductAdmin = productAdmin;
    }

    @XmlElement(name="ProductDeclaration")
    public ApiImportProductDeclaration getProductDeclaration() {
        return ProductDeclaration;
    }

    public void setProductDeclaration(ApiImportProductDeclaration productDeclaration) {
        ProductDeclaration = productDeclaration;
    }

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

    @XmlElement(name="ComeSource")
    public Integer getComeSource() {
        return ComeSource;
    }

    public void setComeSource(Integer comeSource) {
        ComeSource = comeSource;
    }

    @XmlElement(name="ProductClassNameEN")
    public String getProductClassNameEN() {
        return ProductClassNameEN;
    }

    public void setProductClassNameEN(String productClassNameEN) {
        ProductClassNameEN = productClassNameEN;
    }

    @XmlElement(name="ProductClassNameCN")
    public String getProductClassNameCN() {
        return ProductClassNameCN;
    }

    public void setProductClassNameCN(String productClassNameCN) {
        ProductClassNameCN = productClassNameCN;
    }

    @XmlElement(name="ProductName")
    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    @XmlElement(name="ProductNameCN")
    public String getProductNameCN() {
        return ProductNameCN;
    }

    public void setProductNameCN(String productNameCN) {
        ProductNameCN = productNameCN;
    }

    @XmlElement(name="MateDescription")
    public String getMateDescription() {
        return MateDescription;
    }

    public void setMateDescription(String mateDescription) {
        MateDescription = mateDescription;
    }

    @XmlElement(name="ProductDescription")
    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
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

    @XmlElement(name="PackingList")
    public String getPackingList() {
        return PackingList;
    }

    public void setPackingList(String packingList) {
        PackingList = packingList;
    }

    @XmlElement(name="SalePrice")
    public BigDecimal getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        SalePrice = salePrice;
    }

    @XmlElement(name="LastSupplierPrice")
    public BigDecimal getLastSupplierPrice() {
        return LastSupplierPrice;
    }

    public void setLastSupplierPrice(BigDecimal lastSupplierPrice) {
        LastSupplierPrice = lastSupplierPrice;
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

    @XmlElement(name="PackWeight")
    public BigDecimal getPackWeight() {
        return PackWeight;
    }

    public void setPackWeight(BigDecimal packWeight) {
        PackWeight = packWeight;
    }

    @XmlElement(name="FeatureList")
    public String getFeatureList() {
        return FeatureList;
    }

    public void setFeatureList(String featureList) {
        FeatureList = featureList;
    }

    @XmlElement(name="BuyerName")
    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    @XmlElement(name="DevelopType")
    public Integer getDevelopType() {
        return DevelopType;
    }


    public void setDevelopType(Integer developType) {
        DevelopType = developType;
    }

    @XmlElement(name="PictureSource")
    public Integer getPictureSource() {
        return PictureSource;
    }

    public void setPictureSource(Integer pictureSource) {
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
    public BigDecimal getCarton_PcsNum() {
        return Carton_PcsNum;
    }

    public void setCarton_PcsNum(BigDecimal carton_PcsNum) {
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

    @XmlElement(name="ImagesList")
    public ImagesList getImagesList() {
        return ImagesList;
    }

    public void setImagesList(ImagesList imagesList) {
        ImagesList = imagesList;
    }

    @XmlElement(name="ProductSuppiler")
    public ApiImportProductSupplier getProductSuppiler() {
        return ProductSuppiler;
    }

    public void setProductSuppiler(ApiImportProductSupplier productSuppiler) {
        ProductSuppiler = productSuppiler;
    }

    @XmlElement(name="ProductSupplierPrice")
    public ApiImportProductSupplierPrice getProductSupplierPrice() {
        return ProductSupplierPrice;
    }

    public void setProductSupplierPrice(ApiImportProductSupplierPrice productSupplierPrice) {
        ProductSupplierPrice = productSupplierPrice;
    }
}
