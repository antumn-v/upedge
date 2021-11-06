package com.upedge.oms.modules.wholesale.request;

import com.upedge.common.model.user.vo.AddressVo;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ExcelCreateWholesaleRequest {

    @Size(min = 1)
    List<WholesaleExcelData> wholesaleExcelDataList;


    public static class WholesaleExcelData extends AddressVo {

        private String storeTags;

        private String customerOrderNumber;

        private String sku;

        private Integer quantity;

        private int state;

        private String failReason;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getStoreTags() {
            return storeTags;
        }

        public void setStoreTags(String storeTags) {
            this.storeTags = storeTags;
        }

        public String getCustomerOrderNumber() {
            return customerOrderNumber;
        }

        public void setCustomerOrderNumber(String customerOrderNumber) {
            this.customerOrderNumber = customerOrderNumber;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getFailReason() {
            return failReason;
        }

        public void setFailReason(String failReason) {
            this.failReason = failReason;
        }
    }
}
