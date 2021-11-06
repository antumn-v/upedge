package com.upedge.common.model.ship.request;

import com.upedge.common.model.ship.dto.ShipMethodSelectDto;
import lombok.Data;

import java.util.List;

@Data
public class ShipMethodBatchSearchRequest {

    List<BatchShipMethodSelectDto> batchShipMethodSelectDtos;

    @Data
    public static class BatchShipMethodSelectDto{
        String requestId;

        ShipMethodSelectDto shipMethodSelectDto;
    }


}
