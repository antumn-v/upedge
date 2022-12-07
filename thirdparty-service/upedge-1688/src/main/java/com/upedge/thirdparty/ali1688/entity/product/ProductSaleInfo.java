package com.upedge.thirdparty.ali1688.entity.product;

/**
 * Created by guoxing on 2020/6/15.
 */
public class ProductSaleInfo {

    /**
     * 是否支持网上交易。true：支持 false：不支持，国际站不需关注此字段
     */
    Boolean supportOnlineTrade;

    /**
     * 是否支持混批，国际站无需关注此字段
     */
    Boolean mixWholeSale;

    /**
     * 销售方式，按件卖(normal)或者按批卖(batch)，1688站点无需关注此字段
     */
    String saleType;

    /**
     * 是否价格私密信息，国际站无需关注此字段
     */
    Boolean priceAuth;

    /**
     * 区间价格。按数量范围设定的区间价格
     */
    ProductPriceRange priceRanges[];

    /**
     *可售数量，国际站无需关注此字段
     */
    Integer amountOnSale;

    /**
     * 计量单位
     */
    String unit;

    /**
     *最小起订量，范围是1-99999。
     */
    Integer minOrderQuantity;

    /**
     *每批数量，默认为空或者非零值，该属性不为空时sellunit为必填
     */
    Integer batchNumber;

    /**
     * 建议零售价，国际站无需关注
     */
    Double retailprice;

    /**
     *税率相关信息，内容由用户自定，国际站无需关注
     */
    String tax;

    /**
     * 售卖单位，如果为批量售卖，代表售卖的单位，
     * 该属性不为空时batchNumber为必填，例如1"手"=12“件"的"手"，
     * 国际站无需关注
     */
    String sellunit;

    /**
     * 普通报价-FIXED_PRICE("0"),SKU规格报价-SKU_PRICE("1"),
     * SKU区间报价（商品维度）-SKU_PRICE_RANGE_FOR_OFFER("2"),
     * SKU区间报价（SKU维度）-SKU_PRICE_RANGE("3")，国际站无需关注
     */
    Integer quoteType;

    /**
     *分销基准价
     */
    Double consignPrice;

    public Boolean getSupportOnlineTrade() {
        return supportOnlineTrade;
    }

    public void setSupportOnlineTrade(Boolean supportOnlineTrade) {
        this.supportOnlineTrade = supportOnlineTrade;
    }

    public Boolean getMixWholeSale() {
        return mixWholeSale;
    }

    public void setMixWholeSale(Boolean mixWholeSale) {
        this.mixWholeSale = mixWholeSale;
    }

    public String getSaleType() {
        return saleType;
    }

    public void setSaleType(String saleType) {
        this.saleType = saleType;
    }

    public Boolean getPriceAuth() {
        return priceAuth;
    }

    public void setPriceAuth(Boolean priceAuth) {
        this.priceAuth = priceAuth;
    }

    public ProductPriceRange[] getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(ProductPriceRange[] priceRanges) {
        this.priceRanges = priceRanges;
    }

    public Integer getAmountOnSale() {
        return amountOnSale;
    }

    public void setAmountOnSale(Integer amountOnSale) {
        this.amountOnSale = amountOnSale;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Double getRetailprice() {
        return retailprice;
    }

    public void setRetailprice(Double retailprice) {
        this.retailprice = retailprice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getSellunit() {
        return sellunit;
    }

    public void setSellunit(String sellunit) {
        this.sellunit = sellunit;
    }

    public Integer getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(Integer quoteType) {
        this.quoteType = quoteType;
    }

    public Double getConsignPrice() {
        return consignPrice;
    }

    public void setConsignPrice(Double consignPrice) {
        this.consignPrice = consignPrice;
    }
}
