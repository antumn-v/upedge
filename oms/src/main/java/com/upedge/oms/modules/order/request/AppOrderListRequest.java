package com.upedge.oms.modules.order.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.order.dto.AppOrderListDto;
import lombok.Data;

@Data
public class AppOrderListRequest extends Page<AppOrderListDto> {



    Integer pageNum;

    Integer fromNum;

    Integer pageSize;



}
