package com.upedge.thirdparty.shopify.moudles.order.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ShopifyOrder {

    @Override
    public String toString() {
        return "ShopifyOrder{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", closed_at='" + closed_at + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", number=" + number +
                ", note='" + note + '\'' +
                ", token='" + token + '\'' +
                ", gateway='" + gateway + '\'' +
                ", test=" + test +
                ", total_price=" + total_price +
                ", subtotal_price=" + subtotal_price +
                ", total_weight=" + total_weight +
                ", total_tax='" + total_tax + '\'' +
                ", taxes_included=" + taxes_included +
                ", currency='" + currency + '\'' +
                ", financial_status='" + financial_status + '\'' +
                ", confirmed=" + confirmed +
                ", total_discounts='" + total_discounts + '\'' +
                ", total_line_items_price=" + total_line_items_price +
                ", cart_token='" + cart_token + '\'' +
                ", buyer_accepts_marketing=" + buyer_accepts_marketing +
                ", name='" + name + '\'' +
                ", referring_site='" + referring_site + '\'' +
                ", landing_site='" + landing_site + '\'' +
                ", cancelled_at='" + cancelled_at + '\'' +
                ", cancel_reason='" + cancel_reason + '\'' +
                ", total_price_usd='" + total_price_usd + '\'' +
                ", checkout_token='" + checkout_token + '\'' +
                ", reference='" + reference + '\'' +
                ", user_id='" + user_id + '\'' +
                ", location_id='" + location_id + '\'' +
                ", source_identifier='" + source_identifier + '\'' +
                ", source_url='" + source_url + '\'' +
                ", processed_at='" + processed_at + '\'' +
                ", device_id='" + device_id + '\'' +
                ", phone='" + phone + '\'' +
                ", customer_locale='" + customer_locale + '\'' +
                ", app_id=" + app_id +
                ", browser_ip='" + browser_ip + '\'' +
                ", landing_site_ref='" + landing_site_ref + '\'' +
                ", order_number=" + order_number +
                ", processing_method='" + processing_method + '\'' +
                ", checkout_id=" + checkout_id +
                ", source_name='" + source_name + '\'' +
                ", fulfillment_status='" + fulfillment_status + '\'' +
                ", tags='" + tags + '\'' +
                ", contact_email='" + contact_email + '\'' +
                ", order_status_url='" + order_status_url + '\'' +
                ", presentment_currency='" + presentment_currency + '\'' +
                ", total_line_items_price_set=" + total_line_items_price_set +
                ", total_discounts_set=" + total_discounts_set +
                ", total_shipping_price_set=" + total_shipping_price_set +
                ", subtotal_price_set=" + subtotal_price_set +
                ", total_price_set=" + total_price_set +
                ", total_tax_set=" + total_tax_set +
                ", total_tip_received='" + total_tip_received + '\'' +
                ", admin_graphql_api_id='" + admin_graphql_api_id + '\'' +
                ", billing_address=" + billing_address +
                ", shipping_address=" + shipping_address +
                ", customer=" + customer +
                ", line_items=" + line_items +
                ", fulfillments=" + fulfillments +
                ", shipping_lines=" + shipping_lines +
                '}';
    }

    private String id;
    private String email;
    private String closed_at;
    private Date created_at;
    private Date updated_at;
    private Integer number;
    private String note;
    private String token;
    private String gateway;
    private Boolean test;
    private BigDecimal total_price;
    private BigDecimal subtotal_price;
    private BigDecimal total_weight;
    private String total_tax;
    private Boolean taxes_included;
    private String currency;
    private String financial_status;
    private Boolean confirmed;
    private String total_discounts;
    private BigDecimal total_line_items_price;
    private String cart_token;
    private Boolean buyer_accepts_marketing;
    private String name;
    private String referring_site;
    private String landing_site;
    private String cancelled_at;
    private String cancel_reason;
    private String total_price_usd;
    private String checkout_token;
    private String reference;
    private String user_id;
    private String location_id;
    private String source_identifier;
    private String source_url;
    private String processed_at;
    private String device_id;
    private String phone;
    private String customer_locale;
    private Integer app_id;
    private String browser_ip;
    private String landing_site_ref;
    private Integer order_number;
    private String processing_method;
    private Long checkout_id;
    private String source_name;
    private String fulfillment_status;
    private String tags;
    private String contact_email;
    private String order_status_url;
    private String presentment_currency;
    private TotalLineItemsPriceSetBean total_line_items_price_set;
    private TotalDiscountsSetBean total_discounts_set;
    private TotalShippingPriceSetBean total_shipping_price_set;
    private SubtotalPriceSetBean subtotal_price_set;
    private TotalPriceSetBean total_price_set;
    private TotalTaxSetBean total_tax_set;
    private String total_tip_received;
    private String admin_graphql_api_id;
    private BillingAddressBean billing_address;
    private ShippingAddressBean shipping_address;
    private CustomerBean customer;
  
    private List<ShopifyLineItem> line_items;
    private List<ShopifyFulfillment> fulfillments;
 
    private List<ShippingLinesBean> shipping_lines;

    public static class TotalLineItemsPriceSetBean {
        /**
         * shop_money : {"amount":"12.95","currency_code":"USD"}
         * presentment_money : {"amount":"12.95","currency_code":"USD"}
         */


        private ShopMoneyBean shop_money;
        private PresentmentMoneyBean presentment_money;

        public ShopMoneyBean getShop_money() {
            return shop_money;
        }

        public void setShop_money(ShopMoneyBean shop_money) {
            this.shop_money = shop_money;
        }

        public PresentmentMoneyBean getPresentment_money() {
            return presentment_money;
        }

        public void setPresentment_money(PresentmentMoneyBean presentment_money) {
            this.presentment_money = presentment_money;
        }

        public static class ShopMoneyBean {
            /**
             * amount : 12.95
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }

        public static class PresentmentMoneyBean {
            /**
             * amount : 12.95
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }
    }

    public static class TotalDiscountsSetBean {
        /**
         * shop_money : {"amount":"0.00","currency_code":"USD"}
         * presentment_money : {"amount":"0.00","currency_code":"USD"}
         */

        private ShopMoneyBeanX shop_money;
        private PresentmentMoneyBeanX presentment_money;

        public ShopMoneyBeanX getShop_money() {
            return shop_money;
        }

        public void setShop_money(ShopMoneyBeanX shop_money) {
            this.shop_money = shop_money;
        }

        public PresentmentMoneyBeanX getPresentment_money() {
            return presentment_money;
        }

        public void setPresentment_money(PresentmentMoneyBeanX presentment_money) {
            this.presentment_money = presentment_money;
        }

        public static class ShopMoneyBeanX {
            /**
             * amount : 0.00
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }

        public static class PresentmentMoneyBeanX {
            /**
             * amount : 0.00
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }
    }

    public static class TotalShippingPriceSetBean {
        /**
         * shop_money : {"amount":"5.97","currency_code":"USD"}
         * presentment_money : {"amount":"5.97","currency_code":"USD"}
         */

        private ShopMoneyBeanXX shop_money;
        private PresentmentMoneyBeanXX presentment_money;

        public ShopMoneyBeanXX getShop_money() {
            return shop_money;
        }

        public void setShop_money(ShopMoneyBeanXX shop_money) {
            this.shop_money = shop_money;
        }

        public PresentmentMoneyBeanXX getPresentment_money() {
            return presentment_money;
        }

        public void setPresentment_money(PresentmentMoneyBeanXX presentment_money) {
            this.presentment_money = presentment_money;
        }

        public static class ShopMoneyBeanXX {
            /**
             * amount : 5.97
             * currency_code : USD
             */

            private BigDecimal amount;
            private String currency_code;

            public BigDecimal getAmount() {
                return amount;
            }

            public void setAmount(BigDecimal amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }

        public static class PresentmentMoneyBeanXX {
            /**
             * amount : 5.97
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }
    }

    public static class SubtotalPriceSetBean {
        /**
         * shop_money : {"amount":"12.95","currency_code":"USD"}
         * presentment_money : {"amount":"12.95","currency_code":"USD"}
         */

        private ShopMoneyBeanXXX shop_money;
        private PresentmentMoneyBeanXXX presentment_money;

        public ShopMoneyBeanXXX getShop_money() {
            return shop_money;
        }

        public void setShop_money(ShopMoneyBeanXXX shop_money) {
            this.shop_money = shop_money;
        }

        public PresentmentMoneyBeanXXX getPresentment_money() {
            return presentment_money;
        }

        public void setPresentment_money(PresentmentMoneyBeanXXX presentment_money) {
            this.presentment_money = presentment_money;
        }

        public static class ShopMoneyBeanXXX {
            /**
             * amount : 12.95
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }

        public static class PresentmentMoneyBeanXXX {
            /**
             * amount : 12.95
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }
    }

    public static class TotalPriceSetBean {
        /**
         * shop_money : {"amount":"18.92","currency_code":"USD"}
         * presentment_money : {"amount":"18.92","currency_code":"USD"}
         */

        private ShopMoneyBeanXXXX shop_money;
        private PresentmentMoneyBeanXXXX presentment_money;

        public ShopMoneyBeanXXXX getShop_money() {
            return shop_money;
        }

        public void setShop_money(ShopMoneyBeanXXXX shop_money) {
            this.shop_money = shop_money;
        }

        public PresentmentMoneyBeanXXXX getPresentment_money() {
            return presentment_money;
        }

        public void setPresentment_money(PresentmentMoneyBeanXXXX presentment_money) {
            this.presentment_money = presentment_money;
        }

        public static class ShopMoneyBeanXXXX {
            /**
             * amount : 18.92
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }

        public static class PresentmentMoneyBeanXXXX {
            /**
             * amount : 18.92
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }
    }

    public static class TotalTaxSetBean {
        /**
         * shop_money : {"amount":"0.00","currency_code":"USD"}
         * presentment_money : {"amount":"0.00","currency_code":"USD"}
         */

        private ShopMoneyBeanXXXXX shop_money;
        private PresentmentMoneyBeanXXXXX presentment_money;

        public ShopMoneyBeanXXXXX getShop_money() {
            return shop_money;
        }

        public void setShop_money(ShopMoneyBeanXXXXX shop_money) {
            this.shop_money = shop_money;
        }

        public PresentmentMoneyBeanXXXXX getPresentment_money() {
            return presentment_money;
        }

        public void setPresentment_money(PresentmentMoneyBeanXXXXX presentment_money) {
            this.presentment_money = presentment_money;
        }

        public static class ShopMoneyBeanXXXXX {
            /**
             * amount : 0.00
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }

        public static class PresentmentMoneyBeanXXXXX {
            /**
             * amount : 0.00
             * currency_code : USD
             */

            private String amount;
            private String currency_code;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCurrency_code() {
                return currency_code;
            }

            public void setCurrency_code(String currency_code) {
                this.currency_code = currency_code;
            }
        }
    }

    public static class BillingAddressBean {
        /**
         * first_name : Lurriyana
         * address1 : 23900 Southeast Stark Street
         * phone : (971) 806-9779
         * city : Gresham
         * zip : 97030
         * province : Oregon
         * country : United States
         * last_name : Mainwood
         * address2 : 134
         * company : null
         * latitude : 45.51774100000001
         * Longitude : -122.416575
         * name : Lurriyana Mainwood
         * country_code : US
         * province_code : OR
         */

        private String first_name;
        private String address1;
        private String phone;
        private String city;
        private String zip;
        private String province;
        private String country;
        private String last_name;
        private String address2;
        private String company;
        private double latitude;
        private double Longitude;
        private String name;
        private String country_code;
        private String province_code;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
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
    }

    public static class CustomerBean {
        /**
         * id : 4536994791616
         * email : lurriyana200lmainwood@gmail.com
         * accepts_marketing : false
         * created_at : 2020-12-30T07:34:33+00:00
         * updated_at : 2020-12-30T07:39:26+00:00
         * first_name : Lurriyana
         * last_name : Mainwood
         * orders_count : 1
         * state : disabled
         * total_spent : 18.92
         * last_order_id : 3141762973888
         * note : null
         * verified_email : true
         * multipass_identifier : null
         * tax_exempt : false
         * phone : null
         * tags : 
         * last_order_name : #208100
         * currency : USD
         * accepts_marketing_updated_at : 2020-12-30T07:34:33+00:00
         * marketing_opt_in_level : null
         * tax_exemptions : []
         * admin_graphql_api_id : gid://shopify/Customer/4536994791616
         * default_address : {"id":5543322190016,"customer_id":4536994791616,"first_name":"Lurriyana","last_name":"Mainwood","company":null,"address1":"23900 Southeast Stark Street","address2":"134","city":"Gresham","province":"Oregon","country":"United States","zip":"97030","phone":"(971) 806-9779","name":"Lurriyana Mainwood","province_code":"OR","country_code":"US","country_name":"United States","default":true}
         */

        private Long id;
        private String email;
        private Boolean accepts_marketing;
        private String created_at;
        private String updated_at;
        private String first_name;
        private String last_name;
        private Integer orders_count;
        private String state;
        private String total_spent;
        private Long last_order_id;
        private String note;
        private Boolean verified_email;
        private String multipass_identifier;
        private Boolean tax_exempt;
        private String phone;
        private String tags;
        private String last_order_name;
        private String currency;
        private String accepts_marketing_updated_at;
        private String marketing_opt_in_level;
        private String admin_graphql_api_id;
        private DefaultAddressBean default_address;
        private List<?> tax_exemptions;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Boolean isAccepts_marketing() {
            return accepts_marketing;
        }

        public void setAccepts_marketing(Boolean accepts_marketing) {
            this.accepts_marketing = accepts_marketing;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

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

        public Integer getOrders_count() {
            return orders_count;
        }

        public void setOrders_count(Integer orders_count) {
            this.orders_count = orders_count;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTotal_spent() {
            return total_spent;
        }

        public void setTotal_spent(String total_spent) {
            this.total_spent = total_spent;
        }

        public Long getLast_order_id() {
            return last_order_id;
        }

        public void setLast_order_id(Long last_order_id) {
            this.last_order_id = last_order_id;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Boolean isVerified_email() {
            return verified_email;
        }

        public void setVerified_email(Boolean verified_email) {
            this.verified_email = verified_email;
        }

        public String getMultipass_identifier() {
            return multipass_identifier;
        }

        public void setMultipass_identifier(String multipass_identifier) {
            this.multipass_identifier = multipass_identifier;
        }

        public Boolean isTax_exempt() {
            return tax_exempt;
        }

        public void setTax_exempt(Boolean tax_exempt) {
            this.tax_exempt = tax_exempt;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getLast_order_name() {
            return last_order_name;
        }

        public void setLast_order_name(String last_order_name) {
            this.last_order_name = last_order_name;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getAccepts_marketing_updated_at() {
            return accepts_marketing_updated_at;
        }

        public void setAccepts_marketing_updated_at(String accepts_marketing_updated_at) {
            this.accepts_marketing_updated_at = accepts_marketing_updated_at;
        }

        public String getMarketing_opt_in_level() {
            return marketing_opt_in_level;
        }

        public void setMarketing_opt_in_level(String marketing_opt_in_level) {
            this.marketing_opt_in_level = marketing_opt_in_level;
        }

        public String getAdmin_graphql_api_id() {
            return admin_graphql_api_id;
        }

        public void setAdmin_graphql_api_id(String admin_graphql_api_id) {
            this.admin_graphql_api_id = admin_graphql_api_id;
        }

        public DefaultAddressBean getDefault_address() {
            return default_address;
        }

        public void setDefault_address(DefaultAddressBean default_address) {
            this.default_address = default_address;
        }

        public List<?> getTax_exemptions() {
            return tax_exemptions;
        }

        public void setTax_exemptions(List<?> tax_exemptions) {
            this.tax_exemptions = tax_exemptions;
        }

        public static class DefaultAddressBean {
            /**
             * id : 5543322190016
             * customer_id : 4536994791616
             * first_name : Lurriyana
             * last_name : Mainwood
             * company : null
             * address1 : 23900 Southeast Stark Street
             * address2 : 134
             * city : Gresham
             * province : Oregon
             * country : United States
             * zip : 97030
             * phone : (971) 806-9779
             * name : Lurriyana Mainwood
             * province_code : OR
             * country_code : US
             * country_name : United States
             * default : true
             */

            private Long id;
            private Long customer_id;
            private String first_name;
            private String last_name;
            private String company;
            private String address1;
            private String address2;
            private String city;
            private String province;
            private String country;
            private String zip;
            private String phone;
            private String name;
            private String province_code;
            private String country_code;
            private String country_name;
            private Boolean defaultX;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(Long customer_id) {
                this.customer_id = customer_id;
            }

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

            public String getZip() {
                return zip;
            }

            public void setZip(String zip) {
                this.zip = zip;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProvince_code() {
                return province_code;
            }

            public void setProvince_code(String province_code) {
                this.province_code = province_code;
            }

            public String getCountry_code() {
                return country_code;
            }

            public void setCountry_code(String country_code) {
                this.country_code = country_code;
            }

            public String getCountry_name() {
                return country_name;
            }

            public void setCountry_name(String country_name) {
                this.country_name = country_name;
            }

            public Boolean isDefaultX() {
                return defaultX;
            }

            public void setDefaultX(Boolean defaultX) {
                this.defaultX = defaultX;
            }
        }
    }

    public static class ShippingLinesBean {
        /**
         * id : 2618705903808
         * title : Tracked & Insured Shipping - Guarantees that your package is insured against loss, damage or missing contents.
         * price : 5.97
         * code : Tracked & Insured Shipping - Guarantees that your package is insured against loss, damage or missing contents.
         * source : shopify
         * phone : null
         * requested_fulfillment_service_id : null
         * delivery_category : null
         * carrier_identifier : null
         * discounted_price : 5.97
         * price_set : {"shop_money":{"amount":"5.97","currency_code":"USD"},"presentment_money":{"amount":"5.97","currency_code":"USD"}}
         * discounted_price_set : {"shop_money":{"amount":"5.97","currency_code":"USD"},"presentment_money":{"amount":"5.97","currency_code":"USD"}}
         * discount_allocations : []
         * tax_lines : []
         */

        private Long id;
        private String title;
        private String price;
        private String code;
        private String source;
        private String phone;
        private String requested_fulfillment_service_id;
        private String delivery_category;
        private String carrier_identifier;
        private String discounted_price;
        private PriceSetBeanX price_set;
        private DiscountedPriceSetBean discounted_price_set;
        private List<?> discount_allocations;
        private List<?> tax_lines;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRequested_fulfillment_service_id() {
            return requested_fulfillment_service_id;
        }

        public void setRequested_fulfillment_service_id(String requested_fulfillment_service_id) {
            this.requested_fulfillment_service_id = requested_fulfillment_service_id;
        }

        public String getDelivery_category() {
            return delivery_category;
        }

        public void setDelivery_category(String delivery_category) {
            this.delivery_category = delivery_category;
        }

        public String getCarrier_identifier() {
            return carrier_identifier;
        }

        public void setCarrier_identifier(String carrier_identifier) {
            this.carrier_identifier = carrier_identifier;
        }

        public String getDiscounted_price() {
            return discounted_price;
        }

        public void setDiscounted_price(String discounted_price) {
            this.discounted_price = discounted_price;
        }

        public PriceSetBeanX getPrice_set() {
            return price_set;
        }

        public void setPrice_set(PriceSetBeanX price_set) {
            this.price_set = price_set;
        }

        public DiscountedPriceSetBean getDiscounted_price_set() {
            return discounted_price_set;
        }

        public void setDiscounted_price_set(DiscountedPriceSetBean discounted_price_set) {
            this.discounted_price_set = discounted_price_set;
        }

        public List<?> getDiscount_allocations() {
            return discount_allocations;
        }

        public void setDiscount_allocations(List<?> discount_allocations) {
            this.discount_allocations = discount_allocations;
        }

        public List<?> getTax_lines() {
            return tax_lines;
        }

        public void setTax_lines(List<?> tax_lines) {
            this.tax_lines = tax_lines;
        }

        public static class PriceSetBeanX {
            /**
             * shop_money : {"amount":"5.97","currency_code":"USD"}
             * presentment_money : {"amount":"5.97","currency_code":"USD"}
             */

            private ShopMoneyBeanXXXXXXXX shop_money;
            private PresentmentMoneyBeanXXXXXXXX presentment_money;

            public ShopMoneyBeanXXXXXXXX getShop_money() {
                return shop_money;
            }

            public void setShop_money(ShopMoneyBeanXXXXXXXX shop_money) {
                this.shop_money = shop_money;
            }

            public PresentmentMoneyBeanXXXXXXXX getPresentment_money() {
                return presentment_money;
            }

            public void setPresentment_money(PresentmentMoneyBeanXXXXXXXX presentment_money) {
                this.presentment_money = presentment_money;
            }

            public static class ShopMoneyBeanXXXXXXXX {
                /**
                 * amount : 5.97
                 * currency_code : USD
                 */

                private String amount;
                private String currency_code;

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getCurrency_code() {
                    return currency_code;
                }

                public void setCurrency_code(String currency_code) {
                    this.currency_code = currency_code;
                }
            }

            public static class PresentmentMoneyBeanXXXXXXXX {
                /**
                 * amount : 5.97
                 * currency_code : USD
                 */

                private String amount;
                private String currency_code;

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getCurrency_code() {
                    return currency_code;
                }

                public void setCurrency_code(String currency_code) {
                    this.currency_code = currency_code;
                }
            }
        }

        public static class DiscountedPriceSetBean {
            /**
             * shop_money : {"amount":"5.97","currency_code":"USD"}
             * presentment_money : {"amount":"5.97","currency_code":"USD"}
             */

            private ShopMoneyBeanXXXXXXXXX shop_money;
            private PresentmentMoneyBeanXXXXXXXXX presentment_money;

            public ShopMoneyBeanXXXXXXXXX getShop_money() {
                return shop_money;
            }

            public void setShop_money(ShopMoneyBeanXXXXXXXXX shop_money) {
                this.shop_money = shop_money;
            }

            public PresentmentMoneyBeanXXXXXXXXX getPresentment_money() {
                return presentment_money;
            }

            public void setPresentment_money(PresentmentMoneyBeanXXXXXXXXX presentment_money) {
                this.presentment_money = presentment_money;
            }

            public static class ShopMoneyBeanXXXXXXXXX {
                /**
                 * amount : 5.97
                 * currency_code : USD
                 */

                private String amount;
                private String currency_code;

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getCurrency_code() {
                    return currency_code;
                }

                public void setCurrency_code(String currency_code) {
                    this.currency_code = currency_code;
                }
            }

            public static class PresentmentMoneyBeanXXXXXXXXX {
                /**
                 * amount : 5.97
                 * currency_code : USD
                 */

                private String amount;
                private String currency_code;

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getCurrency_code() {
                    return currency_code;
                }

                public void setCurrency_code(String currency_code) {
                    this.currency_code = currency_code;
                }
            }
        }
    }
}
