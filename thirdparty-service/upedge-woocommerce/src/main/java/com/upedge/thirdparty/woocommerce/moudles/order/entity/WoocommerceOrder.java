package com.upedge.thirdparty.woocommerce.moudles.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
public class WoocommerceOrder {




    private String id;
    private int parent_id;
    private String number;
    private String order_key;
    private String created_via;
    private String version;
    private String status;
    private String currency;
    private Date date_created;
    private Date date_created_gmt;
    private Date date_modified;
    private Date date_modified_gmt;
    private BigDecimal discount_total;
    private BigDecimal discount_tax;
    private BigDecimal shipping_total;
    private BigDecimal shipping_tax;
    private BigDecimal cart_tax;
    private BigDecimal total;
    private BigDecimal total_tax;
    private boolean prices_include_tax;
    private int customer_id;
    private String customer_ip_address;
    private String customer_user_agent;
    private String customer_note;
    private BillingBean billing;
    private WoocommerceOrderAddress shipping;
    private String payment_method;
    private String payment_method_title;
    private String transaction_id;
    private Date date_paid;
    private Date date_paid_gmt;
    private Date date_completed;
    private Date date_completed_gmt;
    private String cart_hash;
    private String currency_symbol;

    private List<WoocommerceOrderItem> line_items;




    public static class BillingBean {
        /**
         * first_name : Jones
         * last_name : Olajide
         * company :
         * address_1 : 101 1/2 Cypress Street
         * address_2 :
         * city : HAGERSTOWN
         * state : MD
         * postcode : 21742
         * country : US
         * email : jones1960@md.net
         * phone :
         */

        private String first_name;
        private String last_name;
        private String company;
        private String address_1;
        private String address_2;
        private String city;
        private String state;
        private String postcode;
        private String country;
        private String email;
        private String phone;

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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public String getAddress_2() {
            return address_2;
        }

        public void setAddress_2(String address_2) {
            this.address_2 = address_2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }



}
