package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.dto.OrderExcelImportDto;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OrderExcelImportRequest {

    @Size(min = 1)
    List<OrderExcelImportDto> orderExcels;
}
