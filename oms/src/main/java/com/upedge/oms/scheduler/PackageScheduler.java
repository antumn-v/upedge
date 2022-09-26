package com.upedge.oms.scheduler;

import com.upedge.common.base.Page;
import com.upedge.common.utils.DateUtils;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.OrderVo;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.service.OrderPackageService;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
//@EnableAsync
public class PackageScheduler {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderPackageService orderPackageService;


    /**
     * 从赛盒获取物流
     */
//    @Async
////    @Scheduled(cron = "0 00 00 ? * *")
////    public void pullTrackingScheduledOne(){
////        pullNormalTracking();
////    }
    /**
     * 从赛盒获取物流
     */
    @Async
    @Scheduled(cron = "0 00 19 ? * *")
    public void pullTrackingScheduledTwo(){
        pullNormalTracking();
    }

    public void pullNormalTracking(){
        log.info("从赛盒获取物流pullNormalTracking开始时间:"+ DateUtils.now());
        Page<Order> page = new Page<Order>();
//        page.setBoundary("id > 0 and saihe_order_code is NOT NULL");
//        page.setPageSize(500);
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderStatus(0);
        orderVo.setShipState(0);
        orderVo.setPayState(1);
        Order order = new Order();
        BeanUtils.copyProperties(orderVo,order);
        page.setT(order);
        page.setOrderBy("id ASC");
        pullNormalTracking(page);
        log.info("从赛盒获取物流pullNormalTracking定时器结束时间："+DateUtils.now());
    }

    public void pullNormalTracking(Page<Order> page){
       List<Order> orderList = orderService.selectPage(page);
        for (Order order : orderList) {
            if (StringUtils.isBlank(order.getSaiheOrderCode())){
                continue;
            }
            // 从赛盒获取物流信息
            try {
                orderService.getTrackingFromSaihe(order.getId());
            } catch (Exception e) {
                log.error("从赛盒获取物流pullNormalTracking信息出错：order:{},exception:{}",order,e);
            }
        }
    }


    @Async
    @Scheduled(cron = "0 00 */2 ? * *")
    public void syncPackageInfo(){
        List<OrderPackage> orderPackages = orderPackageService.selectUploadStoreFailedPackages();
        for (OrderPackage orderPackage : orderPackages) {
            orderPackageService.packageRefreshTrackCode(orderPackage);
        }
    }



}
