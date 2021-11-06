package com.upedge.common.model.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.ship.vo.ShipDetail;
import lombok.Data;

import java.util.List;

@Data
public class ShipMethodBatchSearchResponse extends BaseResponse {

    List<BatchShipMethodSelectVo> batchShipMethodSelectVos;

    @Data
    public static class BatchShipMethodSelectVo{
        String requestId;

        List<ShipDetail> ships;
    }

    public ShipMethodBatchSearchResponse() {
    }

    public ShipMethodBatchSearchResponse(int code, List<BatchShipMethodSelectVo> data) {
        super(code, data);
    }
}
