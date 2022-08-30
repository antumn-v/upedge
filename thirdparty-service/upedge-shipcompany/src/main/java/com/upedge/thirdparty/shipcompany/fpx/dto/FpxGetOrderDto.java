package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FpxGetOrderDto {

    @JSONField(name = "data")
    private List<FpxGetOrderDataDTO> data;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "result")
    private String result;

    @NoArgsConstructor
    @Data
    public static class FpxGetOrderDataDTO {
        @JSONField(name = "consignment_info")
        private FpxOrderDto fpxOrderDto;
        @JSONField(name = "parcel_confirm_info")
        private ParcelConfirmInfoDTO parcelConfirmInfo;



        @NoArgsConstructor
        @Data
        public static class ParcelConfirmInfoDTO {
            @JSONField(name = "confirm_parcel_qty")
            private String confirmParcelQty;
            @JSONField(name = "confirm_parcel_weight")
            private Integer confirmParcelWeight;
            @JSONField(name = "parcel_list_confirm_info")
            private List<ParcelListConfirmInfoDTO> parcelListConfirmInfo;

            @NoArgsConstructor
            @Data
            public static class ParcelListConfirmInfoDTO {
                @JSONField(name = "confirm_battery_type")
                private String confirmBatteryType;
                @JSONField(name = "confirm_charge_weight")
                private Integer confirmChargeWeight;
                @JSONField(name = "confirm_high")
                private String confirmHigh;
                @JSONField(name = "confirm_include_battery")
                private String confirmIncludeBattery;
                @JSONField(name = "confirm_length")
                private String confirmLength;
                @JSONField(name = "confirm_volume_weight")
                private Integer confirmVolumeWeight;
                @JSONField(name = "confirm_weight")
                private String confirmWeight;
                @JSONField(name = "confirm_width")
                private String confirmWidth;
                @JSONField(name = "currency_code")
                private String currencyCode;
                @JSONField(name = "parcel_total_value_confirm")
                private String parcelTotalValueConfirm;
            }
        }
    }
}
