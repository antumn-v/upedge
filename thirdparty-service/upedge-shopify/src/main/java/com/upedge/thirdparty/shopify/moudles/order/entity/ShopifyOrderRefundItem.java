package com.upedge.thirdparty.shopify.moudles.order.entity;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ShopifyOrderRefundItem {


    /**
     * id : 209341123
     * line_item : {}
     * line_item_id : 128323456
     * quantity : 2
     * location_id : 40642626
     * restock_type : return
     * subtotal : 10.99
     * total_tax : 2.67
     * subtotal_set : {"shop_money":{"amount":10.99,"currency_code":"CAD"},"presentment_money":{"amount":8.95,"currency_code":"USD"}}
     * total_tax_set : {"shop_money":{"amount":1.67,"currency_code":"CAD"},"presentment_money":{"amount":1.32,"currency_code":"USD"}}
     */

    private String id;
    private String line_item_id;
    private int quantity;
    private int location_id;
    private String restock_type;
    private BigDecimal subtotal;
    private BigDecimal total_tax;
    private SubtotalSetBean subtotal_set;
    private TotalTaxSetBean total_tax_set;



    public static class SubtotalSetBean {
        /**
         * shop_money : {"amount":10.99,"currency_code":"CAD"}
         * presentment_money : {"amount":8.95,"currency_code":"USD"}
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
             * amount : 10.99
             * currency_code : CAD
             */

            private double amount;
            private String currency_code;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
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
             * amount : 8.95
             * currency_code : USD
             */

            private double amount;
            private String currency_code;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
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
         * shop_money : {"amount":1.67,"currency_code":"CAD"}
         * presentment_money : {"amount":1.32,"currency_code":"USD"}
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
             * amount : 1.67
             * currency_code : CAD
             */

            private double amount;
            private String currency_code;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
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
             * amount : 1.32
             * currency_code : USD
             */

            private double amount;
            private String currency_code;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
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
