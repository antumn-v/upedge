package com.upedge.thirdparty.ali1688.entity.product;

import com.upedge.thirdparty.ali1688.vo.ProductInfoVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guoxing on 2020/6/15.
 */
public class ProductInfo {

    /**
     * 商品ID
     */
    Long productID;

    /**
     *商品类型，在线批发商品(wholesale)或者询盘商品(sourcing)，1688网站缺省为wholesale
     */
    String productType;

    /**
     * 类目ID，标识商品所属类目
     */
    Long categoryID;

    /**
     * 商品属性和属性值
     */
    List<ProductAttribute> attributes=new ArrayList<>();

    /**
     * 分组ID，确定商品所属分组。1688可传入多个分组ID，
     * 国际站同一个商品只能属于一个分组，因此默认只取第一个
     */
    Long groupID[];

    /**
     *商品状态。
     * published:上网状态;member expired:会员撤销;
     * auto expired:自然过期;expired:过期(包含手动过期与自动过期);
     * member deleted:会员删除;modified:修改;new:新发;
     * deleted:删除;TBD:to be delete;approved:审批通过;
     * auditing:审核中;untread:审核不通过;
     */
    String status;

    /**
     * 商品标题，最多128个字符
     */
    String subject;

    /**
     *商品详情描述，可包含图片中心的图片URL
     */
    String description;

    /**
     * 语种，参见FAQ 语种枚举值，1688网站默认传入CHINESE
     */
    String language;

    /**
     * 信息有效期，按天计算，国际站无此信息
     */
    Integer periodOfValidity;

    /**
     * 业务类型。1：商品，2：加工，3：代理，4：合作，5：商务服务。国际站按默认商品。
     */
    Integer bizType;

    /**
     * 是否图片私密信息，国际站此字段无效
     */
    Boolean pictureAuth;

    /**
     *商品主图
     */
    ProductImageInfo image;

    /**
     * sku信息
     */
    List<ProductSKUInfo> skuInfos=new ArrayList<>();

    /**
     * 商品销售信息
     */
    ProductSaleInfo saleInfo;

    /**
     *商品物流信息
     */
    ProductShippingInfo shippingInfo;

    /**
     * 商品扩展信息
     */
    ProductExtendInfo extendInfos[];

    /**
     * 供应商用户ID
     */
    String supplierUserId;

    /**
     * 质量星级(0-5)
     */
    Integer qualityLevel;

    /**
     * 供应商loginId
     */
    String supplierLoginId;

    /**
     * 类目名
     */
    String categoryName;

    /**
     *主图视频播放地址
     */
    String mainVedio;

    /**
     *商品货号，产品属性中的货号
     */
    String productCargoNumber;

    /**
     *是否海外代发
     */
    Boolean crossBorderOffer;

    /**
     * 参考价格，返回价格区间，可能为空
     */
    String referencePrice;

    /**
     * 创建时间
     */
    Date createTime;

    /**
     *获取用户最后一次修改商品信息的时间
     */
    Date lastUpdateTime;

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }

    public Long[] getGroupID() {
        return groupID;
    }

    public void setGroupID(Long[] groupID) {
        this.groupID = groupID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPeriodOfValidity() {
        return periodOfValidity;
    }

    public void setPeriodOfValidity(Integer periodOfValidity) {
        this.periodOfValidity = periodOfValidity;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public Boolean getPictureAuth() {
        return pictureAuth;
    }

    public void setPictureAuth(Boolean pictureAuth) {
        this.pictureAuth = pictureAuth;
    }

    public ProductImageInfo getImage() {
        return image;
    }

    public void setImage(ProductImageInfo image) {
        this.image = image;
    }

    public List<ProductSKUInfo> getSkuInfos() {
        return skuInfos;
    }

    public void setSkuInfos(List<ProductSKUInfo> skuInfos) {
        this.skuInfos = skuInfos;
    }

    public ProductSaleInfo getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(ProductSaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public ProductShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ProductShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    public ProductExtendInfo[] getExtendInfos() {
        return extendInfos;
    }

    public void setExtendInfos(ProductExtendInfo[] extendInfos) {
        this.extendInfos = extendInfos;
    }

    public String getSupplierUserId() {
        return supplierUserId;
    }

    public void setSupplierUserId(String supplierUserId) {
        this.supplierUserId = supplierUserId;
    }

    public Integer getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(Integer qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    public String getSupplierLoginId() {
        return supplierLoginId;
    }

    public void setSupplierLoginId(String supplierLoginId) {
        this.supplierLoginId = supplierLoginId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMainVedio() {
        return mainVedio;
    }

    public void setMainVedio(String mainVedio) {
        this.mainVedio = mainVedio;
    }

    public String getProductCargoNumber() {
        return productCargoNumber;
    }

    public void setProductCargoNumber(String productCargoNumber) {
        this.productCargoNumber = productCargoNumber;
    }

    public Boolean getCrossBorderOffer() {
        return crossBorderOffer;
    }

    public void setCrossBorderOffer(Boolean crossBorderOffer) {
        this.crossBorderOffer = crossBorderOffer;
    }

    public String getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getDetailImg(){
        if(image==null||image.getImages()==null||image.getImages().size()==0){
            return null;
        }
        List<String> imgList= image.getImages();
        if(imgList!=null&&imgList.size()>1){
            return imgList.get(1);
        }
        if(imgList!=null&&imgList.size()>0){
            return imgList.get(0);
        }
        return null;
    }

    public ProductInfoVo toProductInfoVo() {
        ProductInfoVo productInfoVo=new ProductInfoVo();
        productInfoVo.setCnDesc(description);
        return productInfoVo;
    }
}
