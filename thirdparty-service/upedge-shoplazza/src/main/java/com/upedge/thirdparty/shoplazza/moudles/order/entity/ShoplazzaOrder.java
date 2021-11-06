package com.upedge.thirdparty.shoplazza.moudles.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ShoplazzaOrder {


    private String id;
    private Date created_at;
    private Date updated_at;
    private String note;
    private String number;
    private BigDecimal total_price;
    private String sub_total;
    private String currency;
    private String financial_status;
    private String status;
    private Object canceled_at;
    private Object cancel_reason;
    private String payment_method;
    private String fulfillment_status;
    private String customer_deleted_at;
    private String deleted_at;
    private String placed_at;
    private String tags;
    private String discount_code;
    private boolean buyer_accepts_marketing;
    private String code_discount_total;
    private String line_item_discount_total;
    private String customer_note;
    private String total_discount;
    private String total_tax;
    private BigDecimal total_shipping;
    private String landing_site;
    private PaymentLineBean payment_line;
    private ShippingLineBean shipping_line;
    private ShoplazzaBillingAddress billing_address;
    private ShoplazzaShippingAddress shipping_address;
    private ShoplazzaCustomer customer;
    private List<ShoplazzaLineItems> line_items;
    private List<ShoplazzaFulfilliment> fulfillments;

    
    public static class PaymentLineBean {
        /**
         * payment_channel : paypal
         * payment_method : online
         * transaction_no : 52Y26167A2351800A
         * merchant_id : 26167A23518
         * merchant_email : abc@def.com
         */

        private String payment_channel;
        private String payment_method;
        private String transaction_no;
        private String merchant_id;
        private String merchant_email;

        public String getPayment_channel() {
            return payment_channel;
        }

        public void setPayment_channel(String payment_channel) {
            this.payment_channel = payment_channel;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getTransaction_no() {
            return transaction_no;
        }

        public void setTransaction_no(String transaction_no) {
            this.transaction_no = transaction_no;
        }

        public String getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(String merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getMerchant_email() {
            return merchant_email;
        }

        public void setMerchant_email(String merchant_email) {
            this.merchant_email = merchant_email;
        }
    }

    public static class ShippingLineBean {
        /**
         * name : free
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ShoplazzaBillingAddress {

        private String first_name;
        private String last_name;
        private String address1;
        private String address2;
        private String city;
        private String zip;
        private String province;
        private String country;
        private Object company;
        private double latitude;
        private double longitude;
        private String name;
        private String country_code;
        private String province_code;
        private String email;
        private String area;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
    }

    @Data
    public static class ShoplazzaShippingAddress {


        private String first_name;
        private String last_name;
        private String address1;
        private String address2;
        private String phone;
        private String city;
        private String zip;
        private String province;
        private String country;
        private String company;
        private double latitude;
        private double longitude;
        private String name;
        private String country_code;
        private String province_code;
        private String phone_area_code;
        private String area;
        private String email;


    }

    @Data
    public static class ShoplazzaCustomer {
        /**
         * email : bob.norman@hostmail.com
         * created_at : 2018-11-02T12:30:10-04:00
         * updated_at : 2018-11-02T12:30:10-04:00
         * first_name : Bob
         * last_name : Norman
         * orders_count : 1
         * total_spent : 41.94
         * phone :
         */

        private String email;
        private String created_at;
        private String updated_at;
        private String first_name;
        private String last_name;
        private int orders_count;
        private String total_spent;
        private String phone;

    }

    @Data
    public static class ShoplazzaLineItems {

        private String id;
        private String product_id;
        private String product_title;
        private String variant_id;
        private String variant_title;
        private int quantity;
        private String note;
        private String image;
        private BigDecimal price;
        private double compare_at_price;
        private String total;
        private String fulfillment_status;
        private String sku;
        private double weight;
        private String weight_unit;
        private String vendor;
        private String product_handle;
        private String product_url;
        private List<PropertiesBean> properties;

        public static class PropertiesBean {
            /**
             * name : color
             * value : red
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    
}
