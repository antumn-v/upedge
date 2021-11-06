package com.upedge.common.model.account.payoneer;

import java.util.List;

public class DebitResponse {

    /**
     * type : debit
     * commit_id : e92708a7-57b6-4b46-a52c-3e94ac60ce49
     * client_reference_id : test23423453
     * last_status : 2020-06-05T15:11:04.6903139Z
     * created_at : 2020-06-05T15:11:04.6903139Z
     * request_details : {"client_reference_id":"test23423453","amount":2,"description":"test charge","currency":"USD","to":{"id":100130760,"type":"partner"}}
     * fees : [{"type":"charge_fee","amount":0.04,"currency":"USD"}]
     * amounts : {"charged":{"amount":1.96,"currency":"USD"},"target":{"amount":1.96,"currency":"USD"}}
     * expires_at : 2020-06-05T15:13:04.6903139Z
     */
    private String payment_id;
    private String type;
    private String commit_id;
    private Integer status;
    private String status_description;
    private String client_reference_id;
    private String last_status;
    private String created_at;
    private RequestDetailsBean request_details;
    private AmountsBean amounts;
    private FxBean fx;
    private String expires_at;
    private List<FeesBean> fees;

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    @Override
    public String toString() {
        return "DebitResponse{" +
                "type='" + type + '\'' +
                ", commit_id='" + commit_id + '\'' +
                ", client_reference_id='" + client_reference_id + '\'' +
                ", last_status='" + last_status + '\'' +
                ", created_at='" + created_at + '\'' +
                ", request_details=" + request_details +
                ", amounts=" + amounts +
                ", fx=" + fx +
                ", expires_at='" + expires_at + '\'' +
                ", fees=" + fees +
                '}';
    }

    public FxBean getFx() {
        return fx;
    }

    public void setFx(FxBean fx) {
        this.fx = fx;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommit_id() {
        return commit_id;
    }

    public void setCommit_id(String commit_id) {
        this.commit_id = commit_id;
    }

    public String getClient_reference_id() {
        return client_reference_id;
    }

    public void setClient_reference_id(String client_reference_id) {
        this.client_reference_id = client_reference_id;
    }

    public String getLast_status() {
        return last_status;
    }

    public void setLast_status(String last_status) {
        this.last_status = last_status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public RequestDetailsBean getRequest_details() {
        return request_details;
    }

    public void setRequest_details(RequestDetailsBean request_details) {
        this.request_details = request_details;
    }

    public AmountsBean getAmounts() {
        return amounts;
    }

    public void setAmounts(AmountsBean amounts) {
        this.amounts = amounts;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    public List<FeesBean> getFees() {
        return fees;
    }

    public void setFees(List<FeesBean> fees) {
        this.fees = fees;
    }

    public static class RequestDetailsBean {
        /**
         * client_reference_id : test23423453
         * amount : 2
         * description : test charge
         * currency : USD
         * to : {"id":100130760,"type":"partner"}
         */

        private String client_reference_id;
        private int amount;
        private String description;
        private String currency;
        private ToBean to;

        public String getClient_reference_id() {
            return client_reference_id;
        }

        public void setClient_reference_id(String client_reference_id) {
            this.client_reference_id = client_reference_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public ToBean getTo() {
            return to;
        }

        public void setTo(ToBean to) {
            this.to = to;
        }

        public static class ToBean {
            /**
             * id : 100130760
             * type : partner
             */

            private int id;
            private String type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class FxBean{
        private String quote;
        private String rate;
        private String source_currency;
        private String target_currency;

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getSource_currency() {
            return source_currency;
        }

        public void setSource_currency(String source_currency) {
            this.source_currency = source_currency;
        }

        public String getTarget_currency() {
            return target_currency;
        }

        public void setTarget_currency(String target_currency) {
            this.target_currency = target_currency;
        }
    }

    public static class AmountsBean {
        /**
         * charged : {"amount":1.96,"currency":"USD"}
         * target : {"amount":1.96,"currency":"USD"}
         */

        private ChargedBean charged;
        private TargetBean target;

        public ChargedBean getCharged() {
            return charged;
        }

        public void setCharged(ChargedBean charged) {
            this.charged = charged;
        }

        public TargetBean getTarget() {
            return target;
        }

        public void setTarget(TargetBean target) {
            this.target = target;
        }

        public static class ChargedBean {
            /**
             * amount : 1.96
             * currency : USD
             */

            private double amount;
            private String currency;

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
        }

        public static class TargetBean {
            /**
             * amount : 1.96
             * currency : USD
             */

            private double amount;
            private String currency;

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
        }
    }

    public static class FeesBean {
        /**
         * type : charge_fee
         * amount : 0.04
         * currency : USD
         */

        private String type;
        private double amount;
        private String currency;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
    }
}
