package com.upedge.pms.modules.product.vo;

import com.upedge.common.utils.excel.ExcelField;

import java.util.ArrayList;
import java.util.List;

public class SaiheSkuVo {

    private Long userId;

    private Long shippingId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    private Integer cateType;

    public Integer getCateType() {
        return cateType;
    }

    public void setCateType(Integer cateType) {
        this.cateType = cateType;
    }

    private String productId;
    private String weight;


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    //产品SKU
    private String productSku;

    // 自定义SKU
    private String customerSku;
    // 产品颜色英文
    private String color;
    // 产品尺码英文
    private String size;
    // 母体变体
    private String parentSku;
    // 产品来源
    private String source="系统采集";
    // 开发类型
    private String developType;
    // 图片来源
    private String imageSource;
    // 产品英文类别名
    private String categoryEnName="upedge";
    // 产品中文类别名
    private String categoryCnName="潘达产品";
    // 产品中文名
    private String productName;
    // 产品报关英文名
    private String productEntryEnName;
    // 产品报关中文名
    private String productEntryCnName;
    // 报关价
    private String entryPrice;
    // 报关单位
    private String entryUnit="USD";
    // 每箱PCS数量
    private String pcsPerBox;
    // 产品尺寸长（CM）
    private String sizeLength;
    // 产品尺寸宽（CM）
    private String sizeWidth;
    // 产品尺寸高（CM）
    private String sizeHeight;
    // 产品包装尺寸长（CM）
    private String packageSizeLength;
    // 产品包装尺寸宽（CM）
    private String packageSizeWidth;
    // 产品包装尺寸高（CM）
    private String packageSizeHeight;
    // 外箱包装尺寸长（M）
    private String outBagLength;
    // 外箱包装尺寸宽（M）
    private String outBagWidth;
    // 外箱包装尺寸高（M）
    private String outBagHeight;
    // 供货价(人民币)
    private String supplyPrice;
    // 供应商名称
    private String supplierName;
    // 供应商类型
    private String supplierType="网络采购";
    // 网络采购链接
    private String purchaseLink;
    // 生产天数
    private String produceDays;
    // 采购运输天数
    private String purchaseShippingDays;
    // 入库天数
    private String stockingDays;
    // 加工费用
    private String processingCost;
    // 其他费用
    private String otherCost;
    // 采购运费
    private String purchaseShippingCost;
    // 单位
    private String unit="Piece";
    // 单位数量
    private String unitNumber;
    // 净重(克)
    private String netWeight;
    // 毛重(克)
    private String grossWeight;
    // 单个产品包裹重量(克)
    private String perProductPackWeight;
    // 产品侵权风险
    private String infringement;
    // 产品物流属性
    private Integer shippingAttribute;
    // 产品状态
    private String productState="正常";
    // 采购人员
    private String purchaseUser="潘达";
    // 负责人员
    private String chargeUser;
    // 编辑人员
    private String editorUser;
    // 图片处理人员
    private String imageHandleUser;
    // 默认本地发货仓库
    private String warehouse="邮e邦发货仓";
    // 开发人员
    private String developUser;
    // 产品英文名
    private String productEnName;
    // 报关材质
    private String entryMaterial;
    // 退税率
    private String refundTaxRate;
    // 海关编码
    private String customsCode;
    // 是否审核海关编码
    private String verifyCustomsCode;
    // 产品关键词
    private String keywords;
    // 产品关键词1
    private String keywords1;
    // 产品关键词2
    private String keywords2;
    // 产品关键词3
    private String keywords3;
    // 产品关键词4
    private String keywords4;
    // 产品关键词5
    private String keywords5;
    // 产品简要描述
    private String productShortDesc;
    // 产品特征1
    private String feature1;
    // 产品特征2
    private String feature2;
    // 产品特征3
    private String feature3;
    // 产品特征4
    private String feature4;
    // 产品特征5
    private String feature5;
    // 产品描述
    private String productDesc;
    // 参考网站链接
    private String websiteLink;
    // 产品包装清单
    private String packChecklist;
    // 品牌名
    private String brandName;
    // 售价（美元）
    private String salePrice;
    // 供应商产品编码
    private String supplierCode;
    // 产品主图
    private String mainImage;
    // 产品其他图片1
    private String image1;
    // 产品其他图片2
    private String image2;
    // 产品其他图片3
    private String image3;
    // 产品其他图片4
    private String image4;
    // 产品其他图片5
    private String image5;
    // 产品其他图片6
    private String image6;
    // 产品其他图片7
    private String image7;
    // 产品其他图片8
    private String image8;
    // 产品其他图片9
    private String image9;
    // 强电插头
    private String strongElePlug;
    // 插头规格
    private String plugStandard;
    // 插头规格作为产品标题
    private String plugStandardAsTitle;
    // 产品用途
    private String productUsage;
    // 开发备注
    private String developRemarks;
    // 编辑备注
    private String editRemarks;
    // 质检备注
    private String qualityCheckRemarks;
    // 采购收货备注
    private String purchaseReceiveRemarks;
    // 默认供货批发量
    private String wholesaleNum;
    // 规格型号
    private String specificationModel;
    // 产品类型
    private String productType;
    //赛盒仓库ID
    private Integer wareHouseId;
    //采购收货备注
    private String receiptRemark;
    //质检备注
    private String procurementCheckMemo;
    //发货打包备注
    private String deliveryPackNote;

