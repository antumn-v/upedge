package com.upedge.common.model.ship.request;

import com.upedge.common.model.ship.dto.ShipMethodSelectDto;
import lombok.Data;

@Data
public class ShipMethodPriceRequest extends ShipMethodSelectDto {

    Long shipMethodId;
}
