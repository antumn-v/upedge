package com.upedge.oms.modules.order.vo;

import com.upedge.common.utils.excel.ExcelField;

public class StoreOrderVariantData {

    //客户ID
    String customerId;
    //店铺id
    String storeId;
    //店铺付款状态
    String financialStatus;
    //币种
    String currency;
    //下单时间
    String processedAt;
    //下单金额
    String totalPrice;
    //客户邮箱
    String customerEmail;
    //first_name
    String firstName;
    //last_name
    String lastName;
    //地址1
    String address1;
    //地址2
    String address2;
    //电话
    String addrPhone;
    //城市
    String city;
    //邮编
    String zip;
    //省
    String province;
    //国家
    String country;
    //国家代码
    String countryCode;
    //产品标题
    String productTitle;
    //产品数量
    Integer quantity;
    //产品售价
    String price;
    //产品ID
    String productId;


    @ExcelField(title="客户ID",align= ExcelField.Align.LEFT,sort = 10)
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @ExcelField(title="店铺ID",align= ExcelField.Align.LEFT,sort = 20)
    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @ExcelField(title="店铺付款状态",align= ExcelField.Align.LEFT,sort = 30)
    public String getFinancialStatus() {
        if(financialStatus!=null){
            switch (financialStatus){
                case "0":
                    financialStatus="已付款";
                    break;
                case "1":
                    financialStatus="部分退款";
                    break;
                case "2":
                    financialStatus="全部退款";
                    break;
            }
        }
        return financialStatus;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    @ExcelField(title="币种",align= ExcelField.Align.LEFT,sort = 50)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @ExcelField(title="下单时间",align= ExcelField.Align.LEFT,sort = 60)
    public String getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(String processedAt) {
        this.processedAt = processedAt;
    }

    @ExcelField(title="下单金额",align= ExcelField.Align.LEFT,sort =70)
    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @ExcelField(title="客户邮箱",align= ExcelField.Align.LEFT,sort =80)
    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @ExcelField(title="First Name",align= ExcelField.Align.LEFT,sort =90)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @ExcelField(title="Last Name",align= ExcelField.Align.LEFT,sort =100)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ExcelField(title="地址1",align= ExcelField.Align.LEFT,sort =110)
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @ExcelField(title="地址2",align= ExcelField.Align.LEFT,sort =120)
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @ExcelField(title="电话",align= ExcelField.Align.LEFT,sort =130)
    public String getAddrPhone() {
        return addrPhone;
    }

    public void setAddrPhone(String addrPhone) {
        this.addrPhone = addrPhone;
    }

    @ExcelField(title="城市",align= ExcelField.Align.LEFT,sort =140)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ExcelField(title="邮编",align= ExcelField.Align.LEFT,sort =150)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @ExcelField(title="省",align= ExcelField.Align.LEFT,sort =160)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @ExcelField(title="国家",align= ExcelField.Align.LEFT,sort =170)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @ExcelField(title="国家代码",align= ExcelField.Align.LEFT,sort =180)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @ExcelField(title="产品标题",align= ExcelField.Align.LEFT,sort =190)
    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    @ExcelField(title="产品数量",align= ExcelField.Align.LEFT,sort =200)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ExcelField(title="产品售价",align= ExcelField.Align.LEFT,sort =210)
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @ExcelField(title="产品ID",align= ExcelField.Align.LEFT,sort =220)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
