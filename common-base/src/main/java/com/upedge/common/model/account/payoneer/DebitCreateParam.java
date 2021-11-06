package com.upedge.common.model.account.payoneer;

public class DebitCreateParam {


    /**
     * client_reference_id : charge12345
     * amount : 2.02
     * currency : USD
     * description : some description of transaction
     * to : {"type":"partner","id":"123123123"}
     */

    private String client_reference_id;
    private double amount;
    private String currency;
    private String description;
    private ToBean to;

    public String getClient_reference_id() {
        return client_reference_id;
    }

    public void setClient_reference_id(String client_reference_id) {
        this.client_reference_id = client_reference_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ToBean getTo() {
        return to;
    }

    public void setTo(ToBean to) {
        this.to = to;
    }

    public static class ToBean {
        /**
         * type : partner
         * id : 123123123
         */

        private String type = "partner";;
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
