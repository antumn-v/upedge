package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.AddressVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.oms.modules.wholesale.request.ExcelCreateWholesaleRequest;
import com.upedge.oms.modules.wholesale.request.ExcelCreateWholesaleRequest.WholesaleExcelData;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderExportShipsRequest;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderListRequest;
import com.upedge.oms.modules.wholesale.response.WholesaleOrderListResponse;
import com.upedge.oms.modules.wholesale.response.WholesaleOrderUpdateResponse;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderAddressService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderItemService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import com.upedge.oms.modules.wholesale.vo.QuantityVo;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderExport;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderItemVo;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * @author author
 */
@Slf4j
@Api(tags = "批发订单")
@RestController
@RequestMapping("/wholesaleOrder")
public class WholesaleOrderController {
    @Autowired
    private WholesaleOrderService wholesaleOrderService;

    @Autowired
    WholesaleOrderAddressService wholesaleOrderAddressService;

    @Autowired
    WholesaleOrderItemService wholesaleOrderItemService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    private OrderShippingUnitService orderShippingUnitService;

    @ApiOperation("订单列表")
    @PostMapping("/app/list")
    public BaseResponse wholesaleOrderAppList(@RequestBody WholesaleOrderAppListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        List<WholesaleOrderAppVo> orderAppVos = wholesaleOrderService.orderAppList(request, session);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, orderAppVos, request);
    }

    @ApiOperation("订单数量")
    @PostMapping("/app/count")
    public BaseResponse wholesaleOrderAppCount(@RequestBody WholesaleOrderAppListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        Map<String, Long> count = wholesaleOrderService.orderAppCount(request, session);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, count, request);
    }

    @ApiOperation("订单产品")
    @GetMapping("/{id}/app/items")
    public BaseResponse orderItems(@PathVariable Long id) {
        List<WholesaleOrderItemVo> itemVos = wholesaleOrderService.selectOrderItems(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, itemVos);
    }

//    @ApiOperation("订单运输方式")
//    @GetMapping("/{id}/ship/list")
    public BaseResponse orderShipList(@PathVariable Long id) {
        WholesaleOrder wholesaleOrder = wholesaleOrderService.selectByPrimaryKey(id);
        List<ShipDetail> shipDetails = wholesaleOrderService.orderShipMethods(wholesaleOrder.getId(),wholesaleOrder.getToAreaId());
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, shipDetails);
    }

