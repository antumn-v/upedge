package com.upedge.oms.modules.stock.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;

/**
 * @author author
 */
public class CustomerStockRecordListRequest extends Page<CustomerStockRecord> {

    String orderBy = "create_time desc";
}
