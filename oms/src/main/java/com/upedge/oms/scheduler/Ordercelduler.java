package com.upedge.oms.scheduler;

import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.common.service.SaiheOrderRecordService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderService;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.stock.service.AdminStockService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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


    @Scheduled(cron = "0 */5 * ? * *")
    public void reUploadOrderToSaihe(){
        List<Long> ids = orderService.selectUploadSaiheFailedIds();
        if (ListUtils.isNotEmpty(ids)){
            for (Long id : ids) {
                try {
                    orderService.importOrderToSaihe(id);
                }catch (Exception e){
                    log.warn("订单ID：{}，失败原因：{}",id,e.getMessage());
                }

            }
        }

    }
}
