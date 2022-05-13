package com.upedge.oms.scheduler;

import com.upedge.common.base.Page;
import com.upedge.common.utils.DateUtils;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import com.upedge.oms.modules.order.vo.OrderVo;
import com.upedge.oms.modules.pack.service.PackageInfoService;
import com.upedge.oms.modules.pack.service.PackageTrackingService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
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
    private WholesaleOrderService wholesaleOrderService;

    @Autowired
    private PackageInfoService packageInfoService;

    @Autowired
    private PackageTrackingService  packageTrackingService;

    @Autowired
    private OrderTrackingService orderTrackingService;

    /**
     * 从赛盒获取物流
     */
//    @Async
//    @Scheduled(cron = "0 00 00 ? * *")
//    public void pullTrackingScheduledOne(){
//        pullNormalTracking();
//    }
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
//
//    //每天1：00执行 获取前一天的包裹
//    @Async
//    @Scheduled(cron = "0 00 01 ? * *")
//    public void pullBeforeDayPackageInfo() {
//        log.info("获取前一天的包裹定时器pullBeforeDayPackageInfo开始时间："+DateUtils.now());
//        Calendar beforeDay = DateTools.getBeforeDay();
//        PackageInfoUpdateRequest request = new PackageInfoUpdateRequest();
//        request.setDateEnd(beforeDay.getTime());
//        request.setDateStart(beforeDay.getTime());
//        try {
//            packageInfoService.updatePackageInfoByDate(request);
//        } catch (Exception e) {
//            log.error("获取前一天的包裹定时器pullBeforeDayPackageInfo，时间为:{},异常为:{}",DateUtils.now(),e);
//            e.printStackTrace();
//        }
//        log.info("获取前一天的包裹定时器pullBeforeDayPackageInfo结束时间："+DateUtils.now());
//    }
//
//
//    //检查没有追踪号信息包裹 从赛盒获取包裹信息 并添加追踪信息 同步追踪信息
//    @Async
//    @Scheduled(cron = "0 00 02 ? * *")
//    public void findNullTrackingNumber() {
//        log.info("获取前一天的包裹定时器findNullTrackingNumber开始时间："+DateUtils.now());
//        Page page = new Page();
//        page.initFromNum();
//        page.setPageSize(500);
//        page.setOrderBy("id asc");
//        page.setBoundary("id > 0");
//        packageInfoService.checkPackageIdNoTrackNumber(page);
//        //更新状态为空的追踪状态
//        packageTrackingService.refreshBlankStatus();
//        log.info("获取前一天的包裹定时器findNullTrackingNumber结束时间："+DateUtils.now());
//    }
//
//    //前一天的包裹 上传追踪信息
//    @Async
//    @Scheduled(cron = "0 00 03 ? * *")
//    public void pullBeforeDayPushTrackLogistics() {
//        Calendar beforeDay = DateTools.getBeforeDay();
//    //    packageTrackingService.updateTrackingMoreByDate(beforeDay);
//    }
//
//    //批发订单从赛盒获取物流
//    @Async
//    @Scheduled(cron = "0 00 18 ? * *")
//    public void pullWholesaleTracking(){
//        log.info("从赛盒获取物流pullWholesaleTracking开始时间:"+DateUtils.now());
//        Page<WholesaleOrder> page = new Page<WholesaleOrder>();
//        page.setBoundary("id > 0 and saihe_order_code is NOT NULL");
//        page.setOrderBy("id asc");
//        page.setPageSize(500);
//        page.setT(new WholesaleOrder());
//        page.getT().setOrderStatus(0);
//        page.getT().setShipState(0);
//        page.getT().setPayState(1);
//        pullWholesaleTracking(page);
//        log.info("从赛盒获取物流pullWholesaleTracking定时器结束时间："+DateUtils.now());
//    }
//
//    void pullWholesaleTracking(Page<WholesaleOrder> page){
//        List<WholesaleOrder> orderList = wholesaleOrderService.selectPage(page);
//        while (!CollectionUtils.isEmpty(orderList)){
//            for (WholesaleOrder wholesaleOrder : orderList) {
//
//                if (StringUtils.isBlank(wholesaleOrder.getSaiheOrderCode())){
//                    continue;
//                }
//                // 从赛盒获取物流信息
//                try {
//                    System.err.println(wholesaleOrder.toString());
//                       boolean trackingFromSaihe = wholesaleOrderService.getTrackingFromSaihe(wholesaleOrder.getId());
//                } catch (Exception e) {
//                    log.error("从赛盒获取物流pullNormalTracking信息出错：order:{},exception:{}",wholesaleOrder,e);
//                }
//            }
//            page.setBoundary("id>"+ orderList.get(orderList.size()-1).getId() + "and saihe_order_code is NOT NULL");
//            orderList.clear();
//            orderList = wholesaleOrderService.selectPage(page);
//        }
//
//    }
//
//
//
//
//    /**
//     * 回传物流失败和待更新的
//     */
//    @Async
//    @Scheduled(cron = "0 00 06 ? * *")
//    public void RetransmitLogistics(){
//        log.info("重新上传失败和待更新的回传物流RetransmitLogistics定时器结束时间："+DateUtils.now());
//       // orderTrackingService.RetransmitLogistics();
//        log.info("重新上传失败和待更新的回传物流RetransmitLogistics定时器结束结束："+DateUtils.now());
//    }

}
