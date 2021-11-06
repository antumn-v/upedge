package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cjq on 2018/10/14.
 * 赛盒产品导出模板
 */
@Data
public class ExportOrderProductVo implements Serializable {

    private List<ProductVariantAttr> productVariantAttrList;

    private Long variantId;

    private Integer cateType;

    public Integer getCateType() {
        return cateType;
    }

    public void setCateType(Integer cateType) {
        this.cateType = cateType;
    }

    public String getShippingAttrId() {
        return shippingAttrId;
    }

    public void setShippingAttrId(String shippingAttrId) {
        this.shippingAttrId = shippingAttrId;
    }

    private String productId;
    private String originalId;
    private String weight;
    //运输属性id
    private String shippingAttrId;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
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
/*    // 产品颜色英文
    private String color;
    // 产品尺码英文
    private String size;*/
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
    //采购人员id
    private Long UserId;
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

    public void setPurchaseLink(String purchaseLink) {
        this.purchaseLink = purchaseLink;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    private Integer wareHouseId;//赛盒仓库ID

    private String receiptRemark;//采购收货备注
    private String procurementCheckMemo;//质检备注
    private String deliveryPackNote;//发货打包备注

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

    public static void main(String[] args) {
        String purchaseLink="dsdsds-1";
        if(purchaseLink.contains("-")) {
            purchaseLink=purchaseLink.substring(0, purchaseLink.indexOf("-"));
        }
        System.out.println(purchaseLink);
    }


}
