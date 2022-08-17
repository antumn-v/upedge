package com.upedge.oms.scheduler;

import com.upedge.common.exception.CustomerException;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.stock.request.CreateProcurementRequest;
import com.upedge.oms.modules.stock.service.AdminStockService;
import com.upedge.oms.modules.stock.service.StockOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
//@Component
public class Ordercelduler {

    @Autowired
    OrderService orderService;

    @Autowired
    StockOrderService stockOrderService;

    @Autowired
    AdminStockService adminStockService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Value("${ifUploadSaihe}")
    Boolean ifUploadSaihe;


    @Scheduled(cron = "0 */10 * ? * *")
    public void reUploadOrderToSaihe(){
        if (!ifUploadSaihe) {
            return ;
        }
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

        List<Long> stockOrderIds = stockOrderService.selectUploadSaiheFailedIds();
        for (Long stockOrderId : stockOrderIds) {
            CreateProcurementRequest request = new CreateProcurementRequest();
            request.setId(stockOrderId);
            try {
                adminStockService.createProcurement(request);
            } catch (CustomerException e) {
                e.printStackTrace();
                continue;
            }
        }

    }
}
