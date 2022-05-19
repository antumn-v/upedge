package com.upedge.common.model.pms.request;

import com.upedge.common.model.oms.order.OrderExcelItemDto;
import com.upedge.common.model.user.vo.Session;
import lombok.Data;

import java.util.List;
@Data
public class StoreCustomVariantRecordSaveRequest {

    private Session session;

    private List<OrderExcelItemDto> orderExcelItemDtos;
}
