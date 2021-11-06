package com.upedge.thirdparty.shoplazza.moudles.shop.entity;

import lombok.Data;

@Data
public class ShoplazzaShop {

    private Long id;
    private String name;
    private String email;
    private String domain;
    private String address1;
    private String zip;
    private String city;
    private String phone;
    private String primary_locale;
    private String address2;
    private String created_at;
    private String updated_at;
    private String country_code;
    private String currency;
    private String customer_email;
    private String timezone;
    private String shop_owner;
    private String account;
    private String province_code;
    private IconBean icon;



    public static class IconBean {
        /**
         * src : https://cn.cdn.shoplazza.com/0d42d239eb4a6a6e07652b24219e62b9.jpg
         * path : 0d42d239eb4a6a6e07652b24219e62b9.jpg
         */

        private String src;
        private String path;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
