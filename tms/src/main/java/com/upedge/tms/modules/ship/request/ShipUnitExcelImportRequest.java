package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.dto.ShipUnitExitImportDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ShipUnitExcelImportRequest {

    @NotNull
    Long methodId;

    @Size(min = 1)
    List<ShipUnitExitImportDto> shipUnitExitImportDtos;

}
