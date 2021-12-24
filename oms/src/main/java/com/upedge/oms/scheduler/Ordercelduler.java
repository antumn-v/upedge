package com.upedge.oms.scheduler;

import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderType;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.common.service.SaiheOrderRecordService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderService;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.statistics.service.OrderDailyRefundCountService;
import com.upedge.oms.modules.stock.service.AdminStockService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class Ordercelduler {

    @Autowired
    StoreOrderService storeOrderService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDailyPayCountService orderDailyPayCountService;

    @Autowired
    SaiheOrderRecordService saiheOrderRecordService;

    @Autowired
    OrderCommonService orderCommonService;

    @Autowired
    WholesaleOrderService wholesaleOrderService;

    @Autowired
    AdminStockService adminStockService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private OrderDailyRefundCountService orderDailyRefundCountService;


    @Scheduled(cron = "0 0 */1 ? * *")
    public void reUploadOrderToSaihe(){
        Page<SaiheOrderRecord> page = new Page<>();
        SaiheOrderRecord saiheOrderRecord = new SaiheOrderRecord();
        saiheOrderRecord.setState(0);
        page.setT(saiheOrderRecord);
        page.setPageSize(200);
        page.setOrderBy("import_time desc");
        List<SaiheOrderRecord> saiheOrderRecords = saiheOrderRecordService.select(page);
        if (ListUtils.isNotEmpty(saiheOrderRecords)){
            for (SaiheOrderRecord orderRecord : saiheOrderRecords) {
                try {
                    Long id = Long.parseLong(orderRecord.getClientOrderCode());
                    switch (orderRecord.getOrderType()){
                        case OrderType.WHOLESALE:
                            wholesaleOrderService.importOrderToSaihe(id);
                            break;
                        case OrderType.NORMAL:
                            orderService.importOrderToSaihe(id);
                            break;
                        default:
                            continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

    }



}
