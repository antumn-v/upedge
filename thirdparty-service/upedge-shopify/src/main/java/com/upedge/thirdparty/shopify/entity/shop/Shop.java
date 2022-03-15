package com.upedge.thirdparty.shopify.entity.shop;

import lombok.Data;

import java.util.List;

@Data
public class Shop {

    private String id;
    private String name;
    private String email;
    private String domain;
    private String province;
    private String country;
    private String address1;
    private String zip;
    private String city;
    private Object source;
    private String phone;
    private String latitude;
    private String longitude;
    private String primary_locale;
    private String address2;
    private String created_at;
    private String updated_at;
    private String country_code;
    private String country_name;
    private String currency;
    private String customer_email;
    private String timezone;
    private String iana_timezone;
    private String shop_owner;
    private String money_format;
    private String money_with_currency_format;
    private String weight_unit;
    private String province_code;
    private Object taxes_included;
    private Object tax_shipping;
    private String county_taxes;
    private String plan_display_name;
    private String plan_name;
    private String has_discounts;
    private String has_gift_cards;
    private String myshopify_domain;
    private Object google_apps_domain;
    private Object google_apps_login_enabled;
    private String money_in_emails_format;
    private String money_with_currency_in_emails_format;
    private boolean eligible_for_payments;
    private boolean requires_extra_payments_agreement;
    private boolean password_enabled;
    private boolean has_storefront;
    private boolean eligible_for_card_reader_giveaway;
    private boolean finances;
    private String primary_location_id;
    private String cookie_consent_level;
    private String visitor_tracking_consent_preference;
    private boolean force_ssl;
    private boolean checkout_api_supported;
    private boolean multi_location_enabled;
    private boolean setup_required;
    private boolean pre_launch_enabled;
    private List<String> enabled_presentment_currencies;


}