    private List<SaiheSkuAttrVo> attrList=new ArrayList<>();

    public List<SaiheSkuAttrVo> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<SaiheSkuAttrVo> attrList) {
        this.attrList = attrList;
    }

    @ExcelField(title="产品SKU",align= ExcelField.Align.CENTER,sort = 10)
    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    @ExcelField(title="自定义SKU",align= ExcelField.Align.CENTER,sort = 20)
    public String getCustomerSku() {
        return customerSku;
    }

    public void setCustomerSku(String customerSku) {
        this.customerSku = customerSku;
    }

    @ExcelField(title="产品颜色英文",align= ExcelField.Align.CENTER,sort = 30)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @ExcelField(title="产品尺码英文",align= ExcelField.Align.CENTER,sort = 40)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @ExcelField(title="母体变体",align= ExcelField.Align.CENTER,sort = 50)
    public String getParentSku() {
        return parentSku;
    }

    public void setParentSku(String parentSku) {
        this.parentSku = parentSku;
    }

    @ExcelField(title="产品来源",align= ExcelField.Align.CENTER,sort = 60)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @ExcelField(title="开发类型",align= ExcelField.Align.CENTER,sort = 70)
    public String getDevelopType() {
        return developType;
    }

    public void setDevelopType(String developType) {
        this.developType = developType;
    }

    @ExcelField(title="图片来源",align= ExcelField.Align.CENTER,sort = 80)
    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    @ExcelField(title="产品英文类别名",align= ExcelField.Align.CENTER,sort = 90)
    public String getCategoryEnName() {
        return categoryEnName;
    }

    public void setCategoryEnName(String categoryEnName) {
        this.categoryEnName = categoryEnName;
    }

    @ExcelField(title="产品中文类别名",align= ExcelField.Align.CENTER,sort = 100)
    public String getCategoryCnName() {
        return categoryCnName;
    }

    public void setCategoryCnName(String categoryCnName) {
        this.categoryCnName = categoryCnName;
    }

    @ExcelField(title="产品中文名",align= ExcelField.Align.CENTER,sort = 110)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @ExcelField(title="产品报关英文名",align= ExcelField.Align.CENTER,sort = 120)
    public String getProductEntryEnName() {
        return productEntryEnName;
    }

    public void setProductEntryEnName(String productEntryEnName) {
        this.productEntryEnName = productEntryEnName;
    }

    @ExcelField(title="产品报关中文名",align= ExcelField.Align.CENTER,sort = 130)
    public String getProductEntryCnName() {
        return productEntryCnName;
    }

    public void setProductEntryCnName(String productEntryCnName) {
        this.productEntryCnName = productEntryCnName;
    }

    @ExcelField(title="报关价",align= ExcelField.Align.CENTER,sort = 140)
    public String getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(String entryPrice) {
        this.entryPrice = entryPrice;
    }

    @ExcelField(title="报关单位",align= ExcelField.Align.CENTER,sort = 150)
    public String getEntryUnit() {
        return entryUnit;
    }

    public void setEntryUnit(String entryUnit) {
        this.entryUnit = entryUnit;
    }

    @ExcelField(title="每箱PCS数量",align= ExcelField.Align.CENTER,sort = 160)
    public String getPcsPerBox() {
        return pcsPerBox;
    }

    public void setPcsPerBox(String pcsPerBox) {
        this.pcsPerBox = pcsPerBox;
    }

    @ExcelField(title="产品尺寸长（CM）",align= ExcelField.Align.CENTER,sort = 170)
    public String getSizeLength() {
        return sizeLength;
    }

    public void setSizeLength(String sizeLength) {
        this.sizeLength = sizeLength;
    }

    @ExcelField(title="产品尺寸宽（CM）",align= ExcelField.Align.CENTER,sort = 180)
    public String getSizeWidth() {
        return sizeWidth;
    }

    public void setSizeWidth(String sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    @ExcelField(title="产品尺寸高（CM）",align= ExcelField.Align.CENTER,sort = 190)
    public String getSizeHeight() {
        return sizeHeight;
    }

    public void setSizeHeight(String sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    @ExcelField(title="产品包装尺寸长（CM）",align= ExcelField.Align.CENTER,sort = 200)
    public String getPackageSizeLength() {
        return packageSizeLength;
    }

    public void setPackageSizeLength(String packageSizeLength) {
        this.packageSizeLength = packageSizeLength;
    }

    @ExcelField(title="产品包装尺寸宽（CM）",align= ExcelField.Align.CENTER,sort = 210)
    public String getPackageSizeWidth() {
        return packageSizeWidth;
    }

    public void setPackageSizeWidth(String packageSizeWidth) {
        this.packageSizeWidth = packageSizeWidth;
    }

    @ExcelField(title="产品包装尺寸高（CM）",align= ExcelField.Align.CENTER,sort = 220)
    public String getPackageSizeHeight() {
        return packageSizeHeight;
    }

    public void setPackageSizeHeight(String packageSizeHeight) {
        this.packageSizeHeight = packageSizeHeight;
    }

    @ExcelField(title="外箱包装尺寸长（M）",align= ExcelField.Align.CENTER,sort = 230)
    public String getOutBagLength() {
        return outBagLength;
    }

    public void setOutBagLength(String outBagLength) {
        this.outBagLength = outBagLength;
    }

    @ExcelField(title="外箱包装尺寸宽（M）",align= ExcelField.Align.CENTER,sort = 240)
    public String getOutBagWidth() {
        return outBagWidth;
    }

    public void setOutBagWidth(String outBagWidth) {
        this.outBagWidth = outBagWidth;
    }

    @ExcelField(title="外箱包装尺寸高（M）",align= ExcelField.Align.CENTER,sort = 250)
    public String getOutBagHeight() {
        return outBagHeight;
    }

    public void setOutBagHeight(String outBagHeight) {
        this.outBagHeight = outBagHeight;
    }

    @ExcelField(title="供货价(人民币)",align= ExcelField.Align.CENTER,sort = 260)
    public String getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    @ExcelField(title="供应商名称",align= ExcelField.Align.CENTER,sort = 270)
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @ExcelField(title="供应商类型",align= ExcelField.Align.CENTER,sort = 280)
    public String getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(String supplierType) {
        this.supplierType = supplierType;
    }

    @ExcelField(title="网络采购链接",align= ExcelField.Align.CENTER,sort = 290)
    public String getPurchaseLink() {
        String prefix="https://detail.1688.com/offer/";
        if(purchaseLink!=null){
            if(purchaseLink.contains("-")) {
                purchaseLink=purchaseLink.substring(0, purchaseLink.indexOf("-"));
            }
            purchaseLink=prefix+purchaseLink+".html";
        }
        return purchaseLink;
    }

    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
    }

    @ExcelField(title="生产天数",align= ExcelField.Align.CENTER,sort = 300)
    public String getProduceDays() {
        return produceDays;
    }

    public void setProduceDays(String produceDays) {
        this.produceDays = produceDays;
    }

    @ExcelField(title="采购运输天数",align= ExcelField.Align.CENTER,sort = 310)
    public String getPurchaseShippingDays() {
        return purchaseShippingDays;
    }

    public void setPurchaseShippingDays(String purchaseShippingDays) {
        this.purchaseShippingDays = purchaseShippingDays;
    }

    @ExcelField(title="入库天数",align= ExcelField.Align.CENTER,sort = 320)
    public String getStockingDays() {
        return stockingDays;
    }

    public void setStockingDays(String stockingDays) {
        this.stockingDays = stockingDays;
    }

    @ExcelField(title="加工费用",align= ExcelField.Align.CENTER,sort = 330)
    public String getProcessingCost() {
        return processingCost;
    }

    public void setProcessingCost(String processingCost) {
        this.processingCost = processingCost;
    }

    @ExcelField(title="其他费用",align= ExcelField.Align.CENTER,sort = 340)
    public String getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(String otherCost) {
        this.otherCost = otherCost;
    }

    @ExcelField(title="采购运费",align= ExcelField.Align.CENTER,sort = 350)
    public String getPurchaseShippingCost() {
        return purchaseShippingCost;
    }

    public void setPurchaseShippingCost(String purchaseShippingCost) {
        this.purchaseShippingCost = purchaseShippingCost;
    }

    @ExcelField(title="单位",align= ExcelField.Align.CENTER,sort = 360)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @ExcelField(title="单位数量",align= ExcelField.Align.CENTER,sort = 370)
    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    @ExcelField(title="净重(克)",align= ExcelField.Align.CENTER,sort = 380)
    public String getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(String netWeight) {
        this.netWeight = netWeight;
    }

    @ExcelField(title="毛重(克)",align= ExcelField.Align.CENTER,sort = 390)
    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    @ExcelField(title="单个产品包裹重量(克)",align= ExcelField.Align.CENTER,sort = 400)
    public String getPerProductPackWeight() {
        return perProductPackWeight;
    }

    public void setPerProductPackWeight(String perProductPackWeight) {
        this.perProductPackWeight = perProductPackWeight;
    }

    @ExcelField(title="产品侵权风险",align= ExcelField.Align.CENTER,sort = 410)
    public String getInfringement() {
        return infringement;
    }

    public void setInfringement(String infringement) {
        this.infringement = infringement;
    }

    @ExcelField(title="产品物流属性",align= ExcelField.Align.CENTER,sort = 420)
    public Integer getShippingAttribute() {
        return shippingAttribute;
    }

    public void setShippingAttribute(Integer shippingAttribute) {
        this.shippingAttribute = shippingAttribute;
    }

    @ExcelField(title="产品状态",align= ExcelField.Align.CENTER,sort = 430)
    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    @ExcelField(title="采购人员",align= ExcelField.Align.CENTER,sort = 440)
    public String getPurchaseUser() {
        return purchaseUser;
    }

    public void setPurchaseUser(String purchaseUser) {
        this.purchaseUser = purchaseUser;
    }

    @ExcelField(title="负责人员",align= ExcelField.Align.CENTER,sort = 450)
    public String getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    @ExcelField(title="编辑人员",align= ExcelField.Align.CENTER,sort = 460)
    public String getEditorUser() {
        return editorUser;
    }

    public void setEditorUser(String editorUser) {
        this.editorUser = editorUser;
    }

    @ExcelField(title="图片处理人员",align= ExcelField.Align.CENTER,sort = 470)
    public String getImageHandleUser() {
        return imageHandleUser;
    }

    public void setImageHandleUser(String imageHandleUser) {
        this.imageHandleUser = imageHandleUser;
    }

    @ExcelField(title="默认本地发货仓库",align= ExcelField.Align.CENTER,sort = 480)
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @ExcelField(title="开发人员",align= ExcelField.Align.CENTER,sort = 490)
    public String getDevelopUser() {
        return developUser;
    }

    public void setDevelopUser(String developUser) {
        this.developUser = developUser;
    }

    @ExcelField(title="产品英文名",align= ExcelField.Align.CENTER,sort = 500)
    public String getProductEnName() {
        return productEnName;
    }

    public void setProductEnName(String productEnName) {
        this.productEnName = productEnName;
    }

    @ExcelField(title="报关材质",align= ExcelField.Align.CENTER,sort = 510)
    public String getEntryMaterial() {
        return entryMaterial;
    }

    public void setEntryMaterial(String entryMaterial) {
        this.entryMaterial = entryMaterial;
    }

    @ExcelField(title="退税率",align= ExcelField.Align.CENTER,sort = 520)
    public String getRefundTaxRate() {
        return refundTaxRate;
    }

    public void setRefundTaxRate(String refundTaxRate) {
        this.refundTaxRate = refundTaxRate;
    }

    @ExcelField(title="海关编码",align= ExcelField.Align.CENTER,sort = 530)
    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    @ExcelField(title="是否审核海关编码",align= ExcelField.Align.CENTER,sort = 540)
    public String getVerifyCustomsCode() {
        return verifyCustomsCode;
    }

    public void setVerifyCustomsCode(String verifyCustomsCode) {
        this.verifyCustomsCode = verifyCustomsCode;
    }

    @ExcelField(title="产品关键词",align= ExcelField.Align.CENTER,sort = 550)
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @ExcelField(title="产品关键词1",align= ExcelField.Align.CENTER,sort = 560)
    public String getKeywords1() {
        return keywords1;
    }

    public void setKeywords1(String keywords1) {
        this.keywords1 = keywords1;
    }

    @ExcelField(title="产品关键词2",align= ExcelField.Align.CENTER,sort = 570)
    public String getKeywords2() {
        return keywords2;
    }


    public void setKeywords2(String keywords2) {
        this.keywords2 = keywords2;
    }

    @ExcelField(title="产品关键词3",align= ExcelField.Align.CENTER,sort = 580)
    public String getKeywords3() {
        return keywords3;
    }

    public void setKeywords3(String keywords3) {
        this.keywords3 = keywords3;
    }

    @ExcelField(title="产品关键词4",align= ExcelField.Align.CENTER,sort = 590)
    public String getKeywords4() {
        return keywords4;
    }

    public void setKeywords4(String keywords4) {
        this.keywords4 = keywords4;
    }

    @ExcelField(title="产品关键词5",align= ExcelField.Align.CENTER,sort = 600)
    public String getKeywords5() {
        return keywords5;
    }

    public void setKeywords5(String keywords5) {
        this.keywords5 = keywords5;
    }

    @ExcelField(title="产品简要描述",align= ExcelField.Align.CENTER,sort = 610)
    public String getProductShortDesc() {
        return productShortDesc;
    }

    public void setProductShortDesc(String productShortDesc) {
        this.productShortDesc = productShortDesc;
    }

    @ExcelField(title="产品特征1",align= ExcelField.Align.CENTER,sort = 620)
    public String getFeature1() {
        return feature1;
    }

    public void setFeature1(String feature1) {
        this.feature1 = feature1;
    }

    @ExcelField(title="产品特征2",align= ExcelField.Align.CENTER,sort = 630)
    public String getFeature2() {
        return feature2;
    }

    public void setFeature2(String feature2) {
        this.feature2 = feature2;
    }

    @ExcelField(title="产品特征3",align= ExcelField.Align.CENTER,sort = 640)
    public String getFeature3() {
        return feature3;
    }

    public void setFeature3(String feature3) {
        this.feature3 = feature3;
    }

    @ExcelField(title="产品特征4",align= ExcelField.Align.CENTER,sort = 650)
    public String getFeature4() {
        return feature4;
    }

    public void setFeature4(String feature4) {
        this.feature4 = feature4;
    }

    @ExcelField(title="产品特征5",align= ExcelField.Align.CENTER,sort = 660)
    public String getFeature5() {
        return feature5;
    }

    public void setFeature5(String feature5) {
        this.feature5 = feature5;
    }

    @ExcelField(title="产品描述",align= ExcelField.Align.CENTER,sort = 670)
    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    @ExcelField(title="参考网站链接",align= ExcelField.Align.CENTER,sort = 680)
    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    @ExcelField(title="产品包装清单",align= ExcelField.Align.CENTER,sort =690)
    public String getPackChecklist() {
        return packChecklist;
    }

    public void setPackChecklist(String packChecklist) {
        this.packChecklist = packChecklist;
    }

    @ExcelField(title="品牌名",align= ExcelField.Align.CENTER,sort = 700)
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @ExcelField(title="售价（美元）",align= ExcelField.Align.CENTER,sort = 710)
    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    @ExcelField(title="供应商产品编码",align= ExcelField.Align.CENTER,sort = 720)
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @ExcelField(title="产品主图",align= ExcelField.Align.CENTER,sort = 730)
    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    @ExcelField(title="产品其他图片1",align= ExcelField.Align.CENTER,sort = 740)
    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    @ExcelField(title="产品其他图片2",align= ExcelField.Align.CENTER,sort = 750)
    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    @ExcelField(title="产品其他图片3",align= ExcelField.Align.CENTER,sort = 760)
    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    @ExcelField(title="产品其他图片4",align= ExcelField.Align.CENTER,sort = 770)
    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    @ExcelField(title="产品其他图片5",align= ExcelField.Align.CENTER,sort = 780)
    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    @ExcelField(title="产品其他图片6",align= ExcelField.Align.CENTER,sort = 790)
    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    @ExcelField(title="产品其他图片7",align= ExcelField.Align.CENTER,sort = 800)
    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = image7;
    }

    @ExcelField(title="产品其他图片8",align= ExcelField.Align.CENTER,sort = 810)
    public String getImage8() {
        return image8;
    }

    public void setImage8(String image8) {
        this.image8 = image8;
    }

    @ExcelField(title="产品其他图片9",align= ExcelField.Align.CENTER,sort = 820)
    public String getImage9() {
        return image9;
    }

    public void setImage9(String image9) {
        this.image9 = image9;
    }

    @ExcelField(title="强电插头",align= ExcelField.Align.CENTER,sort = 830)
    public String getStrongElePlug() {
        return strongElePlug;
    }

    public void setStrongElePlug(String strongElePlug) {
        this.strongElePlug = strongElePlug;
    }

    @ExcelField(title="插头规格",align= ExcelField.Align.CENTER,sort = 840)
    public String getPlugStandard() {
        return plugStandard;
    }

    public void setPlugStandard(String plugStandard) {
        this.plugStandard = plugStandard;
    }

    @ExcelField(title="插头规格作为产品标题",align= ExcelField.Align.CENTER,sort = 850)
    public String getPlugStandardAsTitle() {
        return plugStandardAsTitle;
    }

    public void setPlugStandardAsTitle(String plugStandardAsTitle) {
        this.plugStandardAsTitle = plugStandardAsTitle;
    }

    @ExcelField(title="产品用途",align= ExcelField.Align.CENTER,sort = 860)
    public String getProductUsage() {
        return productUsage;
    }

    public void setProductUsage(String productUsage) {
        this.productUsage = productUsage;
    }

    @ExcelField(title="开发备注",align= ExcelField.Align.CENTER,sort = 870)
    public String getDevelopRemarks() {
        return developRemarks;
    }

    public void setDevelopRemarks(String developRemarks) {
        this.developRemarks = developRemarks;
    }

    @ExcelField(title="编辑备注",align= ExcelField.Align.CENTER,sort = 880)
    public String getEditRemarks() {
        return editRemarks;
    }

    public void setEditRemarks(String editRemarks) {
        this.editRemarks = editRemarks;
    }

    @ExcelField(title="质检备注",align= ExcelField.Align.CENTER,sort = 890)
    public String getQualityCheckRemarks() {
        return qualityCheckRemarks;
    }

    public void setQualityCheckRemarks(String qualityCheckRemarks) {
        this.qualityCheckRemarks = qualityCheckRemarks;
    }

    @ExcelField(title="采购收货备注",align= ExcelField.Align.CENTER,sort = 900)
    public String getPurchaseReceiveRemarks() {
        return purchaseReceiveRemarks;
    }

    public void setPurchaseReceiveRemarks(String purchaseReceiveRemarks) {
        this.purchaseReceiveRemarks = purchaseReceiveRemarks;
    }

    @ExcelField(title="默认供货批发量",align= ExcelField.Align.CENTER,sort = 910)
    public String getWholesaleNum() {
        return wholesaleNum;
    }

    public void setWholesaleNum(String wholesaleNum) {
        this.wholesaleNum = wholesaleNum;
    }

    @ExcelField(title="规格型号",align= ExcelField.Align.CENTER,sort = 920)
    public String getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    @ExcelField(title="产品类型",align= ExcelField.Align.CENTER,sort = 930)
    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getReceiptRemark() {
        return receiptRemark;
    }

    public void setReceiptRemark(String receiptRemark) {
        this.receiptRemark = receiptRemark;
    }

    public String getProcurementCheckMemo() {
        return procurementCheckMemo;
    }

    public void setProcurementCheckMemo(String procurementCheckMemo) {
        this.procurementCheckMemo = procurementCheckMemo;
    }

    public String getDeliveryPackNote() {
        return deliveryPackNote;
    }

    public void setDeliveryPackNote(String deliveryPackNote) {
        this.deliveryPackNote = deliveryPackNote;
    }

    public Integer getWareHouseId() {
        return wareHouseId;
    }
    public void setWareHouseId(Integer wareHouseId) {
        this.wareHouseId = wareHouseId;
    }


}