//    @ApiOperation("订单修改运输方式")
//    @PostMapping("/{id}/ship/update")
    public BaseResponse orderUpdateShipMethod(@PathVariable Long id, @RequestBody ShipDetail shipDetail) {
        return wholesaleOrderService.orderUpdateShipMethod(id, shipDetail);
    }

    @ApiOperation("取消订单")
    @PostMapping("/{id}/cancel")
    public BaseResponse cancelOrder(@PathVariable Long id){
        wholesaleOrderService.updatePayStateById(id,-1);
        orderShippingUnitService.delByOrderId(id, OrderType.WHOLESALE);
        return BaseResponse.success();
    }

    @ApiOperation("恢复订单")
    @PostMapping("/{id}/restore")
    public BaseResponse restoreOrder(@PathVariable Long id){
        wholesaleOrderService.updatePayStateById(id,0);
        return BaseResponse.success();
    }

    @ApiOperation("订单地址")
    @GetMapping("/{id}/address")
    public BaseResponse orderAddress(@PathVariable Long id) {
        WholesaleOrderAddress address = wholesaleOrderAddressService.selectOrderAddressById(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, address);
    }

    @ApiOperation("订单修改地址")
    @PostMapping("/{id}/address/update")
    public BaseResponse updateOrderAddress(@PathVariable Long id, @RequestBody AddressVo addressVo) {
        boolean b = wholesaleOrderService.orderUpdateAddress(id, addressVo);
        if (b) {
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }


//    @ApiOperation("订单产品修改数量")
//    @PostMapping("/item/{itemId}/update/quantity")
    public BaseResponse orderItemUpdateQuantity(@PathVariable Long itemId, @Validated @RequestBody QuantityVo quantity) {
        boolean b = wholesaleOrderItemService.orderItemUpdateQuantity(itemId, quantity.getQuantity());
        if (b) {
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }

    @ApiOperation("excel导入检查")
    @PostMapping("/excel/check")
    public BaseResponse checkWholesaleExcel(@RequestBody @Valid ExcelCreateWholesaleRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        List<WholesaleExcelData> excelData = wholesaleOrderService.excelDataCheck(request,session);

        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,excelData);
    }

    @ApiOperation("excel生成订单")
    @PostMapping("/excel/create")
    public BaseResponse excelCreateWholesale(@RequestBody @Valid ExcelCreateWholesaleRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        List<WholesaleExcelData> wholesaleExcelDates = request.getWholesaleExcelDataList();
        Map<String, WholesaleExcelData> orderAddress = new ConcurrentHashMap<>();
        Map<String, Map<String, Integer>> orderSkuList = new ConcurrentHashMap<>();
        List<WholesaleExcelData> dataList = new ArrayList<>();
        if (wholesaleExcelDates.size() > 500) {
            dataList = wholesaleExcelDates.subList(0, 500);
        }else {
            dataList = wholesaleExcelDates;
        }

        Iterator<WholesaleExcelData> excelDataIterator = dataList.iterator();
        while (excelDataIterator.hasNext()) {
            WholesaleExcelData data = excelDataIterator.next();
            if (data.getState() != 1) {
                excelDataIterator.remove();
                continue;
            }
            String order = new StringBuilder(data.getStoreTags().trim())
                    .append(data.getCustomerOrderNumber().trim()).toString().replace(" ", "_");
            String orderSku = order.concat(" ").concat(data.getSku().trim());

            if (!orderSkuList.containsKey(order)) {
                orderSkuList.put(order, new ConcurrentHashMap<>());
            }

            Map<String, Integer> skuQuantity = orderSkuList.get(order);

            if (skuQuantity.containsKey(orderSku)) {
                skuQuantity.put(orderSku, (skuQuantity.get(orderSku) + data.getQuantity()));
            } else {
                skuQuantity.put(orderSku, data.getQuantity());
                orderSkuList.put(order, skuQuantity);
            }

            if (!orderAddress.containsKey(order)) {
                orderAddress.put(order, data);
            }
        }
        if(MapUtils.isEmpty(orderAddress) ||
        MapUtils.isEmpty(orderSkuList)){
            return BaseResponse.failed();
        }
        wholesaleOrderService.createOrdersByExcel(orderAddress,orderSkuList,session.getCustomerId());
        return BaseResponse.success();
    }

    @ApiOperation("导出订单物流信息")
    @PostMapping("/export/ships")
    public BaseResponse exportOrderShips(@RequestBody @Valid WholesaleOrderExportShipsRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        Date startTime = new Date();
        Date endTime = new Date();
        String start = request.getStart();
        String end = request.getEnd();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(null == request.getStart() || null == request.getEnd()){
            start = DateUtils.getDate("yyyy-MM-dd", -30, Calendar.DAY_OF_MONTH);
            end = DateUtils.getDate("yyyy-MM-dd", 0, Calendar.DAY_OF_MONTH);

        }
        try {
            startTime= format.parse(start);
            endTime = format.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<WholesaleOrderExport> exports = wholesaleOrderService.selectOrderTrackingByDate(session.getCustomerId(),startTime,endTime);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,exports);
    }

    //=========================admin============================

    /**
     * 批发订单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public WholesaleOrderListResponse adminList(@RequestBody @Valid WholesaleOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.adminList(request, session);
    }

    /**
     * admin历史订单列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/historyList", method = RequestMethod.POST)
    public WholesaleOrderListResponse historyList(@RequestBody @Valid WholesaleOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.historyList(request, session);
    }

    /**
     * 批发订单详情页
     */
    @RequestMapping(value = "/orderDetails/{id}", method = RequestMethod.POST)
    public BaseResponse orderDetails(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.orderDetails(id);
    }

    /**
     * 批发订单补发申请
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyReshipOrder", method = RequestMethod.POST)
    public BaseResponse applyReshipOrder(@RequestBody @Valid ApplyReshipOrderRequest request) {
        String key = RedisUtil.KEY_WHOLESALE_APPLY_RESHIP + request.getOriginalOrderId();
        boolean flag = RedisUtil.lock(redisTemplate, key, 2L, 1000L * 2 * 60);
        //获取锁成功
        if (!flag) {
            return new BaseResponse(ResultCode.FAIL_CODE, "操作中...");
        }
        try {
            Session session = UserUtil.getSession(redisTemplate);
            return wholesaleOrderService.applyWholesaleReshipOrder(request, session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, e.getMessage());
        } finally {
            RedisUtil.unLock(redisTemplate, key);
        }
    }

    /**
     * 批发订单补发审核列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pendingList", method = RequestMethod.POST)
    public WholesaleOrderListResponse pendingList(@RequestBody @Valid WholesaleOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.pendingList(request, session);
    }

    /**
     * 批发订单补发启用
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/confirmPendingOrder", method = RequestMethod.POST)
    public WholesaleOrderUpdateResponse confirmPendingOrder(@RequestBody @Valid UpdatePendingOrderRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.confirmPendingOrder(request, session);
    }

    /**
     * 批发订单补发作废
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelPendingOrder", method = RequestMethod.POST)
    public WholesaleOrderUpdateResponse cancelPendingOrder(@RequestBody @Valid UpdatePendingOrderRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.cancelPendingOrder(request, session);
    }

    /**
     * 导入赛盒  订单已经关联了赛盒运输方式 并且没有saiheOrderCode
     * @return
     */
    @RequestMapping(value = "/importOrderToSaihe", method= RequestMethod.POST)
    public BaseResponse importOrderToSaihe(@RequestBody OrderImportSaiheRequest request) {
        if(!SaiheConfig.SAIHE_ORDER_SWITCH){
            return new BaseResponse(ResultCode.FAIL_CODE,"未开启");
        }
        String key= RedisUtil.KEY_WHOLESALE_IMPORT_SAIHE+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            boolean res= wholesaleOrderService.importOrderToSaihe(request.getOrderId());
            if(res){
                return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
            }
            return new BaseResponse(ResultCode.FAIL_CODE,"导入失败!");
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }

    /**
     * 有赛盒orderCode 从赛盒获取物流
     * @param request
     * @return
     */
    @RequestMapping(value = "/fromSaiheTracking", method= RequestMethod.POST)
    public BaseResponse fromSaiheTracking(@RequestBody OrderSaiheTrackingRequest request) {
//        if(!SaiheConfig.SAIHE_ORDER_SWITCH){
//            return new BaseResponse(ResultCode.FAIL_CODE,"未开启");
//        }
        String key= RedisUtil.KEY_WHOLESALE_SAIHE_TRACK+request.getOrderId();
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*2*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,Constant.MESSAGE_FAIL);
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            boolean res= wholesaleOrderService.getTrackingFromSaihe(request.getOrderId());
            if(res){
                return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
            }
            return new BaseResponse(ResultCode.FAIL_CODE,"获取失败!");
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE,e.getMessage());
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
    }

    /**
     * 获取客户经理批发订单销售额
     * @param allOrderAmountVo
     * @return
     */
    @ApiOperation("获取某月客户经理批发订单销售额  根据 set<managerCode> select")
    @PostMapping("/amountByManagerCodeSet")
    public BaseResponse getWholeSaleOrderAmountByManagerCodeSet(@RequestBody AllOrderAmountVo allOrderAmountVo){
        return wholesaleOrderService.getWholeSaleOrderAmountByManagerCodeSet(allOrderAmountVo);
    };


    @ApiOperation("获取某月客户经理批发订单下单客户数  根据 set<managerCode> select")
    @PostMapping("/wholeSaleOrderCount")
    public BaseResponse getWholeSaleOrderCount(@RequestBody AllOrderAmountVo allOrderAmountVo){
        return wholesaleOrderService.getWholeSaleOrderCount(allOrderAmountVo);
    };

}
